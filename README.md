# tomcatEncryptModule 
tomcatEncryptModule server.xml Aes Encrypt & Decrypt

### [실행 명령어]
```
cmd : java -jar tomcat_encryptModule.jar
```
 
### [적용 방법] 
```
현재 소스는(url, username, password)을 암호화 적용해야 합니다. [아래 사진 참고]
※EncryptedDataSourceFactory의 full path 기억하기
```
![image](https://user-images.githubusercontent.com/29367023/185823873-3c52542f-504f-4f58-a3b3-732ad3f8dc65.png)
![image](https://user-images.githubusercontent.com/29367023/186051524-5ccba25f-bce1-4570-8c15-4cc8f354dd60.png)

### [Maven Repository]
```
  <repository>
   <id>tomcatEncryptModule-snapshot</id>
   <url>https://github.com/hunecenter94/tomcatEncryptModule-maven-repo/tree/main/snapshots</url>
  </repository>
```

### [Mavne Dependency]
```
 <dependency>
   <groupId>com.github</groupId>
   <artifactId>tomcatEncryptModule</artifactId>
   <version>0.0.1</version>
 </dependency>
```

### [tistory url]
```
https://hune.tistory.com/77?category=782515
```

### [jar download url] 
```
https://blog.kakaocdn.net/dn/xrtjW/btrKkm5Wary/7nOZ3zzJqUTTIsFi4EKEWk/tomcatEncryptModule-0.0.1.jar?attach=1&knm=tfile.jar
```

### [비고]
```
프로젝트 중에 Server.xml DB Resource 부분에 url, id, password 암호화를 할수없냐는 문의가 들어와서 찾아 보고 적용을 해보았다.
암호화하는 역할을 하는 DataSourceFactory를 가지고 참조해 소스를 수정 및 추가를 하였다.
※Tomcat7 버전 이상 부터 적용 가능하다.
```

### [참고]
``` 
http://www.jdev.it/encrypting-passwords-in-tomcat/
https://jar-download.com/artifacts/org.apache.tomcat/tomcat-jdbc/7.0.19/source-code/org/apache/tomcat/jdbc/pool/DataSourceFactory.java
https://tomcat.apache.org/tomcat-8.0-doc/api/org/apache/tomcat/jdbc/pool/DataSourceFactory.html
```
