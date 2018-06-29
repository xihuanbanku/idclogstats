#!/bin/bash

nohup spark-submit --master spark://213server:7077 \
	--class com.isinonet.ismartnet.idc.IdcLogStreaming \
	--executor-memory 1g \
	--executor-cores 2 \
	--total-executor-cores 2 \
	idclogstats-1.0-SNAPSHOT.jar &

