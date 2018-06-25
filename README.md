**Smart Explorer Core API**

RESTful web service that allows you to search for the historical places (museum etc).

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
* Spock Framework 1.1
* Google Maps API
* Thymeleaf
* Lombok

Run:
-
App runs on port :8090

1. Build project:    $ ./gradlew build
2. Run app:          $ java -jar ~/build/libs/smart-explorer-core-1.0.0.jar