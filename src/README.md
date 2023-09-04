http://localhost:8080/swagger-ui/index.html

- java 11
- mariaDB
- kotlin


### 로컬


### 운영
- VM : https://lightsail.aws.amazon.com/ls/webapp/ap-northeast-2/instances/Amazon_Linux_2023-1/connect
- mariadb docker container 실행  
      ```
    docker run -d --name mariadb -e MYSQL_ROOT_PASSWORD=1234 -e MYSQL_DATABASE=cupidcrew -e MYSQL_USER=ivy -e MYSQL_PASSWORD=1234 -p 3306:3306 mariadb/server:latest
       ```
    ````
    docker exec -it mariadb /bin/bash
    ````