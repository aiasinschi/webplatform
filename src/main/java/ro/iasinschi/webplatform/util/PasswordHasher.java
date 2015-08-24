package ro.iasinschi.webplatform.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * File PasswordHasher.java
 * TODO: Document me
 * <p/>
 * Created by Adrian Iasinschi (aiasinschi@gmail.com) on 1/2/2015 6:09 PM.
 * Based on the algorithm by Taylor Hornby as described below:
 * <p/>
 * Password Hashing With PBKDF2 (http://crackstation.net/hashing-security.htm).
 * Copyright (c) 2013, Taylor Hornby
 * All rights reserved.
 * <p/>
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * <p/>
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * <p/>
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * <p/>
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

public class PasswordHasher {
    private static final Log LOG = LogFactory.getLog(PasswordHasher.class);

    public static final int SALT_BYTE_SIZE = 24;
    public static final int HASH_BYTE_SIZE = 24;
    public static final int iterations = 1000;
    public static final int ITERATION_INDEX = 0;
    public static final int SALT_INDEX = 1;
    public static final int PBKDF2_INDEX = 2;
    public static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";

    public PasswordHasher(String initialPassword) {
        /*SecureRandom random = new SecureRandom();
        salt = new byte[SALT_BYTE_SIZE];
        random.nextBytes(salt);
        char[] password = initialPassword.toCharArray();
        // Hash the password
        PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, HASH_BYTE_SIZE * 8);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
            //byte[] hash = pbkdf2(password, salt, PBKDF2_ITERATIONS, HASH_BYTE_SIZE);
            hash = skf.generateSecret(spec).getEncoded();
        } catch (Exception ex) {
            LOG.error("Error while hashing password" + ex.toString());
        }*/
    }

    public static String createHash(String password) {
        return createHash(password.toCharArray());
    }


    public static String createHash(char[] password){
        // Generate a random salt
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_BYTE_SIZE];
        random.nextBytes(salt);

        // Hash the password
        byte[] hash = pbkdf2(password, salt, iterations, HASH_BYTE_SIZE);
        // format iterations:salt:hash
        return iterations + ":" + toHex(salt) + ":" +  toHex(hash);
    }

    public static boolean validatePassword(String password, String correctHash) {
        return validatePassword(password.toCharArray(), correctHash);
    }

    public static boolean validatePassword(char[] password, String correctHash) {
        // Decode the hash into its parameters
        String[] params = correctHash.split(":");
        int iterations = Integer.parseInt(params[ITERATION_INDEX]);
        byte[] salt = fromHex(params[SALT_INDEX]);
        byte[] hash = fromHex(params[PBKDF2_INDEX]);
        // Compute the hash of the provided password, using the same salt,
        // iteration count, and hash length
        byte[] testHash = pbkdf2(password, salt, iterations, hash.length);
        // Compare the hashes in constant time. The password is correct if
        // both hashes match.
        return slowEquals(hash, testHash);
    }

    private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes) {
        PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
            return skf.generateSecret(spec).getEncoded();
        } catch (Exception ex) {
            LOG.error("Error while hashing password" + ex.toString());
        }
        return new byte[1];
    }

    private static boolean slowEquals(byte[] a, byte[] b) {
        int diff = a.length ^ b.length;
        for (int i = 0; i < a.length && i < b.length; i++)
            diff |= a[i] ^ b[i];
        return diff == 0;
    }

    private static byte[] fromHex(String hex) {
        byte[] binary = new byte[hex.length() / 2];
        for (int i = 0; i < binary.length; i++) {
            binary[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return binary;
    }

    private static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0)
            return String.format("%0" + paddingLength + "d", 0) + hex;
        else
            return hex;
    }

    public static void main(String[] args) {
        // Print out 10 hashes
        for (int i = 0; i < 10; i++)
            System.out.println(PasswordHasher.createHash("p\r\nassw0Rd!"));

        // Test password validation
        boolean failure = false;
        System.out.println("Running tests...");
        for (int i = 0; i < 100; i++) {
            String password = "" + i;
            String hash = createHash(password);
            String secondHash = createHash(password);
            if (hash.equals(secondHash)) {
                System.out.println("FAILURE: TWO HASHES ARE EQUAL!");
                failure = true;
            }
            String wrongPassword = "" + (i + 1);
            if (validatePassword(wrongPassword, hash)) {
                System.out.println("FAILURE: WRONG PASSWORD ACCEPTED!");
                failure = true;
            }
            if (!validatePassword(password, hash)) {
                System.out.println("FAILURE: GOOD PASSWORD NOT ACCEPTED!");
                failure = true;
            }
        }
        if (failure)
            System.out.println("TESTS FAILED!");
        else
            System.out.println("TESTS PASSED!");
    }
}


