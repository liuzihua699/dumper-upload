package com.zihua.upload;

import com.zihua.upload.options.UploadConfig;
import com.zihua.upload.utils.CommonsUtils;
import io.minio.MinioClient;
import io.minio.errors.*;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.lang3.StringUtils;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * author: zihua
 * data: 2020/10/11
 * describe: 
 */
public class Upload {

    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(16, 16, 600L,
            TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(65536));
    private UploadConfig config;
    
    
    public Upload(UploadConfig config) {
        this.config = config;
    }
    
    public void start() {
        System.out.println("...running");
        CommandLine cmdLine = new CommandLine("./bin/mydumper");
        
        this.setArgument(cmdLine);

        DefaultExecutor executor = new DefaultExecutor();
        executor.setExitValues(null);
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
        PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream, errorStream);
        executor.setStreamHandler(streamHandler);
        
        try {
            executor.execute(cmdLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        try {
            String out = outputStream.toString("GBK");
            String err = errorStream.toString("GBK");
            System.out.println(out);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        
        // 上传备份文件
        String endpoint = "http://" + config.getMinioIp() + ":" + config.getMinioPort();
        try {
            MinioClient client = new MinioClient(endpoint, config.getMinioAccess(), config.getMinioSecret());
            boolean isExist = client.bucketExists(config.getOutDir());
            if (!isExist) {
                client.makeBucket(config.getOutDir());
            }
            
            File dir = new File(config.getOutDir());
            File[] backList = dir.listFiles();
            int count = backList.length;
            CountDownLatch latch = new CountDownLatch(count);
            
            for (File file : backList) {
                this.submit(() -> {
                    try {
                        client.putObject(config.getOutDir(), file.getName(), file.getAbsolutePath());
                        System.out.println("file "+ file.getName() +" upload success");
                        latch.countDown();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }

            latch.await();
            
            threadPoolExecutor.shutdown();
            System.out.println("finished upload.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void setArgument(CommandLine cmdLine) {
        if (StringUtils.isNotBlank(config.getHost())) {
            cmdLine.addArgument("-h");
            cmdLine.addArgument(config.getHost());
        }
        if (StringUtils.isNotBlank(config.getUserName())) {
            cmdLine.addArgument("-u");
            cmdLine.addArgument(config.getUserName());
        }
        if (StringUtils.isNotBlank(config.getPassword())) {
            cmdLine.addArgument("-p");
            cmdLine.addArgument(config.getPassword());
        }
        if (config.getPort() != 0) {
            cmdLine.addArgument("-P");
            cmdLine.addArgument(String.valueOf(config.getPort()));
        }
        if (StringUtils.isBlank(config.getOutDir())) {
            config.setOutDir("backup-" + System.currentTimeMillis());
        }
        cmdLine.addArgument("-o");
        cmdLine.addArgument(config.getOutDir());
        
        if (StringUtils.isNotBlank(config.getDatabaseName())) {
            cmdLine.addArgument("-B");
            cmdLine.addArgument(config.getDatabaseName());
        }
        if (config.getTableList() != null && config.getTableList().length != 0) {
            cmdLine.addArgument("-T");
            cmdLine.addArgument(String.join(",", config.getTableList()));
        }
        if (config.getStatementSize() != 0) {
            cmdLine.addArgument("-s");
            cmdLine.addArgument(String.valueOf(config.getStatementSize()));
        }
        if (config.getRows() != 0) {
            cmdLine.addArgument("-r");
            cmdLine.addArgument(String.valueOf(config.getRows()));
        }
        if (config.getChunkSize() != 0) {
            cmdLine.addArgument("-F");
            cmdLine.addArgument(String.valueOf(config.getChunkSize()));
        }
    }

    public static void main(String[] args) {
        String endpoint = "http://118.25.130.227:9000";
        try {
            MinioClient client = new MinioClient(endpoint, "AKIAIOSFODNN7EXAMPLE", "wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY");
            System.out.println(client.bucketExists("backup-" + System.currentTimeMillis()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  提交任务
     */
    public static void submit(Runnable task) {
        if (threadPoolExecutor == null) {
            synchronized (Upload.class) {
                if (threadPoolExecutor == null) {
                    threadPoolExecutor = new ThreadPoolExecutor(16, 16, 600L,
                            TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(65536));
                }
            }
        }
        threadPoolExecutor.submit(task);
    }
}
