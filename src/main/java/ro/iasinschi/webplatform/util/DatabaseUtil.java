package ro.iasinschi.webplatform.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

/**
 * File DatabaseUtil.java
 * TODO: Document me
 * <p/>
 * Created by Adrian Iasinschi (aiasinschi@gmail.com) on 1/10/2015 6:17 PM.
 */
public class DatabaseUtil {

    private static final Log LOG = LogFactory.getLog(DatabaseUtil.class);

    private static Connection connection;

    private static Map<String, String> properties;

    /**
     * Method that reads the properties from the user.properties file
     * @return a map containing key:value pairs
     */
    public static Map<String, String> getPropValues() {
        Properties databaseProperties = new Properties();
        String dbPropsFilename = "user.properties";
        InputStream inputStream = DatabaseUtil.class.getClassLoader().getResourceAsStream(dbPropsFilename);
        if (inputStream != null) {
            try {
                databaseProperties.load(inputStream);
            } catch (IOException ex) {
                System.err.println("Could not load properties." + ex.toString());
            }
        } else {
            System.err.println("Property file '" + dbPropsFilename + "' not found in the classpath");
        }
        return (Map) databaseProperties;
    }

    /**
     * Lazy init for the connection
     */
    private static void initConnection() {
        if (connection == null) {
            try {
                Class.forName(getProperties().get("apps.jdbc.driver"));
            } catch (ClassNotFoundException ex) {
                System.err.println("Class not found. Database configuration for project not complete!");
            }
            try {
                connection = DriverManager.getConnection(
                        getProperties().get("apps.jdbc.url"),
                        getProperties().get("apps.jdbc.user"),
                        getProperties().get("apps.jdbc.password"));
            } catch (SQLException ex) {
                System.err.println("SQL Exception while creating connection!" + ex.toString());
            }
        }
    }

    /**
     * Closing the connection and un-registering the DB driver
     */
    private static void closeConnection(){
        try {
            connection.close();
            connection = null;
            Enumeration<Driver> dr = DriverManager.getDrivers();
            while (dr.hasMoreElements()){
                Driver driver = dr.nextElement();
                if (driver.getClass().toString().equals(getProperties().get("apps.jdbc.driver"))){
                    DriverManager.deregisterDriver(driver);
                    LOG.debug("De-registering driver: " + driver.getClass().toString());
                }
            }
            //DriverManager.deregisterDriver(getProperties().get("apps.jdbc.drive"));
        } catch (SQLException ex){
            System.err.println("Exception while closing connection" + ex.toString());
        }
    }

    public static void dropTable(String tableName){
        initConnection();
        String sql = String.format("DROP TABLE IF EXISTS %s ;", tableName);
        try {
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
        } catch (Exception ex){
            System.err.println("Exception while deleting table!" + ex.toString());
        }
        closeConnection();
    }

    public static void createTable(String tableName, List<String> columns){
        initConnection();
        try {
            Statement stmt = connection.createStatement();
            StringBuilder fields = new StringBuilder("");
            for (String column: columns){
                fields.append(", ");
                fields.append(column);
                fields.append(" VARCHAR ");
            }
            String sql = String.format("CREATE TABLE %s ( ID INT PRIMARY KEY NOT NULL %s ); ", tableName, fields.toString());
            stmt.execute(sql);
        } catch(SQLException ex){
            System.err.println("Exception while creating table! " + ex.toString());
        }
        closeConnection();
    }

    public static void addDataToTable(String tableName, List<String> columns, List<List<String>> data){
        initConnection();
        try {
            StringBuilder sqlB = new StringBuilder(String.format("INSERT INTO %s (ID, ", tableName));
            for (int i = 0; i < columns.size() - 1; i++){
                sqlB.append(columns.get(i));
                sqlB.append(", ");
            }
            sqlB.append(columns.get(columns.size() - 1));
            sqlB.append(") VALUES (?, ");
            for (int i = 0; i < columns.size() - 1; i++){
                sqlB.append(" ?, ");
            }
            sqlB.append("? );");
            PreparedStatement prepStatement = connection.prepareStatement(sqlB.toString());
            for (int i = 0; i < data.size(); i ++){
                List<String> row = data.get(i);
                prepStatement.setInt(1, i + 1);
                for (int index = 0; index < row.size(); index ++) {
                    prepStatement.setString(index + 2, row.get(index));
                }
                prepStatement.execute();
             }
        }  catch(SQLException ex){
            System.err.println("Exception while adding data to table! " + ex.toString());
        }
        closeConnection();
    }

    public static List<List<String>> getDataFromTable(String tableName, List<String> columns){
        initConnection();
        List<List<String>> dataList = new ArrayList<>();
        try{
            LOG.debug(String.format("Connection = %s", connection));
            Statement stmt = connection.createStatement();
            LOG.debug(String.format("Statement = %s", stmt));
            String sql = String.format("SELECT * FROM %s;", tableName);
            ResultSet result = stmt.executeQuery(sql);
            LOG.debug(String.format("Result = %s", result));
            while (result.next()){
                List<String> row = new ArrayList<>();
                for (int j = 0; j < columns.size(); j ++) {
                    row.add(result.getString(j + 1));
                }
                dataList.add(row);
            }
        } catch (Exception ex){
            System.err.println("Exception while retrieving data from database!" + ex.toString());
        }
        closeConnection();
        if (dataList.size() == 0){
            return null;
        } else {
            return dataList;
        }
    }


    /**
     * Lazy get method for the properties
     *
     * @return
     */
    public static Map<String, String> getProperties() {
        if (properties == null) {
            properties = getPropValues();
        }
        return properties;
    }

    public static List<String> getColumns(String tableName) {
        List<String> columns = new ArrayList<>();
        initConnection();
        try {
            Statement stmt = connection.createStatement();
            String sql = String.format("SELECT * FROM %s;", tableName);
            LOG.debug(String.format("Executing >>%s<<", sql));
            ResultSet result = stmt.executeQuery(sql);
            ResultSetMetaData rsmd = result.getMetaData();
            for (int i = 0; i < rsmd.getColumnCount(); i++) {
                columns.add(rsmd.getColumnName(i + 1));
            }
        } catch (Exception ex){
            LOG.error(String.format("Exception while fetching column names! %s", ex));
        }
        closeConnection();
        return columns;
    }

    public static void main(String[] args) {
        List<List<String>> data = new ArrayList<>();
        data.add(Arrays.asList(new String[]{"Ionescu", "1500", "27"}));
        data.add(Arrays.asList(new String[]{"Popescu", "1550", "29"}));
        data.add(Arrays.asList(new String[]{"Georgescu", "2150", "39"}));
        String tableName = "USERNAME_DATA_1977";
        List<String> columns = Arrays.asList(new String[]{"name", "salary", "age"});
        DatabaseUtil.dropTable(tableName);
        DatabaseUtil.createTable(tableName, columns);
        DatabaseUtil.addDataToTable(tableName, columns, data);
    }


}
