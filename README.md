# dropbox
用springboot实现的图床后台服务完整版（fastdfs）
使用方法很简单只需要将application.properties文件修改为自己所需要的ip就好如下：
#=========================设置最大文件上传大小=========
spring.servlet.multipart.max-file-size=500Mb
spring.servlet.multipart.max-request-size=500Mb
#==========================fastdfs配置=================
fdfs.so-timeout=1500
fdfs.connect-timeout=600
fdfs.thumb-image.width=200
fdfs.thumb-image.height=300
fdfs.tracker-list[0]= 192.168.254.142:22122
fastdfs.ip=192.168.254.142
#=======================mysql==============================
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/fileserver?useSSL=false&characterEncoding=utf-8
spring.datasource.username=yourusername
spring.datasource.password= yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

说明：
spring.servlet.multipart.max-file-size
spring.servlet.multipart.max-request-size 此处设置文件上传最大size

fdfs.thumb-image.width
fdfs.thumb-image.height此处为图片压缩图大小

fdfs.tracker-list[0]
fastdfs.ip 此处为 fastdfs服务器的ip地址


感谢 tobato提供的spring fastdfs整合包项目地址 https://github.com/tobato/FastDFS_Client
