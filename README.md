# dormitory

#### 介绍
正式进入Java EE后，在学习jsp和servlet时做的宿舍管理系统，未使用任何框架。（制作于2019年8月）

bilibili：https://www.bilibili.com/video/BV1TE411f7rt?spm_id_from=333.999.0.0

可以添加学生（姓名，学号，院系，专业，房间），宿舍有人数检测。

#### 软件架构
jsp + servlet + tomcat 8.5


#### 安装教程

1.  在mysql中执行主目录下的 domitory.sql。（导入数据库和部分数据）
2.  将主目录下 extra library 下的两个jar包放入本地tomcat根目录下的lib文件夹内。（如果是mysql8.0请自行更换连接jar）
3.  本做本使用tomcat内置数据源，需将主目录下的context.xml替换到本地tomcat根目录下conf文件夹内，或是自行配置数据源——修改本地tomcat根目录中conf文件夹内的context.xml，末尾加上 （name="dormitoryDBPC"建议不要修改，否则需修改源码中的DButil.java）
```xml
<Resource name="dormitoryDBPC" auth="Container" type="javax.sql.DataSource"

               maxTotal="100" maxIdle="30" maxWaitMillis="10000"

               username="root" password="12345" driverClassName="com.mysql.jdbc.Driver"

               url="jdbc:mysql://localhost:3306/dormitory?characterEncoding=utf8&amp;useSSL=false"/>
```

#### 使用说明

1.  导入idea，并配置tomcat运行。
2.  或是直接将主目录下的dorm.war部署到tomcat下的webapp下，进行访问。

#### 参与贡献

皆非

![](https://file.makeyourchoice.cn/img/github/dorm.jpg)
