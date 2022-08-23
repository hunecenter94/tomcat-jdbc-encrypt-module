# tomcatEncryptModule 
tomcatEncryptModule server.xml Aes Encrypt & Decrypt

### [비고]
```
프로젝트 중에 Server.xml DB Resource 부분에 url, id, password 암호화를 할수없냐는 문의가 들어와서 찾아 보고 적용을 해보았다.
암호화하는 역할을 하는 DataSourceFactory를 가지고 참조해 소스를 수정 및 추가를 하였다.
secretKey를 사용자가 입력을 하게 해주고 싶지만, 자바 파일을 jar로 다시 묶을수 밖에 없을거 같다..

[시도해본것]
1.propertises를 지정경로로 잡고 secretKey값을 가져오게하기 (사용자들 고려해서 경로를 잡고 가져올려고 해봤지만 사용자 프로젝트마다 경로 잡는게 다르니 실패..)
2.server.xml에 secretKey값을 넣게 할려했지만 secretKey값을 노출한다는거 자체가 보안에 취약하니 패스...

※Tomcat7 버전 이상 부터 적용 가능하다.
```

### [실행 명령어]
```
cmd : java -jar tomcat_encryptModule.jar
```
 
### [적용 방법] 
```
현재 소스는(url, username, password)을 암호화 적용해야 합니다. [아래 사진 참고]
※EncryptedDataSourceFactory의 full path 기억하기
※secretKey는 자바에 선언한 secretKey로 암호화를 해야합니다.
ex)public static String defaultSecretKey = "사용자 키값";
```
![image](https://user-images.githubusercontent.com/29367023/185823873-3c52542f-504f-4f58-a3b3-732ad3f8dc65.png)
![image](https://user-images.githubusercontent.com/29367023/186051524-5ccba25f-bce1-4570-8c15-4cc8f354dd60.png)

### [tistory url]
```
https://hune.tistory.com/77?category=782515
```



### [참고]
``` 
http://www.jdev.it/encrypting-passwords-in-tomcat/
https://jar-download.com/artifacts/org.apache.tomcat/tomcat-jdbc/7.0.19/source-code/org/apache/tomcat/jdbc/pool/DataSourceFactory.java
https://tomcat.apache.org/tomcat-8.0-doc/api/org/apache/tomcat/jdbc/pool/DataSourceFactory.html
```
