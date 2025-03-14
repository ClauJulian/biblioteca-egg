Proyecto Biblioteca

Descripción

Este proyecto es un sistema de gestión de una biblioteca que permite administrar Autores, Libros y Editoriales mediante una aplicación web desarrollada con Spring Boot y ThymeLeaf. Implementa un sistema de roles con Spring Security, permitiendo accesos diferenciados para usuarios (USER) y administradores (ADMIN).

Características

Gestión de Autores, Libros y Editoriales con operaciones CRUD.

Sistema de autenticación y autorización mediante Spring Security.

Interfaz dinámica con ThymeLeaf y Bootstrap.

Diferenciación de roles: los administradores pueden gestionar todos los recursos, mientras que los usuarios tienen acceso restringido a ciertas funcionalidades.

Persistencia de datos con Spring Data JPA y MySQL.

Tecnologías Utilizadas

Java 17

Spring Boot 3.4.3

Spring Data JPA

Spring Security

ThymeLeaf con ThymeLeaf Security

Bootstrap y CSS para el diseño frontend

MySQL como base de datos

Lombok para reducción de código boilerplate

Instalación y Configuración

Clonar el repositorio:

git clone <URL_DEL_REPOSITORIO>

Configurar la base de datos:

Crear una base de datos llamada biblioteca en MySQL.

Ajustar las credenciales en application.properties.

Compilar y ejecutar el proyecto:

mvn clean install
mvn spring-boot:run

Acceder a la aplicación:

Usuario: http://localhost:8080/

Administrador: http://localhost:8080/admin

Dependencias (pom.xml)
"""
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.4.3</version>
        <relativePath/>
    </parent>
    <groupId>com.egg</groupId>
    <artifactId>biblioteca</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>biblioteca</name>
    
    <properties>
        <java.version>17</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
        <dependency>
            <groupId>org.thymeleaf.extras</groupId>
            <artifactId>thymeleaf-extras-springsecurity6</artifactId>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <scope>provided</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
"""
Contacto

Para cualquier consulta o contribución, no dudes en contactarme.