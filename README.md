## Table of Contents
1. [General Info](#general-info)
2. [Technologies](#technologies)
3. [Installation](#installation)
4. [EndPoints Examples](#endpoints-examples)
### General Info
***
This project is an RestAPI made in a Spring Boot Application that gets its data from the free-to-use API OpenWeather 
[OpenWeather website](https://openweathermap.org/api)
You can only make request only if you have an account signin (Authorization with bearer token) to 3 endpoints that retrieves
from the OpenWeather API:
* GET /weather/current
* GET /weather/pollution
* GET /weather/forecast
***
This endpoints only require the name of the city you need to retrieve data. The response gives a String that contains the JSON Object or
Array with the data of the weather.
This aplication has a rate limit of calls of 100 per hour handle with Bucket4j, also the 3 endpoints are cacheable.
For the creation of users there are 2 endpoints that can be use:
* POST /auth/signup
* POST /auth/signin
This project implements Spring Security and Jwt Authentication for handling the users that can access to the main endpoints.
A MySQL database connection is made for storing the data from the users and also a table for historic purposes to store every call that 
a user had make to the weather endpoints.
### Tecnologies
***
A list of technologies used within the project:
* [Spring Boot Framework](https://spring.io): Version 3.0.12
* [Java JDK](https://www.java.com): Version 17 [Need Installed]
* [Apache Maven](https://maven.apache.org): Version 3.6.3 [Need Installed]
* [MySQL Database](https://www.mysql.com): Version 8.0.32 [Need Installed]
* Lombok (org.projectlombok)
* JSON Web Token (io.jsonwebtoken): Version 0.11.5
* Spring Framework Security
* OpenAPI (org.springdoc): Version 2.2.0
* Spring Starter Cache (org.springframework.boot): Version 2.4.0
* Caffeine (com.github.ben-manes.caffeine): Version 3.1.5
* Bucked4j (com.bucket4j): Version 8.1.0
### Installation
This project can only be deploy by having Java JDK 17, a MySQL database and Apache Maven locally in the machine where is going to be deploy. You can access the website of each software required on the specified url in the Technologies Section, these are marked with the notation [Need Installed]. For installing then you can consult the official documentation or watch a tutorial.
Having the enviroment ready, you need to clone the repository or download the source code of the project, there you need to make changes to the application.properties files, there you need to change the datasources values corresponding to your local database:
```
spring.datasource.url=jdbc:mysql:/[DB_URL]:[DB_PORT]/[DB_NAME]
spring.datasource.username=[DB_USERNAME]
spring.datasource.password=[DB_PASSWORD]
```
Next step is to build and run the project, you can make it via CMD in the root of the directory of the project by following the next steps:
```
mvnw clean install
mvnw spring-boot:run
```
or an IDE that can run a Spring Boot Application and execute the build.
### EndPoints Examples
***
GET /weather/current
Request: 
```
http://localhost:8080/weather/current?city=Santa Ana
```
Response: 
```
{"coord":{"lon":-117.8732,"lat":33.7495},"weather":[{"id":801,"main":"Clouds","description":"few clouds","icon":"02d"}],"base":"stations","main":{"temp":295.1,"feels_like":294.68,"temp_min":291.94,"temp_max":297.95,"pressure":1017,"humidity":51},"visibility":10000,"wind":{"speed":5.14,"deg":230,"gust":8.75},"clouds":{"all":20},"dt":1700430704,"sys":{"type":2,"id":2003506,"country":"US","sunrise":1700404038,"sunset":1700441204},"timezone":-28800,"id":5392900,"name":"Santa Ana","cod":200}
```

GET /weather/pollution
Request: 
```
http://localhost:8080/weather/pollution?city=San Salvador
```
Response: 
```
{"coord":{"lon":-89.1914,"lat":13.699},"list":[{"main":{"aqi":3},"components":{"co":340.46,"no":0.27,"no2":4.63,"o3":127.32,"so2":16.69,"pm2_5":12.71,"pm10":15.34,"nh3":2.44},"dt":1700425196}]}
```

GET /weather/forecast
Request: 
```
http://localhost:8080/weather/forecast?city=San Salvador
```
Response: 
```
[{"dt":1700438400,"main":{"temp":298.6,"feels_like":299.24,"temp_min":293.3,"temp_max":298.6,"pressure":1013,"sea_level":1013,"grnd_level":938,"humidity":78,"temp_kf":5.3},"weather":[{"id":800,"main":"Clear","description":"clear sky","icon":"01n"}],"clouds":{"all":4},"wind":{"speed":0.88,"deg":227,"gust":1.34},"visibility":10000,"pop":0,"sys":{"pod":"n"},"dt_txt":"2023-11-20 00:00:00"},{"dt":1700524800,"main":{"temp":293.16,"feels_like":293.59,"temp_min":293.16,"temp_max":293.16,"pressure":1012,"sea_level":1012,"grnd_level":939,"humidity":91,"temp_kf":0},"weather":[{"id":802,"main":"Clouds","description":"scattered clouds","icon":"03n"}],"clouds":{"all":42},"wind":{"speed":1,"deg":209,"gust":1.34},"visibility":10000,"pop":0,"sys":{"pod":"n"},"dt_txt":"2023-11-21 00:00:00"},{"dt":1700611200,"main":{"temp":293.91,"feels_like":294.44,"temp_min":293.91,"temp_max":293.91,"pressure":1010,"sea_level":1010,"grnd_level":937,"humidity":92,"temp_kf":0},"weather":[{"id":500,"main":"Rain","description":"light rain","icon":"10n"}],"clouds":{"all":23},"wind":{"speed":0.8,"deg":217,"gust":1.26},"visibility":10000,"pop":0.22,"rain":{"3h":0.18},"sys":{"pod":"n"},"dt_txt":"2023-11-22 00:00:00"},{"dt":1700697600,"main":{"temp":294.32,"feels_like":294.87,"temp_min":294.32,"temp_max":294.32,"pressure":1010,"sea_level":1010,"grnd_level":938,"humidity":91,"temp_kf":0},"weather":[{"id":500,"main":"Rain","description":"light rain","icon":"10n"}],"clouds":{"all":22},"wind":{"speed":0.87,"deg":215,"gust":1.26},"visibility":10000,"pop":0.58,"rain":{"3h":0.76},"sys":{"pod":"n"},"dt_txt":"2023-11-23 00:00:00"},{"dt":1700784000,"main":{"temp":294.52,"feels_like":295.17,"temp_min":294.52,"temp_max":294.52,"pressure":1011,"sea_level":1011,"grnd_level":938,"humidity":94,"temp_kf":0},"weather":[{"id":500,"main":"Rain","description":"light rain","icon":"10n"}],"clouds":{"all":78},"wind":{"speed":0.95,"deg":213,"gust":1.23},"visibility":10000,"pop":0.52,"rain":{"3h":1.31},"sys":{"pod":"n"},"dt_txt":"2023-11-24 00:00:00"}]
```
POST /auth/signin
Request: 
```
http://localhost:8080/weather/auth/signin
```
Body:
```
{"email": "algoprueba@mail.com","password": "123456"}
Response: {"token":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGdvcHJ1ZWJhQG1haWwuY29tIiwiaWF0IjoxNzAwNDMwNjIxLCJleHAiOjE3MDA0NzM4MjF9.7bDa-Ihk47GWTuzDVttoANWGRwJcfB3QDGtbfdq1O7Y","expiresIn":43200000}
```
