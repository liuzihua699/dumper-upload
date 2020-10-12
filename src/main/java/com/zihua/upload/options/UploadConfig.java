package com.zihua.upload.options;

import java.util.Arrays;

/**
 * author: zihua
 * data: 2020/10/11
 * describe:
 */
public class UploadConfig {
    private String databaseName;
    private String[] tableList;
    private String outDir;
    private int statementSize = 1000000;
    private int rows = 0;
    private int chunkSize = 0;
    
    private String host;
    private String userName;
    private String password;
    private int port = 3306;
    
    private String minioIp;
    private int minioPort;
    private String minioAccess;
    private String minioSecret;

    public String getHost() {
        return host;
    }
    public void setHost(String host) {
        this.host = host;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort() {
        return port;
    }
    public void setPort(int port) {
        this.port = port;
    }

    public String getDatabaseName() {
        return databaseName;
    }
    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String[] getTableList() {
        return tableList;
    }
    public void setTableList(String[] tableList) {
        this.tableList = tableList;
    }

    public String getOutDir() {
        return outDir;
    }
    public void setOutDir(String outDir) {
        this.outDir = outDir;
    }

    public int getStatementSize() {
        return statementSize;
    }
    public void setStatementSize(int statementSize) {
        this.statementSize = statementSize;
    }

    public int getRows() {
        return rows;
    }
    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getChunkSize() {
        return chunkSize;
    }
    public void setChunkSize(int chunkSize) {
        this.chunkSize = chunkSize;
    }


    public String getMinioIp() {
        return minioIp;
    }
    public void setMinioIp(String minioIp) {
        this.minioIp = minioIp;
    }

    public int getMinioPort() {
        return minioPort;
    }
    public void setMinioPort(int minioPort) {
        this.minioPort = minioPort;
    }

    public String getMinioAccess() {
        return minioAccess;
    }
    public void setMinioAccess(String minioAccess) {
        this.minioAccess = minioAccess;
    }

    public String getMinioSecret() {
        return minioSecret;
    }
    public void setMinioSecret(String minioSecret) {
        this.minioSecret = minioSecret;
    }


    @Override
    public String toString() {
        return "UploadConfig{" +
                "databaseName='" + databaseName + '\'' +
                ", tableList=" + Arrays.toString(tableList) +
                ", outDir='" + outDir + '\'' +
                ", statementSize=" + statementSize +
                ", rows=" + rows +
                ", chunkSize=" + chunkSize +
                ", host='" + host + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", port=" + port +
                ", minioIp='" + minioIp + '\'' +
                ", minioPort=" + minioPort +
                ", minioAccess='" + minioAccess + '\'' +
                ", minioSecret='" + minioSecret + '\'' +
                '}';
    }
}
