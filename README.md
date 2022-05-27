# Simple Shop APIs
Simple shop API created by spring boot,spring data!

I create a Spring Boot project(Rest APIs) for a simple shop. You need the following tools and technologies to develop the same.
- Spring-Boot 2.7.0.RELEASE
- Springfox-swagger2 2.9.2
- Lombok 1.18.8
- MapStruct 1.3.0.Final
- JavaSE 11
- Maven 3.3.9

# Dependencies
Open the pom.xml file for spring-aop configuration:

      <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.7.0.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
      </parent>
      
and dpendencies:

       <!-- SPRING -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
         <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
       <!-- PROJECT LOMBOK -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>
        <!-- MAPSTRUCT -->
        <dependency>
            <groupId>org.mapstruct</groupId>
            <artifactId>mapstruct</artifactId>
        </dependency>
         <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <scope>runtime</scope>
        </dependency>


# Features

1. Products Review : return product list based on category also return at least 3 comments for each of them 
2. Enquiry System : return price of products for each provider
