
## Java currency API consumer
	

#### Build: ####

- Install maven
- Run in terminal command:
	

```bash
mvn spring-boot:run
```

#### Includes: ####

- As a client, I want to get a list of all available currencies<br>
http://localhost:8080/api/currencies <br>
- As a client, I want to get all EUR-FX exchange rates at all available dates as a collection<br>
http://localhost:8080/api/rates <br>
- As a client, I want to get the EUR-FX exchange rate at particular day <br>
http://localhost:8080/api/rates?date=2000-09-15 <br>
http://localhost:8080/api/rates?currency=AUD&date=2000-09-15 <br>
http://localhost:8080/api/rates?currency=AUD <br>
- As a client, I want to get a foreign exchange amount for a given currency converted to EUR on a particular day <br>
http://localhost:8080/api/exchange?currency=AUD&date=2000-09-15&amount=2 <br>

[API documentation](http://localhost:8080/swagger-ui/#/)<br>
[DB H2](http://localhost:8080/h2/)<br>
[Bundesbank Daily Exchange Rates](https://www.bundesbank.de/dynamic/action/en/statistics/time-series-databases/time-series-databases/759784/759784?statisticType=BBK_ITS&listId=www_sdks_b01012_3&treeAnchor=WECHSELKURSE)<br>
