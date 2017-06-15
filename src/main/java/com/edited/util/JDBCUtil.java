package com.edited.util;

import com.edited.dto.MessageDto;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;


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


    private String findFileInResources(final String fileName) {
        return getClass().getClassLoader().getResource(fileName).getFile();
    }

    public Map<String, List<String>> getDataFromDB(final String dbPath) {
        final String query = "SELECT timestamp, chatname, author, from_dispname, dialog_partner, body_xml FROM Messages ORDER BY timestamp";
        Map<String, List<String>> result = new HashMap<>();
        try (Connection connection = DriverManager.getConnection("Jdbc:sqlite:" + dbPath)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            Set<String> set = new HashSet<>();
            List<MessageDto> list = new ArrayList<>();
            while (resultSet.next()) {
                LocalDateTime time = LocalDateTime.ofInstant(new Timestamp(Long.parseLong(resultSet.getString(1)) * 1000).toInstant(), ZoneOffset.ofHours(0));
                String chat = resultSet.getString(2);
                String fromLoginName = resultSet.getString(3);
                String fromDisplayName = resultSet.getString(4);
                String dialogPartner = resultSet.getString(5);
                String message = resultSet.getString(6);

                set.add(chat);
                list.add(new MessageDto(time, chat, fromLoginName, fromDisplayName, dialogPartner, message));
            }

            for (String chatName : set) {
                List<String> resultList = new ArrayList<>();
                for (MessageDto elem : list) {
                    if (elem.getDialogPartner() == null) {
                        if (chatName.equals(elem.getChatName())) {
                            resultList.add(elem.getFullMessage());
                        }
                    } else {
                        if (chatName.equals(elem.getDialogPartner()))
                            resultList.add(elem.getFullMessage());
                    }
                }
                result.put(chatName, resultList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean exportToTxt(final String folderPath, Map<String, List<String>> printData) {
        Path folcerPath = new File(folderPath).toPath();
        if (!Files.exists(folcerPath)) {
            return false;
        }
        List<String> fullContentFile = new ArrayList<>();
        final String fullContentFileName = "_ALL_CONTENT";
        for (Map.Entry<String, List<String>> entry : printData.entrySet()) {
            if (entry.getValue().isEmpty()) {
                continue;
            }
            String fileName = entry.getKey();
            fileName = fileName.replaceAll("#", "").replaceAll("/\\$", "_").replaceAll(";", "_");
            if (saveFile(entry.getValue(), folderPath, fileName)) {
                fullContentFile.addAll(entry.getValue());
            }
        }
        saveFile(fullContentFile, folderPath, fullContentFileName);
        return true;
    }

    private boolean saveFile(final List<String> content, final String folderPath, final String fileName) {
        final String file = folderPath + "\\" + fileName + ".txt";

        try {
            Path path = Paths.get(file);
            Files.write(path, content, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Can't create file: " + fileName);
            return false;
        }
        return true;
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
