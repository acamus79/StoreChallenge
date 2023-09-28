<p align="center">
<a href="#" target="_blank"><img src="https://github.com/acamus79/StoreChallenge/assets/85143329/11117597-0502-4b99-af3e-1656440e379b" width="200"></a>
</p>
<h1 align="center">API REST StoreChallenge</h1>

* :argentina: Adrian Camus [LinkedIn](https://www.linkedin.com/in/acamus79/ ) - [GitHub](https://github.com/acamus79)


## Project Description
<a href="https://github.com/topics/java" target="_blank"><img align="center" alt="Made With JAVA"  src="https://img.shields.io/badge/Made%20With-Java-blue"></a>
<a href="http://localhost:8080/api/swagger-ui/index.html" target="_blank"><img align="center" alt="SWAGGER DOC"  src="https://img.shields.io/badge/swagger-3.0-green"></a>
<a href="https://documenter.getpostman.com/view/30103514/2s9YJZ3j89" target="_blank"><img align="center" alt="POSTMAN DOC"  src="https://img.shields.io/badge/Postman-ApiDoc-orange"></a>

This project represents a robust and feature-rich Java-based REST API developed using the Spring Boot framework. With its primary focus on serving as the backbone for an online store, this API offers a comprehensive suite of functionalities tailored to cater to the needs of both users and administrators alike. Whether you are a customer seeking a seamless shopping experience or an administrator responsible for managing the store's operations, this API provides the essential tools and services to streamline operations, enhance user interactions, and ensure the efficient management of your online store.

## Key Features and Highlights

- **User Management:** Effortlessly create and manage user accounts, complete with secure authentication and authorization mechanisms. Differentiate between user roles, including standard users and administrators, to control access and permissions effectively.

- **Product Catalog:** Maintain an extensive product catalog with detailed information, including product names, descriptions, pricing, and stock levels. Keep your offerings up-to-date and organized for a superior shopping experience.

- **Shopping Cart:** Facilitate smooth and convenient shopping journeys with a robust shopping cart system. Users can add, update, and remove items from their carts, ensuring a hassle-free purchasing process.

- **Security:** Prioritize the security of user data and transactions. Utilize industry-standard practices to protect sensitive information and secure communications.

- **RESTful API:** Leverage the power of REST architecture for efficient and scalable communication between clients and the server. This API adheres to REST principles, making it easy to integrate with various client applications.

- **Documentation:** Comprehensive documentation ensures that developers and administrators can easily understand and utilize the API's endpoints and functionalities.

- **Database Integration:** Seamlessly integrate with PostgreSQL, a robust relational database, to store and retrieve essential data efficiently.

- **Containerization:** Containerize your application using Docker and Docker Compose, allowing for straightforward deployment and scaling.

## Technologies Used
- **Programming Language:** Java
- **Framework:** Spring Boot
- **Database:** PostgreSQL
- **Database Management System (DBMS):** PostgreSQL
- **Documentation Tools:** Swagger (for API documentation)
<div style="display: inline_block"><br><br>
  <a href="#" target="_blank"><img align="center" alt="JAVA" height="84" width="84" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg"></a>
  <a href="#" target="_blank"><img align="center" alt="SPRING" height="84" width="84" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg"></a>
  <a href="#" target="_blank"><img align="center" alt="MAVEN" height="84" width="84" src="https://www.svgrepo.com/show/373829/maven.svg"></a>
  <a href="#" target="_blank"><img align="center" alt="PostgreSQL" height="84" width="84" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/postgresql/postgresql-original-wordmark.svg" /></a>
  <a href="#" target="_blank"><img align="center" alt="SWAGGER" height="84" width="84" src="https://www.svgrepo.com/show/374111/swagger.svg" /></a>
  <a href="#" target="_blank"><img align="center" alt="SWAGGER" height="84" width="84" src="https://www.svgrepo.com/show/452192/docker.svg" /></a>
  <a href="#" target="_blank"><img align="center" alt="SWAGGER" height="84" width="84" src="https://www.svgrepo.com/show/306240/intellijidea.svg" /></a>
 </div>


## Documentation

Through Postman and Swagger

|       Document        |      Link   |
|:-----------------------:|:-----------:|
| POSTMAN |<a href="https://documenter.getpostman.com/view/30103514/2s9YJZ3j89" target="_blank">LINK</a>|
| SWAGGER |<a href="http://localhost:8080/swagger-ui/index.html" target="_blank">LINK</a> (only if running)|

<br>
<p align="center">
 <a href="#" target="_blank"><img src="https://github.com/acamus79/StoreChallenge/assets/85143329/fd6d3ce7-0e76-4562-a11b-3d0efe392750" height="300"></a>
 <a href="#" target="_blank"><img src="https://github.com/acamus79/StoreChallenge/assets/85143329/0fb86a36-7f89-470f-962e-cf1a712c4572" height="300"></a>
</p>

## Docker
If you have docker from the root directory, you can run the command ```docker pull acamus79/aec-api-store:latest```, to download the compiled image from my personal docker hub repository, then simply run the command ```docker-compose up```

If you don't want to download the compiled image, you will have to package the application with maven and create your own image and then pull it up with docker compose.
```
cd store
mvn clean package -DskipTests
cd..
docker build -t aec-api-store:1.0.8 .
docker-compose up
```

If you do not want to use Docker to run the application you must configure the data source by configuring the connection to a postgres database in the yml configuration file; and then you can run the command ```mvn spring-boot:run```

![imagen](https://github.com/acamus79/StoreChallenge/assets/85143329/57935ff2-ed68-461b-aadf-2014521fb610)



## Summary
This project represents a robust and secure API developed in Java with Spring Boot, allowing users to manage products and shopping carts in an online store. It incorporates best practices in security, API documentation, and a modular approach that facilitates system maintenance and scalability.
_____
<p align="center">
 <a href="https://acamus79.github.io" target="_blank"><img align="center" alt="Portfolio"  src="https://forthebadge.com/images/badges/built-with-love.svg"></a>
 <a href="#" target="_blank"><img align="center" alt="Daugther"  src="https://github.com/acamus79/StoreChallenge/assets/85143329/f5dda3bd-81cb-4ece-9a60-4c7ae215fa88"></a>
 <a href="#" target="_blank"><img align="center" alt="myMachine"  src="https://github.com/acamus79/StoreChallenge/assets/85143329/5f6c41aa-4209-44b4-8eb7-62f6163c23a7"></a>
</p>

