// Copyright 2018 Boray Data Co. Ltd.  All rights reserved.

package com.zihua.upload.options;

import org.kohsuke.args4j.Option;

/**
 * Class holding the command line flags
 */
public class CommandLineFlags
{

  /**
   * e.g ./start -C ./config/application.config
   */
  @Option(name = "-C",
          usage = "upload config file path.")
  public String configFile;
  
  
  /**
   * e.g localhost, 127.0.0.1
   */
  @Option(name = "-h",
          usage = "mysql host address")
  public String host;

  
  /**
   * e.g root
   */
  @Option(name = "-u",
          usage = "mysql login name")
  public String username;

  /**
   * e.g localhost, 127.0.0.1
   */
  @Option(name = "-p",
          usage = "mysql login password")
  public String password;


  /**
   * default is 3306.
   */
  @Option(name = "-P",
          usage = "mysql port")
  public int port;

  

  /**
   * e.g ./start -B backup_test
   */
  @Option(name = "-B",
          usage = "mydumper database name config.")
  public String database;

  
  /**
   * e.g ./start -T a,b,c
   */
  @Option(name = "-T",
          usage = "mydumper tablelist config.")
  public String tableList;


  /**
   * default output director is export-time
   * e.g ./start -o ./data
   */
  @Option(name = "-o",
          usage = "mydumper outdir config.")
  public String outDir;


  /**
   * e.g ./start -s 1000000
   */
  @Option(name = "-s",
          usage = "mydumper statement-size config.")
  public int statementSize;


  /**
   * e.g ./start -r 100
   */
  @Option(name = "-r",
          usage = "mydumper rows config.")
  public int rows;


  /**
   * e.g ./start -F 10mb
   */
  @Option(name = "-F",
          usage = "mydumper chunk-size config.")
  public int chunkSize;
}
