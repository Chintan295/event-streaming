
### Prerequisite

* postgresql
* mvn cli

### Build Setup

* start postgresql


    brew services start postgresql;

* create 'destinations' table


    CREATE TABLE destinations (
        destination_id VARCHAR(50) PRIMARY KEY,
        end_point VARCHAR ( 200 ) NOT NULL,
        cursor TIMESTAMP NOT NULL,
        retry_count INT NOT NULL,
        retry_threshold INT NOT NULL
    );

* Add destinations


    INSERT into destinations (
        destination_id,
        end_point,
        cursor,
        retry_count,
        retry_threshold)
    VALUES (
        '<destinationId>',
        '<endpoint>',
        current_timestamp,
        0,
        10);

* Build application.jar file

    
    mvn clean install
    
* run jar file


    java -jar ./target/event-streaming-0.0.1-SNAPSHOT.jar


* Send event

    
    curl -X POST -H "Content-Type: application/json" -d '{"sourceId":"src1","payload":"Hello Destinations !!!"}' http://localhost:8080/api/event/send


### Architecture

![Architecture](Event-delivery.png)

### Features
* Sender can send events to all the destinations
* All events will be sent in FIFO order
* Once event ingested, it will be available in system in case of application crash
* All the destinations can be configured for retry in isolation
* Easy to support and configure new destination
* At least once delivery ensured
* Better performance of Reactive send event api using webflux

