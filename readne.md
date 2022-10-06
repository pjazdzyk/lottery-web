# Lotto|Web
## _Web number lottery game_

Lotto|Web is a web port of the popular number lottery game. User provides 6 distinct numbers from 1 to 99 and receives unique coupon identifier. Winning numbers are drawn once per week and all coupons are processed to determine winners. To become a winner user must score at least 3 matched numbers in lottery. User can retrieve lottery results anytime using his unique copupon identifier.

## Specification

- Spring boot, web application.
- Modular monolith hexagonal architecture.
- No-SQL databases (MongoDB) for coupon and results repositories.
- Docker containers used for DB.
- Redis cache for optimized results queries.
- Automatic winning number generation and results processing (scheduler).

## Tech

Lotto|Web is developed using following technologies: <br>
![image](https://img.shields.io/badge/Java-17-orange?style=for-the-badge) 
![image](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring) 
![image](https://img.shields.io/badge/MongoDB-4EA94B?style=for-the-badge&logo=mongodb&logoColor=white)
![image](https://img.shields.io/badge/redis-%23DD0031.svg?&style=for-the-badge&logo=redis&logoColor=white) 
![image](https://img.shields.io/badge/Docker-2CA5E0?style=for-the-badge&logo=docker&logoColor=white)

Testing:<br>
![image](https://img.shields.io/badge/Junit5-25A162?style=for-the-badge&logo=junit5&logoColor=white) 
![image](https://img.shields.io/badge/Mockito-78A641?style=for-the-badge) 
![image](https://img.shields.io/badge/Testcontainers-9B489A?style=for-the-badge)


## Installation

Lotto|Web requires [Docker](https://www.docker.com/products/docker-desktop/) to run.

[TODO]

## License

MIT


## Author

Piotr Jażdżyk