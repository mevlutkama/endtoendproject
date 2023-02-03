package utilities;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcUtils {
    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    //1. Step: Registration to the driver
    //2. Step: Create connection with database
    public static Connection connectToDatabase(String hostName, String databaseName, String username, String password) {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            connection = DriverManager.getConnection("jdbc:postgresql://" + hostName + ":5432/" + databaseName, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        createStatement();
        System.out.println("Connection is success!");
        return connection;
    }

    //3. Step: Create statement
    public static Statement createStatement() {

        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Statement created!");
        return statement;
    }

    //4. Step: Execute the query
    public static boolean execute(String query) {
        boolean isExecute;

        try {
            isExecute = statement.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Query Executed!");
        return isExecute;
    }

    //5. Step: Close the connection and statement
    public static void closeConnectionAndStatement() {
        try {
            connection.close();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            if (connection.isClosed() && statement.isClosed()) {
                System.out.println("Connection and statement closed!");
            } else {
                System.out.println("Connection and statement not closed!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static void dropTable(String tableName) {

        try {
            statement.execute("DROP TABLE " + tableName);
            System.out.println("Table " + tableName + " dropped!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    //The method to create table
    public static void createTable(String tableName, String... columnName_DataType) {
        StringBuilder columnName_DataTypeString = new StringBuilder("");
        for (String w : columnName_DataType) {
            columnName_DataTypeString.append(w).append(",");
        }

        columnName_DataTypeString.deleteCharAt(columnName_DataTypeString.lastIndexOf(","));

        try {
            statement.execute("CREATE TABLE " + tableName + "(" + columnName_DataTypeString + " )");
            System.out.println("Table " + tableName + " created!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    //The method to insert data into table
    public static void insertDataIntoTable(String tableName, String... columnName_Value) {

        StringBuilder columnNames = new StringBuilder("");
        StringBuilder values = new StringBuilder("");

        for (String w : columnName_Value) {
            columnNames.append(w.split(" ")[0]).append(",");
            values.append(w.split(" ")[1]).append(",");
        }

        //"INSERT INTO "+tableName+"(id, name, address) VALUES(123, 'john', 'new york')"
        columnNames.deleteCharAt(columnNames.lastIndexOf(","));
        values.deleteCharAt(values.lastIndexOf(","));

        String query = "INSERT INTO " + tableName + "(" + columnNames + ") VALUES(" + values + ")";

        try {
            statement.execute(query);
            System.out.println("Data inserted into table " + tableName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //The method to get column data into a list
    public static List<Object> getColumnList(String columnName, String tableName) {

        List<Object> columnData = new ArrayList<>();

        String query = "SELECT " + columnName + " FROM " + tableName;

        try {
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        while (true) {
            try {
                if (!resultSet.next()) break;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                columnData.add(resultSet.getObject(1));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        return columnData;
    }


    public static List<Map<String, Object>> getQueryResultMap(String query) throws SQLException {
        resultSet = statement.executeQuery(query);
        List<Map<String, Object>> rowList = new ArrayList<>();
        ResultSetMetaData rsmd;
        try {
            rsmd = resultSet.getMetaData();
            while (resultSet.next()) {
                Map<String, Object> colNameValueMap = new HashMap<>();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    colNameValueMap.put(rsmd.getColumnName(i), resultSet.getObject(i));
                }
                rowList.add(colNameValueMap);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowList;
    }
}
