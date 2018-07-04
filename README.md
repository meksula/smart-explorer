**Smart Explorer Core API**

REST web service that allows you to search for the historical places (museum etc).
Manage your `Spot` quickly and comfortably. Let other users to discover your `Spot`!

Project website:
http://smart-explorer.pl/

The trial version works here:
http://51.38.129.50:8085/

General features:
-
* Two user's types: SpotMaker and Explorer
* Registration & login
* Create new places to visit
* Statistics, rankings, rapports
* Searching for nearest place to visit based on your latitude and longitude
* Searching for places in city, district etc.
* Evaluations and opinions

Technologies used:
-
* Java 8 and Groovy 2.4
* Spring Framework (DI, AOP, Data, Security, Web)
* mongoDB 
* Spock Framework 1.1
* Google Maps API
* Thymeleaf
* Lombok
* Swagger2

Run:
-
App runs on port :8085

1. Build project:    $ ./gradlew build
2. Run app:          $ java -jar ~/build/libs/smart-explorer-core-1.0.0.jar

WARN:
-
If you want to build application with unit/integration test notice that EmbeddedMongo must be download.
It can take relatively to much time.

To build app without tests:
3. Build project(no tests)  $ ./gradlew build -x test  