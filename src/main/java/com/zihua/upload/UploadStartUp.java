package com.zihua.upload;

import com.zihua.upload.exception.IllegalCommandException;
import com.zihua.upload.options.CommandLineFlags;
import com.zihua.upload.options.UploadConfig;
import com.zihua.upload.utils.CommonsUtils;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

/**
 * Main class for the util.
 * This handles setting up inputs and env, then starting the class.
 */
public class UploadStartUp {

    public static void main(String[] args) {
        CommandLineFlags options = new CommandLineFlags();
        CmdLineParser lineParser = new CmdLineParser(options);

        try {
            lineParser.parseArgument(args);
        } catch (CmdLineException e) {
            lineParser.printUsage(System.out);
            return;
        }

        checkArgument(options);
        
        final UploadConfig uploadConfig = CommonsUtils.getUploadConfig(options);
        System.out.println("start backup database.");
        System.out.println(uploadConfig);
        
        Upload upload = new Upload(uploadConfig);
        upload.start();
    }
    

    /**
     * TODO 检查参数合法性
     * @see IllegalCommandException
     */
    public static void checkArgument(CommandLineFlags options) {
        
    }
}
