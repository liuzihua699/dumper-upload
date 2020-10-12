#!/bin/bash
# Get the relative path to this file.
DIR=$(dirname "$0")

CLASSPATH="${CLASSPATH}:.:${DIR}:${DIR}/bin/*:${DIR}/lib/*"
#nohup java -cp ${CLASSPATH} com.zihua.upload.UploadStartUp -C ./config/application.config $@ 2>&1 &
java -cp ${CLASSPATH} com.zihua.upload.UploadStartUp -C ./config/application.config $@