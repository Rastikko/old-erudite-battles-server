# Erudite Battles Server

[![codecov](https://codecov.io/gh/Rastikko/erudite-battles-server/branch/master/graph/badge.svg?token=op7alomaAC)](https://codecov.io/gh/Rastikko/erudite-battles-server)

To build and test do:

```shell
mvn clean install
```

## TODO

### Features

  - Shuffle cemetery into deck
  - Implement align logic battle mechanic
    - Implement inner game alignment
    - On preparation choose a random question type and delete that aligment.
    - Both players answer same questions, first to answer correctly wins.
    - Damage: base attack + (alignment of question's attacker - 0.5 * defender's resistance alignment)
  - Add cards to transfer alignment to the alternative.
  - Create diverse default deck with options for 3 categories

### Refactors

  - Avoid game model to interface with userId
 
 ## Standars
 
  - Payload must always be a json object that can be deserialized in a phasePayloadObject.
  
 ## DB Configurations

```sh
docker run --name mysqldb -p 3306:3306 -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -d mysql
```

Run in the database:
```sql
CREATE DATABASE eb_dev;
```

In Edit Configuration -> Spring Boot -> Active Profile: `dev`
 

 ### Reference:

 https://github.com/springframeworkguru/spring5-mysql-recipe-app/blob/mysql-scripts/src/main/scripts/configure-mysql.sql
 https://github.com/springframeworkguru/spring-boot-mysql-example/blob/master/src/main/resources/application.properties
