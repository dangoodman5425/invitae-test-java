# invitae-lab-platform-exercise - Variant Service
This houses a Spring Boot REST API responsible for creating and retrieving variants. When a variant is created it is persisted to a Kafka topic.

### Building the project 
The project using `maven`

To do a full clean install (including running tests and building packages)
```shell
mvn clean install 
```
## Docker environment 

To run the docker container

`$ docker-compose up`

## Service's actuator API
`$ curl http://localhost:8080/api/meta/`

## Swagger Docs

Located at

`http://localhost:8080/swagger-ui.html`

## Example usage

To retrieve a variant

`$ curl http://localhost:8080/api/v1/variants/ad7dbf59-1c4a-43ef-a857-c78ddbac944b`

To create a variant

`$ curl -X POST -d '{"gene": "test_gene","proteinChange": "protein_change_1","nucleotideChange": "nucleotide_change_1","lastEvaluated": "2021-08-17"}' -H 'Content-Type: application/json' http://localhost:8080/api/v1/variants`


## Migrations

Migrations are applied automatically on application start via Flyway. They can also be manually applied by running:

`$ mvn clean install -Pmigrate`

