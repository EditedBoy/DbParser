package com.edited;

import com.edited.util.JDBCUtil;

import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        final String dbFilePath = "D:\\Programing\\Elex\\main1.db";
        final String outputPath = "D:\\output";
        final String zipPath = "D:\\output.zip";

        JDBCUtil instance = JDBCUtil.getInstance();
        Map<String, List<String>> dataFromDB = instance.getDataFromDB(dbFilePath);

        System.out.println("Export TXT files: " + instance.exportToTxt(outputPath, dataFromDB, true));
        System.out.println("ZIP files: " + instance.zipFolder(outputPath, zipPath));
    }
}
