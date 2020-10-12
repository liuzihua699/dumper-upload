# 简单运行
下载[dumper-upload-1.0.zip](https://github.com/zihuaVeryGood/dumper-upload/releases/download/1.0/dumper-upload-1.0.zip) 解压进入目录

修改config/application配置项：
```shell script
# mydumper configuration
dumper.database=backup_test
dumper.chunk-size=5
#dumper.table-list=a,b,c,d
#dumper.outdir=./data
#dumper.statement-size=1000000
#dumper.rows=100

# mysql configuration
dumper.mysql.host=rm-bp1j1g6l45i6vcq23co.mysql.rds.aliyuncs.com
dumper.mysql.username=dev
dumper.mysql.password=VoKMmIjY3YZm7cRV
dumper.mysql.port=3306

# minio configuration
minio.ip=118.25.130.227
minio.port=9000
minio.access-key=AKIAIOSFODNN7EXAMPLE
minio.secret-key=wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY
```

以下命令行会以5MB为一个块对数据库进行备份并上传至MinIO服务器
```shell script
./start.sh -h rm-bp1j1g6l45i6vcq23co.mysql.rds.aliyuncs.com -u dev -p VoKMmIjY3YZm7cRV -B backup_test -F 5 -C ./config/application.config
```

等待输出finished upload代表上载完成。

参考图：https://liuzihua.top/upload/2020/10/image-12e101db583940059726416c7ed891b2.png


可以看到备份文件已被上传：

参考图：https://liuzihua.top/upload/2020/10/image-5650f876d08c44f483b6a7c1645751a7.png

# 项目编译

```shell script
git clone https://github.com/zihuaVeryGood/dumper-upload
cd dumper-upload
mvn clean package
```
会在target目录打包好完整的dumper-upload-1.0.zip文件，解压后可直接运行。


# 说明
## 实现思路
dumper-upload使用java实现，运行后会根据配置项和配置文件执行/bin/mydumper去备份mysql数据库，然后使用MinIO的API将备份数据上传。

## 配置项
同时支持配置文件和参数配置，参数配置优先级更高(覆盖配置文件内容)：

主要支持的参数：

参数项 | 配置项 | 描述
---|---|---
-B | dumper.database | 备份的数据库
-T | dumper.table-list | 备份的表
-o | dumper.outdir | 备份文件目录
-s | dumper.statement-size | 生成插入语句字节数
-r | dumper.rows | 按行切分时每行行数
-F | dumper.chunk-size | 按大小切分时每个块大小数，单位MB
-h | dumper.mysql.host | mysql地址
-u | dumper.mysql.username | 登录名
-p | dumper.mysql.password | 登录密码
-P | dumper.mysql.port | 端口号
-C | | 指定配置文件




