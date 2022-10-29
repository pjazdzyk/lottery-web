::This batch file will clean and build project, rename docker image and prepare for push to hub
call mvnw clean package
docker stop pjazdzyk/lottery-web
docker rm pjazdzyk/lottery-web
docker rmi pjazdzyk/lottery-web
docker build -t pjazdzyk/lottery-web .
docker tag pjazdzyk/lottery-web pjazdzyk/lottery-web:latest