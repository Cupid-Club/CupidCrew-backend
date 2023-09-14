http://localhost:8080/swagger-ui/index.html

- java 11
- mariaDB
- kotlin

### local & VM 
- VM(aws lightsail) : https://lightsail.aws.amazon.com/ls/webapp/ap-northeast-2/instances
- SSH용 포트=22 말고 다른 아무번호 추가해서 그걸로 접속해야함 
- lightsail instance ssh 접속
  ````
  ssh -i LightsailDefaultKey-ap-northeast-2.pem ec2-user@3.39.66.26 -p922
  ssh -i defaultKey.pem ec2-user@3.39.66.26 -p922
  ````
- Mariadb docker container 실행  
    ````
    docker run -d --name mariadb -e MYSQL_ROOT_PASSWORD=1234 -e MYSQL_DATABASE=cupidcrew -e MYSQL_USER=ivy -e MYSQL_PASSWORD=1234 -p 3306:3306 mariadb/server:latest
    ````
    ````
    docker exec -it mariadb /bin/bash
    ````
    ````
    apt-get update && apt-get install mariadb-client -y
    ````
- Git SSH 설정
    ````
    ssh-keygen
    cat ~/.ssh/id_rsa.pub
    Git 가서 위에 등록하면됨
    ````

- minio
  ````
  mkdir -p ~/minio/data
  ````
  ````
  1.minio server 시작
  docker run -p 9000:9000 -d -p 9001:9001 --name minio -v ~/minio/data:/data -e "MINIO_ROOT_USER=ivy" -e "MINIO_ROOT_PASSWORD=123456789" quay.io/minio/minio server /data --console-address ":9001"
  
  2.minio createbuckets server 시작
  docker run -it --name createbuckets -d --link minio minio/mc 
  
  3.컨테이너 들어감
  docker exec -it createbuckets /bin/bash
  
  4.bucket 만들기  
  /usr/bin/mc alias set cupidcrew http://minio:9000 ivy 123456789
  /usr/bin/mc mb cupidcrew/candidates
  /usr/bin/mc policy set public cupidcrew/candidates
  ````

- Supervisord 설정


### 로컬 배포
- 디렉토리 구조 설정
- start-cupidcrew.sh 작성
- git pull, start-cupidcrew.sh 등 섞어서 배포 자동화 파일 작성

### 운영 배포
- 디렉토리 구조 설정
- start-cupidcrew.sh 작성
- git pull, start-cupidcrew.sh 등 섞어서 배포 자동화 파일 작성
- deploy.sh 작성
- swagger: http://3.39.66.26:8080/swagger-ui/index.html