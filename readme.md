# Lottery|Web
## _Number lottery game - Spring Boot application_

Lotto|Web is a web port of the well-known number lottery game. The user provides 6 distinct numbers from 1 to 99 and receives a unique coupon identifier.
Winning numbers are drawn once per week and all coupons are processed to determine winners. To become a winner user must score at least 3 matched numbers in the lottery.
Users can retrieve lottery results anytime using their unique coupon identifier. User coupons generated winning numbers and processed results are kept in a separate database
using SpringData with MongoDB. A configured scheduler will run the lottery once per week will and gather all coupons for the current draw date to process them accordingly to the game rules
and determine all winners and losers. When processing is done, the user can check his results using his unique coupon ID and get his winner award.<br>

Do you wonder if you can really win something? Here is a surprise for you! My reward for you is to give you the opportunity to do something good - and in this case, you can support the
animal shelters!

The main purpose of this project is to learn, learn and learn. Learning by doing. I have explored a couple of different technologies and incorporated them into this project.
For a better presentation and my understanding of how the frontend works with the backend, I have created a very simple static landing page, which you can use for playing.
This project uses modular monolithic application architecture with elements of hexagonal and microservice architecture. The winning numbers generator has been deployed as an independent microservice.
The application is stored in Docker Hub, all modules are containerized - the only thing you need to deploy and run this app by yourself is the docker-compose file and associated MongoDB config js file.
For presentation purposes, Lottery|Web has been deployed on the AWS EC2 Linux server. I have hooked my company domain (synerset.com) to mask long and unpleasant AWS DNS addresses,
and it should be available at: http://lottery.synerset.com:8000.

Here is the page, if you would like to test your luck:<br>
<a href=http://lottery.synerset.com:8000>CLICK HERE PLAY THE GAME....</a>

If you like my project, please hit the star button, thank you!

> VERSION: 1.0.0 <br>
> AUTHOR: PIOTR JAŻDŻYK <br>
> LINKEDIN: https://www.linkedin.com/in/pjazdzyk <br>

## Specification

- Spring Boot, web application
- Modular monolith hexagonal architecture with one module extracted as microservice
- Facade design pattern
- NoSQL databases (MongoDB) for coupon and results repositories
- Good coverage with unit tests, including "happy path" integration tests
- Controllers tested via mockMvc, winning numbers service was stubbed using WireMock
- Scheduled lottery run and results processing
- Redis cache for optimized results queries
- Docker is used for module containerization
- Basic static landing page provided for presentation purposes
- Application deployed on AWS Linux EC2 server

## Tech

Lotto|Web is developed using following technologies: <br>

Core: <br>
![image](https://img.shields.io/badge/17-Java-orange?style=for-the-badge) &nbsp;
![image](https://img.shields.io/badge/apache_maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white) &nbsp;
![image](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring) &nbsp;
![image](https://img.shields.io/badge/MongoDB-4EA94B?style=for-the-badge&logo=mongodb&logoColor=white) &nbsp;
![image](https://img.shields.io/badge/redis-%23DD0031.svg?&style=for-the-badge&logo=redis&logoColor=white) &nbsp;
![image](https://img.shields.io/badge/Docker-2CA5E0?style=for-the-badge&logo=docker&logoColor=white) &nbsp;

Testing:<br>
![image](https://img.shields.io/badge/Junit5-25A162?style=for-the-badge&logo=junit5&logoColor=white) &nbsp;
![image](https://img.shields.io/badge/Mockito-78A641?style=for-the-badge) &nbsp;
![image](https://img.shields.io/badge/Testcontainers-9B489A?style=for-the-badge) &nbsp;

Front:<br>
![image](https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white) &nbsp;
![image](https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white) &nbsp;
![image](https://img.shields.io/badge/Bootstrap-563D7C?style=for-the-badge&logo=bootstrap&logoColor=whitee) &nbsp;

Deployed on:<br>
![image](https://img.shields.io/badge/Amazon_AWS-FF9900?style=for-the-badge&logo=amazonaws&logoColor=white) &nbsp;

## C3 diagram

The C3 diagram blow presents main application components and module dependencies. <br>
Click on image below to review it more readable size.

<a href="https://raw.githubusercontent.com/pjazdzyk/lottery-web/master/architecture/C3_Architecture.png"><img src="architecture/C3_Architecture.png" width="850"/><br></a>

## Installation and run

Lotto|Web requires [Docker](https://www.docker.com/products/docker-desktop/) to run.
Both Winning Numbers microservice and main application (Lottery|Web) are pushed as an images into the Docker Hub.
To deploy and run application, just copy anywhere **docker-compose** and **inint-mongo.js** files. Both files
must be in the same folder. After that, just run following command, and wait for containers to be pulled up and started.

``
docker-compose up
``

After everything builds and ready, you can test the application using [Postman](https://www.postman.com/)
or use a simple <a href="http://lottery.synerset.com:8000">landing page</a> I have prepared for testing. Please note, that lottery results are generated
each Saturday at 12:00.<br>

## Rest-API Endpoints

Application provides two endpoints: for input numbers and results checking. Please follow the specification below:

Service url: http://lottery.synerset.com:8000

|    ENDPOINT     | METHOD |         REQUEST          | RESPONSE |             FUNCTION              |
|:---------------:|:------:|:------------------------:|:--------:|:---------------------------------:|
| api/v1/receiver |  POST  | JSON BODY (typedNumbers) |   JSON   | receives 6 distinct input numbers |
| api/v1/results  |  GET   |   PARAM (requestUuid)    |   JSON   | retrieves lottery results for ID  |


POSTMAN CURL's:<br>

**/receiver**<br>
```
curl --location --request POST 'http://lottery.synerset.com:8000/api/v1/receiver' \
--header 'Content-Type: application/json' \
--data-raw '{
"typedNumbers" : [1,2,3,4,5,6]
}'
```

**/results**<br>
```
curl --location --request GET 'http://lottery.synerset.com:8000/api/v1/results?requestUuid=a0d7bf38-6463-49a7-a397-d8161dd5bf15'
```

## License

GNU GENERAL PUBLIC LICENSE V3

## Acknowledgments

I am grateful for all the wonderful people who supported me during the development of this project.
Thank you!

* [Shields.io](https://img.shields.io)
* [Badges 4 README.md](https://github.com/alexandresanlim/Badges4-README.md-Profile)
