package com.edited.util;

import java.sql.*;
import java.util.List;


public class JDBCUtil {

    private static JDBCUtil instance;



    private JDBCUtil(){}

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



    public List<String> getFromDB() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection connection = DriverManager.getConnection("Jdbc:sqlite:main1.db")){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM messages");
            System.out.println(resultSet.getString("author"));


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
