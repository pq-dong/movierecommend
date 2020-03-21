### 数据集简介

本数据集采集于豆瓣电影，电影与演员数据收集于2019年8月上旬，影评数据(用户、评分、评论)收集于2019年9月初，共945万数据，其中包含14万部电影，7万演员，63万用户，416万条电影评分，442万条影评！

### 数据来源声明 
   
爬虫项目源码： [AntSpider](https://github.com/csuldw/AntSpider)项目源码。  
下载来源：[斗码小院公众号](http://www.csuldw.com/assets/articleImg/2019/code-main-fun.png)。  

### Schema

### Movie数据格式

电影数据共140502部，2019年之前的电影有139129，当前未上映的有1373部，包含21个字段，部分字段数据为空，字段说明如下: 

- MOVIE_ID: 电影ID
- NAME: 电影名称
- ACTORS: 主演
- COVER: 封面图片地址
- DIRECTORS: 导演
- GENRES: 类型
- OFFICIAL_SITE: 地址
- REGIONS: 制片国家/地区
- LANGUAGES: 语言
- RELEASE_DATE: 上映日期
- MINS: 片长
- IMDB_ID: IMDbID
- DOUBAN_SCORE: 豆瓣评分
- DOUBAN_VOTES: 豆瓣投票数
- TAGS: 标签
- STORYLINE: 电影描述
- SLUG: 加密的url
- YEAR: 年份
- ACTOR_IDS: 演员与PERSON_ID的对应关系,多个演员采用“\|”符号分割，格式“演员A:ID\|演员B:ID”；
- DIRECTOR_IDS: 导演与PERSON_ID的对应关系,多个导演采用“\|”符号分割，格式“导演A:ID\|导演B:ID”；

## Person数据格式

Person包括演员和导演，共72959个名人数据，包含10个字段，每个PERSON_ID都会对应一个name，不存在PERSON_ID的数据已过滤，各个字段说明如下: 

- PERSON_ID: 名人ID
- NAME: 演员名称
- SEX: 性别
- NAME_EN: 更多英文名
- NAME_ZH: 更多中文名
- BIRTH: 出生日期
- BIRTHPLACE: 出生地
- CONSTELLATORY: 星座
- PROFESSION: 职业
- BIOGRAPHY: 简介，存在简介数据的名人只有15135个。



## User数据格式

639125用户数据，包含4个字段，具体的字段如下：

- USER_ID：用户ID
- USER_MD: 用户md5，唯一标示
- USER_NICKNAME: 评论用户昵称
- USER_AVATAR: 评论用户头像
- USER_Tags: 用户标签

### 标签表
- TAG_ID: 标签id
- TAG_NAME: 标签名称

### Rating数据

600384个用户的4169420条评分数据，涉及电影68471部，评分值为1-5分（1-很差，2-较差，3-还行，4-推荐，5-力荐），共包含5个字段，数据格式如下：

- RATING_ID: 评分ID
- USER_ID：豆瓣用户ID
- MOVIE_ID: 电影ID，对应豆瓣的DOUBAN_ID
- RATING: 评分
- RATING_TIME: 评分时间


### Comment数据格式

评论数据共4428475 条，包含6个字段，各字段说明: 

- COMMENT_ID: 评论ID
- USER_ID：用户ID
- MOVIE_ID: 电影ID，对应豆瓣的DOUBAN_ID
- CONTENT: 评论内容
- VOTES: 评论赞同数
- COMMENT_TIME: 评论时间
