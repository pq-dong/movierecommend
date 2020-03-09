# movierecommend
基于Spring Boot的大数据电影推荐系统，采用协同过滤算法实现个性化推荐

### 任务点
- [x] 开发环境搭建测试
- [x] 初步架构方案
- [x] 数据整理
- [x] 数据导入mysql
- [ ] 后端代码开发
- [ ] 前端代码开发

### 开发环境
* 腾讯云服务器 1核2G 1M带宽
* 域名申请及备案
* mysql部署
* docker 18.09
* nginx 部署测试

### 本地开发
gradle需要在编译阶段引入lombok包  
idea中下载lombok插件  

### 架构
基于SpringBoot，采用gradle进行包管理，采用docker-compose部署，实现简单的水平扩容和负载均衡。  
整体分为以下几个部分：  
* movierecommend web部分提供前端接口
* movierecommend admin部分提供admin编辑系统接口
* movierecommend recommend提供推荐算法
* movierecommend job通过flink对数据进行操作

![项目结构图](http://ydschool-online.nos.netease.com/1582746970143Snipaste_2020-02-26_22-19-39.png)

### 技术栈
* spring boot
* docker
* mysql
* flink
* es
* redis
* gradle
* kafka


