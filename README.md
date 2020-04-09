# movierecommend
基于Spring Boot的大数据电影推荐系统，采用协同过滤算法实现个性化推荐    

### demo地址：[http://movie.pqdong.com/#/](http://movie.pqdong.com/#/)
学生机（俗称板砖机），导致资源加载，接口响应比较慢，请耐心多等一会，让子弹多飞一会；    
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


### 架构
- 项目组织： 前端后端分离，通过Restful接口传递数据
- 代码组织：基于SpringBoot，采用gradle进行依赖管理
- 部署方式：采用docker部署，通过nginx实现简单的负载均衡。
- 大数据处理：采用ElasticSearch进行海量数据的全文检索
- 推荐算法： 采用Mahout基于用户的协同过滤算法和基于内容的协同过滤算法  

![项目结构图](http://ydschool-online.nos.netease.com/1582746970143Snipaste_2020-02-26_22-19-39.png)

### 技术栈
* spring boot
* docker
* mysql
* es
* redis
* gradle

### 其他说明及文档
由于一直从事Golang开发，没怎么搞过java，所以决定此毕设使用java来做。其中的一些还代码有待商榷，会一点点完善。  
其他文档具体可见 /doc目录      

### 数据库中数据来源声明 
来源：[斗码小院公众号](http://www.csuldw.com/assets/articleImg/2019/code-main-fun.png)。  
具体可见/doc/databaseSchema.md

