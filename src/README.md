http://localhost:8080/swagger-ui/index.html

- java 11
- mariaDB
- kotlin


### 로컬


### 운영
- VM(aws lightsail) : https://lightsail.aws.amazon.com/ls/webapp/ap-northeast-2/instances/Amazon_Linux_2-3/networking
- ssh -i cupidcrew.pem ec2-user@13.124.185.227 -p222
- git
- docker
- java 11
- mariadb docker container 실행  
      ```
    docker run -d --name mariadb -e MYSQL_ROOT_PASSWORD=1234 -e MYSQL_DATABASE=cupidcrew -e MYSQL_USER=ivy -e MYSQL_PASSWORD=1234 -p 3306:3306 mariadb/server:latest
       ```
    ````
    docker exec -it mariadb /bin/bash
    ````
- git 연동



