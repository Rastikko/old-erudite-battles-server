# Erudite Battles Server

[![codecov](https://codecov.io/gh/Rastikko/erudite-battles-server/branch/master/graph/badge.svg?token=op7alomaAC)](https://codecov.io/gh/Rastikko/erudite-battles-server)

To build and test do:

```shell
mvn clean install
```

## TODO

 - Create plan base on turn system where each player can play 1 card at a time.
 - Aligment system, each card you play will drag you towards S, C or L.
 - Question system where you might answer multiple questions
 - Preparation phase will give you information about selected questions
 - Add authentication and authorization endpoints
 - Create websocket updates for when the phase is done
 - Create Bot entity instead of repurposing User
 - Standarize test and fixtures IDS for players, cards..
 
 ## Standars
 
  - Payload must always be a json object that can be deserialized in a phasePayloadObject.
  
  
 ## Configurations
 
 Grab from https://github.com/Rastikko/erudite-battles-server/blob/master/src/main/resources/application-dev.yml
 https://github.com/springframeworkguru/spring5-mysql-recipe-app/blob/mysql-scripts/src/main/scripts/configure-mysql.sql
  
