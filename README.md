# movierecommend
基于Spring Boot的大数据电影推荐系统，采用协同过滤算法实现个性化推荐    

### demo地址：[http://movie.pqdong.com/#/](http://movie.pqdong.com/#/)
由于首页资源比较多，且服务器带宽太小，所以首次访问时加载速度会很慢（大约在30s左右，可怕！），请多等一下；  
待功能开发完毕后会优化此页面加载速度  

### 如何在本地开发
```
# 环境依赖
1. java环境
2. gradle项目，建议通过Intellij IDEA打开，运行build.gradle下载依赖，具体参考gradle教程
3. IDEA下载开启 lombok插件
4. 如果需要正常运行，需要使用mysql数据库和redis，具体配置可根据自己的项目配置在application.yml中
5. 发送短信和照片上传需要一些token和access_key，可以参考代码`configService.getConfigValue`获取配置和阿里云短信
```

### 任务点
- [x] 开发环境搭建测试
- [x] 初步架构方案
- [x] 数据整理
- [x] 数据导入mysql
- [x] 部署测试
- [ ] 后端代码开发
- [ ] 前端代码开发

### 开发环境
* 腾讯云服务器 1核2G 1M带宽
* 域名申请及备案，挂了个二级域名
* mysql部署
* docker 18.09
* nginx 部署测试

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

### 其他说明
由于一直从事Golang开发，没怎么搞过java，所以决定此毕设使用java来做。其中的一些还代码有待商榷，会一点点完善。    

### 数据库中数据来源声明 
来源：[斗码小院公众号](http://www.csuldw.com/assets/articleImg/2019/code-main-fun.png)。  
具体可见/doc/databaseSchema.md

