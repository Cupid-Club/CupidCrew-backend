http://localhost:8080/swagger-ui/index.html

- java 11
- mariaDB
- kotlin


### 로컬


### 운영
- VM(aws lightsail) : https://lightsail.aws.amazon.com/ls/webapp/ap-northeast-2/instances/Amazon_Linux_2-1/connect
- https://always-try.tistory.com/197
- mariadb docker container 실행  
      ```
    docker run -d --name mariadb -e MYSQL_ROOT_PASSWORD=1234 -e MYSQL_DATABASE=cupidcrew -e MYSQL_USER=ivy -e MYSQL_PASSWORD=1234 -p 3306:3306 mariadb/server:latest
       ```
    ````
    docker exec -it mariadb /bin/bash
    ````
  
- 로컬 console에서 작업 및 배포하기 가이드
  https://velog.io/@jonghyun3668/SpringBoot-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-EC2-%EB%B0%B0%ED%8F%AC%ED%95%98%EA%B8%B0
- ssh -i /Users/carrot/.ssh/LightsailDefaultKey-ap-northeast-2.pem ec2-user@43.201.64.27
- ssh -i ./lightsail.pem ec2-user@43.201.6.6

