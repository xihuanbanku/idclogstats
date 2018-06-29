#!/bin/bash

source /etc/profile
cd /home/hadoop/deploy/spark_jobs/
date >> nohup.out
today=`date -d "a day ago" +%Y-%m-%d`

nohup spark-submit --master spark://213server:7077 \
    --class com.isinonet.ismartnet.idc.LogStats \
    --executor-memory 2g \
    --executor-cores 2 \
    /home/hadoop/deploy/spark_jobs/idclogstats-1.0-SNAPSHOT.jar \
    $today hdfs://192.168.1.213:9000/ismartnet/$today* $1 $2 >> nohup.out &
