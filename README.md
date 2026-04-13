# LiterAlura

Book catalog app built with Spring Boot that integrates with the
[Gutendex API](https://gutendex.com) to search, store and query
books and authors in a PostgreSQL database.

![Java](https://img.shields.io/badge/Java-ED8B00?style=flat&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=flat&logo=springboot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?style=flat&logo=postgresql&logoColor=white)
![Jackson](https://img.shields.io/badge/Jackson-000000?style=flat&logo=json&logoColor=white)

## Features

| # | Option | Description |
|---|--------|-------------|
| 1 | Search book by title | Queries Gutendex API and saves result to DB |
| 2 | List saved books | Shows all books stored in the database |
| 3 | List saved authors | Shows all authors stored in the database |
| 4 | Authors alive in a year | Filters authors by a given year |
| 5 | Books by language | Filters books by language code |
| 0 | Exit | Closes the application |

## Getting started

### Prerequisites

- Java 17+
- PostgreSQL running locally
- Maven

### Installation

```bash
# 1. Clone the repository
git clone https://github.com/Ccirhack/literalura-java

# 2. Configure your database in src/main/resources/application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
spring.datasource.username=YOUR_USER
spring.datasource.password=YOUR_PASSWORD

# 3. Run
./mvnw spring-boot:run
```

## What I learned

- Consuming a public REST API with Spring's RestTemplate
- Persisting data with Spring Data JPA and PostgreSQL
- Writing JPQL queries for filtering and reporting
- Deserializing JSON with Jackson
- Building interactive console menus in Spring Boot

## Contributing

Contributions are welcome. Please open an issue first to discuss
any changes you'd like to make, then submit a pull request.

## Contact

Yuan Retamozo · [LinkedIn](https://www.linkedin.com/in/yuan-retamozo/) · yretamozovilca@gmail.com
