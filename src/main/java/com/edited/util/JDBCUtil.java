package com.edited.util;

import com.sun.org.apache.xerces.internal.impl.dv.xs.AbstractDateTimeDV;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;


public class JDBCUtil {

    private static JDBCUtil instance;

    private JDBCUtil() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static JDBCUtil getInstance() {
        if (instance == null) {
            synchronized (JDBCUtil.class) {
                if (instance == null) {
                    instance = new JDBCUtil();
                }
            }
        }
        return instance;
    }

    private Connection openConnection(final String fileName) {
        try {
            return DriverManager.getConnection("Jdbc:sqlite:" + this.findFile(fileName));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    private String findFile(final String fileName) {
        return getClass().getClassLoader().getResource(fileName).getFile();
    }

    public List<String> getDataFromDB() {
        final String query = "SELECT timestamp, chatname, author, from_dispname, body_xml FROM Messages ORDER BY timestamp";
        try (Connection connection = DriverManager.getConnection("Jdbc:sqlite:" + this.findFile("main1.db"))) {
            Statement statement = connection.createStatement();

            System.out.println(getTableColumns(connection, "messages"));

            ResultSet rs = statement.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();

            // Iterate through the data in the result set and display it.
            while (rs.next()) {
                //Print one row
                LocalDateTime time = LocalDateTime.ofInstant(new Timestamp(Long.parseLong(rs.getString(1)) * 1000).toInstant(), ZoneOffset.ofHours(0));
                String chat = rs.getString(2);
                String fromLoginName = rs.getString(3);
                String fromDisplayName = rs.getString(4);
                String message = rs.getString(5);


                System.out.println(String.format("'%s' => [%s] %s(%s): %s", chat, time, fromDisplayName, fromLoginName, message));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<String> getTableColumns(final Connection connection, final String tableName) throws SQLException {
        List<String> result = new ArrayList<>();

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName);
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        System.out.println(String.format("Table '%s' has [%s] columns.", tableName, columnCount));
        for (int i = 1; i <= columnCount; i++) {
            String col_name = metaData.getColumnName(i);
            result.add(col_name);
        }
        return result;
    }

    public List<String> getDatabaseMetaData(final Connection connection) throws SQLException {
        List<String> result = new ArrayList<>();
        DatabaseMetaData md = connection.getMetaData();
        ResultSet rs = md.getTables(null, null, "%", null);
        while (rs.next()) {
            result.add(rs.getString(3));
        }
        return result;
    }
}
