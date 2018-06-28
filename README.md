## 一.项目结构

- core 核心服务
- stage 平台后台进程

- 项目部署

> 1.1 沿用1.0版本的方式。 采用docker 虚拟容器  
  smartnet-web 启动3个 http 服务 + 3个 udp 服务, 通过 nginx 负载均衡
  
| host | ip | process |
| --- |  --- |  --- | 
| docker2 | 192.162.0.2 | zookeeper(id=0), hadoop(namenode), accumulo(master, gc, monitor) |
| docker3 | 192.162.0.3 | zookeeper(id=1), hadoop(datanode), accumulo(tserver) |
| docker4 | 192.162.0.4 | zookeeper(id=2), hadoop(datanode), accumulo(tserver) |
| docker5 | 192.162.0.5 | kafka(id=0) |
| docker6 | 192.162.0.6 | kafka(id=1) |
| docker7 | 192.162.0.7 | kafka(id=2) |

> 1.2 数据库使用 postgres

| host | port | username | pass |
| --- | --- | --- | --- | 
|192.168.1.213 | 5432 | postgres | 123456 |

> 1.3 主机213上的部署内容

| 部署内容                | host      | ip           | port | linux user | 账号 | pass |
| --------------------- | --------- | ------------ | ----- | ---------- | --- | ----  |
| 智能互联网平台           | 213server | 192.168.1.213 | 10808 | hadoop | admin@super | 123456 |
| 爬虫请求未完成任务(nginx) | 213server | 192.168.1.213 | 23683 | root |     |  |     
| iprobe上报(nginx)       | 213server | 192.168.1.213 | 23684 | root |     |  |    
| iStream                | 213server | 192.168.1.213 | 18630  |  | admin | admin |


> 1.4 相关的文件统一在 /home/hadoop/deploy 下


```
启动 http + udp :
    /home/hadoop/deploy/start_web.sh
    
nginx 启动 & 停止(需要root用户):
    nginx -c /etc/nginx/nginx.conf
    nginx -s stop
    
nginx 配置文件路径:
   /etc/nginx/nginx.conf
   
   /home/hadoop/.nginx/conf/http/ismartnet.conf
   /home/hadoop/.nginx/conf/udp/ismartnet.conf
```

> 1.5 智能互联网平台使用tomcat7.0进行部署, 通过配制 conf/server.xml 引用项目的方式进行部署, 引用路径:

``/home/hadoop/deploy/ismartnet_platform``

> 1.6 spark集群采用standalone运行方式进行部署, 两台Executor

| host | ip | cores | memory |
| --- | --- | --- | --- |
| 213server |192.168.1.213| 6 | 16G |
| 216server |192.168.1.216| 8 | 8G |

```
spark master ui 访问 :
http://192.168.1.213:8080/
```

> 1.7 定时统计任务使用linux crontab, 每天凌晨1点执行任务

``0 1 * * * sh /home/hadoop/lhk/video/video.sh``

> 1.8 iStream 

``/home/hadoop/deploy/istream-smartnet``


## 二.环境依赖

- hadoop 2.9.0
- scala 2.11
- spark 2.2.1
- accumulo 1.8.1
- zookeeper 3.4.11

_PS: 本版本的新功能都是以 IP 地址作为用户的ID_

## 三.视频终端用户画像

### 3.1 离线数据收集

    将udp上报的消息数据, 沿用v1.0.0版本的设计, 使用iStream取出需要的字段(mac地址, domain等)
    整理后存入postgresql, 同时管道的输出增加一份至HDFS中。
    
* #### 3.1.1 技术实现

    1. 将udp的数据存入*kafka*消息队列中 
    2. 使用*iStream*管道连接, 在管道中使用*javascript*脚本进行数据字段的筛选, 输出目的地增加*HDFS*

* #### 3.1.2 输入
    
    iProbe通过UDP上报给媒资库的 json数据, 包括url, domain, UA, MAC, source IP, source port, dist IP, dist port 等 
    
* #### 3.1.3 输出

    *HDFS* 文件

### 3.2 单个用户偏好的视频类型

* #### 3.2.1 技术实现

    1. 查询单个用户的视频偏好
    2. 查询accumulo中对应ip 某一天的视频观看历史记录url
    3. 媒资库中豆瓣的数据复制一份存储在accumulo中
    4. 根据视频url 与豆瓣的匹配结果, 得到视频类型top N

* #### 3.2.2 输入

    用户唯一id(ip或mac?),  搜索对应的观看历史, 从豆瓣库中查找视频分类信息, 汇总结果
     
* #### 3.2.3 输出
    
    用户某一天偏好观看的视频标签  
    
    ![用户视频偏好](imgs/3.2.3.png)
    
### 3.3 全网视频类型统计

* #### 3.3.1 技术实现

    1. 媒资库中豆瓣的id和 类型 关系读取到jdbcRDD_1中
    2. 媒资库中豆瓣的id和 url1 关系读取到jdbcRDD_2中
    3. spark读取当天的 HDFS日志文件, 对URL1 进行去重操作(urlRDD)
    4. urlRDD 与jdbcRDD进行join 连接, 找到URL1对应的豆瓣id(idRDD)
    5. idRDD reduceByKey 去重, 统计count,  (类型, count)
    6. 结果与 jdbcRDD_2 进行join 连接, 得到(类型, count)
    7. 结果foreachPartition入postgresql数据库

* #### 3.3.2 输入

    前一天已经能够匹配豆瓣信息的URL1
     
* #### 3.3.3 输出
    
    用户偏好观看的视频标签  
    ![全网视频类型统计](imgs/3.3.3.png)

* #### 3.3.4 数据库设计
    
| 字段名 | 类型 | 长度 | 是否null | 注释 |
| --- | :--- | ---: | --- | --- |
| id | int | 8 | 否 | 主键 |
| atime | date | 10 | 否 | 日期 |
| v_type | int | 2 |  | 视频类型 |
| v_count| int | 8 |  | 数量 |
| mod_date| datetime | 10 |  | 修改时间 |


### 3.4 全网用户 UA 终端统计分析

* #### 3.4.1 技术实现

    对日志中的ua属性进行划分, 得到用户使用的UA 终端类型, 分组统计数量

* #### 3.4.2 输入

    某一天用户观看视频的HDFS日志文件
     
* #### 3.4.3 输出
    
    全网UA 数量分布统计  
    ![全网UA统计分析](imgs/3.4.3.png)
    
* #### 3.4.4 数据库设计
    
| 字段名 | 类型 | 长度 | 是否null | 注释 |
| --- | :--- | ---: | --- | --- |
| id | int | 8 | 否 | 主键 |
| atime | date | 10 | 否 | 日期 |
| ua_type | int | 2 |  | 浏览器类型 |
| ua_count| int | 8 |  | 数量 |
| mod_date| datetime | 10 |  | 修改时间 |

    
### 3.5 全网用户性别分析

* #### 3.5.1 技术实现

    将用户标签分类, 对全网用户性别进行猜测, 分组统计结果

* #### 3.5.2 输入

    观看视频的标签分布
     
* #### 3.5.3 输出
    
    用户性别统计  
    ![全网性别统计分析](imgs/3.5.3.png)
    
* #### 3.5.4 数据库设计
    
| 字段名 | 类型 | 长度 | 是否null | 注释 |
| --- | :--- | ---: | --- | --- |
| id | int | 8 | 否 | 主键 |
| atime | date | 10 | 否 | 日期 |
| gender | int | 2 |  | 性别 |
| ua_count| int | 8 |  | 数量 |
| mod_date| datetime | 10 |  | 修改时间 |

### 3.6 全网用户观看视频时长

* #### 3.6.1 技术实现

    统计全网用户某一天访问视频网站的最早时间和最晚时间, 作为视频的时长, 以10分以下, 10-30分, 30-60分, 60分以上为单位分组统计数量.

* #### 3.6.2 输入

    某一天的HDFS日志文件
     
* #### 3.6.3 输出
    
    全网用户观看视频时长统计  
    ![全网视频时长统计](imgs/3.6.3.png)

* #### 3.6.4 数据库设计
    
| 字段名 | 类型 | 长度 | 是否null | 注释 |
| --- | :--- | ---: | --- | --- |
| id | int | 8 | 否 | 主键 |
| atime | date | 10 | 否 | 日期 |
| duration_type | int | 2 |  | 时长类型 |
| duration_count| int | 8 |  | 数量 |
| mod_date| datetime | 10 |  | 修改时间 |

### ~~3.7 热点视频top 10~~

* #### 3.7.1 技术实现

    1. 对全网用户某一天访问视频网站URL1分组统计count, 取top10
    2. 从accumulo中查询 URL1对应的视频名称

* #### 3.7.2 输入

    某一天的HDFS日志文件
     
* #### 3.7.3 输出
    
    全网用户观看视频top10统计  

* #### 3.7.4 数据库设计
    
| 字段名 | 类型 | 长度 | 是否null | 注释 |
| --- | :--- | ---: | --- | --- |
| id | int | 8 | 否 | 主键 |
| atime | date | 10 | 否 | 日期 |
| v_name | varchar | 20 |  | 视频名称 |
| v_url | varchar | 100 |  | 视频url1 |
| mod_date| datetime | 10 |  | 修改时间 |

## 四.其他数据库表设计

* ### 4.1 字典表

| 字段| 类型 | 长度 | 是否null | 注释 |
|---|---|---:|---|---|
| id | int | 10 | 否 | 主键 |
| p_type | varchar | 50 |  | 字典分类(例如:V_TYPE) |
| p_name | varchar | 50 |  | 名称(例如:type1, type2, ...) |
| p_value | varchar | 100 |  | 参数值(例如:1, 2, ...) |
| description | varchar | 30 |  | 描述说明(例如:1=电影, 2=电视剧, ...) |

* ### 4.2 标签库设计

| 字段| 类型 | 长度 | 是否null | 注释 |
|---|---|---:|---|---|
|id | int | 8 | no | 主键 |
| tip_name | varchar | 20 | | 标签名称(例: 新闻,广告,购物) |

* ### 4.3 accumulo

rowkey |   
| 字段| 类型 | 长度 | 是否null | 注释 |
|---|---|---:|---|---|
|id | int | 8 | no | 主键 |
| tip_name | varchar | 20 | | 标签名称(例: 新闻,广告,购物) |


## 五.疑问

> 5.1 如果出现 错误 "hadoop is not allowed to impersonate hadoop"
      将以下配置增加到docker2的namenode上(core-site.xml), 重启hdfs即可
      
```
<property>
  <name>hadoop.proxyuser.hadoop.hosts</name>
  <value>*</value>
</property>
<property>
  <name>hadoop.proxyuser.hadoop.groups</name>
 <value>hadoop</value>
</property>
```
