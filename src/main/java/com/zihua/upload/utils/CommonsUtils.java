package com.zihua.upload.utils;

import com.zihua.upload.options.CommandLineFlags;
import com.zihua.upload.options.UploadConfig;
import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * author: zihua
 * data: 2020/10/11
 * describe: 获取配置项，参数项配置优先，其次为配置文件。
 */
public class CommonsUtils {

    public static UploadConfig getUploadConfig(CommandLineFlags options) {
        
        UploadConfig uploadConfig   = new UploadConfig();
        FileInputStream inputStream = null;
        Properties properties       = null;
            
        try {
            inputStream             = new FileInputStream(options.configFile);
            if (inputStream == null) throw new RuntimeException("Not fount configuration file: " + options.configFile);
            properties              = new Properties();
            properties.load(inputStream);

            uploadConfig.setMinioIp(properties.getProperty("minio.ip"));
            uploadConfig.setMinioPort(Integer.parseInt(properties.getProperty("minio.port")));
            uploadConfig.setMinioAccess(properties.getProperty("minio.access-key"));
            uploadConfig.setMinioSecret(properties.getProperty("minio.secret-key"));
            
            uploadConfig.setHost(properties.getProperty("dumper.mysql.host"));
            uploadConfig.setUserName(properties.getProperty("dumper.mysql.username"));
            uploadConfig.setPassword(properties.getProperty("dumper.mysql.password"));
            uploadConfig.setPort(Integer.parseInt(properties.getProperty("dumper.mysql.port")));

            uploadConfig.setDatabaseName(properties.getProperty("dumper.database"));
            uploadConfig.setChunkSize(Integer.parseInt(properties.getProperty("dumper.chunk-size", "0")));
            uploadConfig.setOutDir(properties.getProperty("dumper.outdir", ""));
            uploadConfig.setStatementSize(Integer.parseInt(properties.getProperty("dumper.statement-size", "1000000")));
            uploadConfig.setRows(Integer.parseInt(properties.getProperty("dumper.rows", "0")));
            String tables = properties.getProperty("dumper.table-list");
            if (StringUtils.isNotBlank(tables)) {
                uploadConfig.setTableList(tables.split(","));
            }
            
            // options config rewrite
            if (StringUtils.isNotBlank(options.host)) uploadConfig.setHost(options.host);
            if (StringUtils.isNotBlank(options.username)) uploadConfig.setUserName(options.username);
            if (StringUtils.isNotBlank(options.password)) uploadConfig.setPassword(options.password);
            if (options.port != 0) uploadConfig.setPort(options.port);

            if (StringUtils.isNotBlank(options.database)) uploadConfig.setDatabaseName(options.database);
            if (options.chunkSize != 0) uploadConfig.setChunkSize(options.chunkSize);
            if (StringUtils.isNotBlank(options.outDir)) uploadConfig.setOutDir(options.outDir);
            if (options.statementSize != 0) uploadConfig.setStatementSize(options.statementSize);
            if (options.rows != 0) uploadConfig.setRows(options.rows);
            if (StringUtils.isNotBlank(options.tableList)) {
                uploadConfig.setTableList(options.tableList.split(","));
            }
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (properties != null) properties.clear();
        }
        
        return uploadConfig;
    }
    
    // ./mydumper 

    /**
     * 备份数据库指定数据库
     * @param database 数据库名
     */
    public static void backupDatabase(String database) {
        
    }

    /**
     * 备份数据库和表
     * @param database 数据库名
     * @param tableList 备份的表集合
     */
    public static void backupDatabase(String database, String[] tableList) {
        
    }

}
