<p align="center">
<a href="https://puppilots.com" target="_blank"><img src="https://github.com/acamus79/StoreChallenge/assets/85143329/e933ede2-eae8-49e3-b13b-0a57761c04d3" width="200"></a>
</p>
<h1 align="center">API REST StoreChallenge</h1>
<p align="center">
 <a href="https://acamus79.github.io" target="_blank"><img align="center" alt="Portfolio"  src="https://forthebadge.com/images/badges/built-with-love.svg"></a> 
</p>
</p>

* :argentina: Adrian Camus [LinkedIn](https://www.linkedin.com/in/acamus79/ ) - [GitHub](https://github.com/acamus79)


## Project Description
<a href="https://github.com/topics/java" target="_blank"><img align="center" alt="Made With JAVA"  src="https://img.shields.io/badge/Made%20With-Java-blue"></a>
<a href="http://localhost:8080/api/swagger-ui/index.html" target="_blank"><img align="center" alt="SWAGGER DOC"  src="https://img.shields.io/badge/swagger-3.0-green"></a>
<a href="https://documenter.getpostman.com/view/30103514/2s9YJZ3j89" target="_blank"><img align="center" alt="POSTMAN DOC"  src="https://img.shields.io/badge/Postman-ApiDoc-orange"></a>

This project represents a Java-based REST API developed using the Spring Boot framework. Its primary purpose is to manage an online store and provide various functionalities for both users and administrators.

-----

**Documentation**

Through Postman and Swagger

|       Document        |      Link   |
|:-----------------------:|:-----------:|
| POSTMAN |<a href="https://documenter.getpostman.com/view/30103514/2s9YJZ3j89" target="_blank">LINK</a>|
| SWAGGER |<a href="http://localhost:8080/api/swagger-ui/index.html" target="_blank">LINK</a> (only if running)|

<br>


## Technologies Used
- **Programming Language:** Java
- **Framework:** Spring Boot
- **Database:** PostgreSQL
- **Database Management System (DBMS):** PostgreSQL
- **Documentation Tools:** Swagger (for API documentation)
<div style="display: inline_block"><br><br>
  <a href="#" target="_blank"><img align="center" alt="JAVA" height="84" width="84" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg"></a>
  <a href="#" target="_blank"><img align="center" alt="SPRING" height="84" width="84" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg"></a>
  <a href="#" target="_blank"><img align="center" alt="PostgreSQL" height="84" width="84" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/postgresql/postgresql-original-wordmark.svg" /></a>
          
      
</div>


## Key Features
1. **Security:** This project uses Spring Security for managing application security, including user authentication and authorization. Users have roles and permissions assigned.
2. **Data Persistence:** It employs Spring Data JPA along with PostgreSQL for data persistence. It defines data entities such as users and products.
3. **RESTful API:** It implements a RESTful API that allows clients to perform various operations, including product and shopping cart management, as well as user authentication and authorization.
4. **API Documentation:** It uses Swagger to document the API, making it easier for developers to understand and test available routes and endpoints.
5. **Data Transfer Objects (DTOs):** It uses DTOs to represent data sent and received via the API, helping to separate data representations from database entities.
6. **User Management:** It enables user management, including user registration and profile administration.
7. **Product Management:** Administrators can manage products, including creation, updating, and deletion.
8. **Shopping Carts:** Users can add products to their shopping carts and confirm purchases at their convenience.
9. **PasswordEncoder:** It utilizes the BCrypt algorithm to securely store passwords in the database.
10. **Auditing:** It tracks entity creation and update dates using auditing annotations.
11. **Initial Data Load (Seeders):** It implements a seeder that loads initial data into the database, including users and products.
12. **Error Handling:** Properly handles errors and provides clear error responses to API clients.



## Summary
This project represents a robust and secure API developed in Java with Spring Boot, allowing users to manage products and shopping carts in an online store. It incorporates best practices in security, API documentation, and a modular approach that facilitates system maintenance and scalability.



<a href="https://github.com/acamus79" target="_blank"><img align="center" alt="Works on myMachine" src="https://forthebadge.com/images/badges/works-on-my-machine.svg"></a>
![#](https://github.com/acamus79/StoreChallenge/assets/85143329/3dd91f2c-5532-485f-aabc-fee0c4ed5cf6)



_____


# API REST de Tienda en Línea

## Descripción del Proyecto
Este proyecto representa una API REST desarrollada en Java con el framework Spring Boot. Su propósito principal es gestionar una tienda en línea y proporcionar diversas funcionalidades tanto para usuarios como para administradores.

## Tecnologías Utilizadas
- **Lenguaje de Programación:** Java
- **Framework:** Spring Boot
- **Base de Datos:** PostgreSQL
- **Sistema de Gestión de Base de Datos (DBMS):** PostgreSQL
- **Herramientas de Documentación:** Swagger (para documentar la API)

## Características Clave
1. **Seguridad:** Este proyecto utiliza Spring Security para gestionar la seguridad de la aplicación, incluyendo la autenticación y autorización de usuarios. Los usuarios tienen roles y permisos asignados.
2. **Persistencia de Datos:** Emplea Spring Data JPA junto con PostgreSQL para la persistencia de datos. Define entidades de datos como usuarios y productos.
3. **API RESTful:** Implementa una API RESTful que permite a los clientes realizar diversas operaciones, como la gestión de productos y carritos de compra, así como la autenticación y autorización de usuarios.
4. **Documentación de la API:** Utiliza Swagger para documentar la API, lo que facilita a los desarrolladores comprender y probar las rutas y endpoints disponibles.
5. **DTOs (Data Transfer Objects):** Emplea DTOs para representar los datos que se envían y reciben a través de la API. Esto ayuda a separar las representaciones de los datos de las entidades de la base de datos.
6. **Gestión de Usuarios:** Permite la gestión de usuarios, incluyendo el registro de nuevos usuarios y la administración de sus perfiles.
7. **Gestión de Productos:** Los usuarios administradores pueden gestionar productos, incluyendo la creación, actualización y eliminación de los mismos.
8. **Carritos de Compra:** Los usuarios pueden agregar productos a sus carritos de compra y confirmar la compra cuando lo deseen.
9. **PasswordEncoder:** Utiliza el algoritmo BCrypt para almacenar las contraseñas de forma segura en la base de datos.
10. **Auditoría:** Realiza un seguimiento de las fechas de creación y actualización de las entidades mediante anotaciones de auditoría.
11. **Carga Inicial de Datos (Seeders):** Implementa un seeder que carga datos iniciales en la base de datos, como usuarios y productos.
12. **Manejo de Errores:** Gestiona los errores de manera adecuada y proporciona respuestas de error claras a los clientes de la API.
13. **Control de Sesiones:** Desactiva la característica "spring.jpa.open-in-view" para evitar consultas de base de datos innecesarias durante la vista/renderización.

## Resumen
Este proyecto representa una API sólida y segura desarrollada en Java con Spring Boot, que permite a los usuarios gestionar productos y carritos de compra en una tienda en línea. Incorpora buenas prácticas de seguridad, documentación de la API y un enfoque modular que facilita el mantenimiento y la escalabilidad del sistema.

