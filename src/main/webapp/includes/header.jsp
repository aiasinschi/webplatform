<%--
  Created by IntelliJ IDEA.
  User: Adrian
  Date: 1/2/2015
  Time: 5:07 PM
  This is the header file for files when no user is logged in.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!doctype html>

<html lang="en">
<head>
  <meta charset="utf-8">

  <title>e-StA</title>
  <meta name="description" content="e-StA - statistical analysis">
  <meta name="author" content="Adrian Iasinschi">

  <!-- css part -->
  <link rel="stylesheet" href="css/main.css">
  <!-- fonts part -->
  <link href='http://fonts.googleapis.com/css?family=Ubuntu' rel='stylesheet' type='text/css'>
  <link href='http://fonts.googleapis.com/css?family=Aladin' rel='stylesheet' type='text/css'>
  <!-- js part -->

</head>

<body>
<header>
  <div class = "esta-logo">
    <img src="img/esta.png" height=100/>
  </div>
  <div class = "esta-header-text">
    <h1>e-StA - statistical app</h1>
    <h3>easy reporting and analysis</h3>
  </div>
  <div class = "esta-login">
    <form action = "login" method="post">
      <span>Enter user name:</span><input type="text" value="" name = "username"><br/>
      <span>Enter password:</span><input type="password" value="" name = "password"><br/>
      <input type=submit value="Login" name="login">
      <br/>
      <p class="esta-error-login">${loginError}</p>
    </form>
  </div>
</header>
