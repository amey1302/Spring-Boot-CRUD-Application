# CRUD Application with Spring Boot and MongoDB

This project demonstrates a CRUD (Create, Read, Update, Delete) application built using Spring Boot and MongoDB. It includes API endpoints for managing books.

## API Endpoints

### Create Book

- **POST** `/api/v1/book`
- **Description:** Create a new book.
- **Request Body:**
  ```json
  {
    "name": "DDD",
    "author": "Eric Vans",
    "publication": "updated publication",
    "price": 450
  }
### Get All Books

- **GET** `/api/v1/books`
- **Description:** Retrieve all books.

### Get Book By Name (Valid)

- **GET** `/api/v1/book/DDD`
- **Description:** Retrieve a book by its name.
- **Path Parameter:** Book Name (e.g., DDD)

### Get Book By Name (Invalid)

- **GET** `/api/v1/book/Unknown Book`
- **Description:** Attempt to retrieve a book by an unknown name.

### Update Book (Valid)

- **PUT** `/api/v1/book/update/DDD`
- **Description:** Update a book by its name.
- **Path Parameter:** Book Name (e.g., DDD)
- **Request Body:**
  ```json
  {
    "author":"R K Narayan",
    "publication":"updated publication",
    "price":450
  }
### Book By Name Query Parameter (Valid)
- **GET** `/api/v1/book?name=DDD`
- **Description:** Retrieve a book by its name using query parameters.
- **Query Parameter:** name=DDD

### Book By Name Query Parameter (Invalid)

- **GET** `/api/v1/book?name=Unknown`
- **Description:** Attempt to retrieve a book by an unknown name using query parameters.
- **Query Parameter:** name=Unknown

### Delete Book By Name

- **DELETE** `/api/v1/book/delete/DDD`
- **Description:** Delete a book by its name.
- **Path Parameter:** Book Name (e.g., DDD)

### Delete All Books

- **DELETE** `/api/v1/books/delete`
- **Description:** Delete all books.

## Dependencies

### build.gradle

```groovy
implementation 'org.springframework.boot:spring-boot-starter-data-mongodb'
implementation 'org.springframework.boot:spring-boot-starter-web'
testImplementation 'org.springframework.boot:spring-boot-starter-test'

```
### application.properties(localhost)
spring.data.mongodb.host=localhost

spring.data.mongodb.port=27017

spring.data.mongodb.database=Book


### Docker Setup

update application.properties
spring.data.mongodb.host=localhost

spring.data.mongodb.port=27017

spring.data.mongodb.database=Book
