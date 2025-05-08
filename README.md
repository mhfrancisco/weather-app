
# Weather App
This is a weather service application




## Environment Variables

To run this project, you will need to add key variables to the application.yaml file

```
weatherstack:
  url: http://api.weatherstack.com/
  key: 

openweathermap:
  url: http://api.openweathermap.org/data/2.5/
  key: 
```


## Run Locally

Clone the project

```bash
  git clone https://github.com/mhfrancisco/weather-app.git
```

Go to the project directory

```bash
  cd weather-app
```

Install dependencies

```bash
  mvn clean install
```

Start the server

```bash
  mvn spring-boot:run
```


## API Reference

#### Get weather of Melbourne

```http
  GET http://localhost:8080/v1/weather"
```

#### Get weather of a specific city

```http
  GET http://localhost:8080/v1/weather?city={name}"
```

| Parameter | Type     | Description                        |
| :-------- | :------- | :--------------------------------- |
| `name`    | `string` | Name of the city for weather data  |



## Things for improvement

- Unit test and code coverage
- Linting tool
- Yaml files for different environments
- Containerization (Docker)

