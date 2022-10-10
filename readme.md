# Lotto|Web
## _Number lottery game - Spring Boot application_

Lotto|Web is a web port of the popular number lottery game. User provides 6 distinct numbers from 1 to 99 and receives unique coupon identifier. Winning numbers are drawn once per week and all coupons are processed to determine winners. To become a winner user must score at least 3 matched numbers in lottery. User can retrieve lottery results anytime using his unique copupon identifier.

> VERSION: 1.0.0 - SNAPSHOT <br>
> AUTHOR: PIOTR JAŻDŻYK <br>
> LINKEDIN: https://www.linkedin.com/in/pjazdzyk <br>

## Specification

- Spring boot, web application
- modular monolith architecture + facade design pattern
- noSQL databases (MongoDB) for coupon and results repositories
- Docker containers used for DB
- Redis cache for optimized results queries
- automatic winning number generation and results processing (scheduler)

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

## C3 diagram

[TODO]

## Installation

Lotto|Web requires [Docker](https://www.docker.com/products/docker-desktop/).

[TODO]

## License

MIT

## Acknowledgments
* [Shields.io](https://img.shields.io)
* [Badges 4 README.md](https://github.com/alexandresanlim/Badges4-README.md-Profile)

