## v1.0.0 Released 2023-02-21
### APIs versions
* Provides `instance-authority-links v1.0`
* Provides `instance-authority-links-statistics v1.0`
* Provides `instance-authority-linking-rules v1.0`
* Requires `login v7.0`
* Requires `permissions v5.3`
* Requires `users v16.0`
* Requires `mapping-rules-provider v2.0`
* Requires `source-storage-source-records v3.1`
* Requires `authority-source-files v1.0`
* Requires `instance-storage v10.0`

### Features
* Create endpoint for saving instance-authority links ([MODELINKS-2](https://issues.folio.org/browse/MODELINKS-2))
* Create endpoint for retrieving instance-authority links ([MODELINKS-3](https://issues.folio.org/browse/MODELINKS-3))
* Create endpoint for retrieving total links number for authority IDs ([MODELINKS-8](https://issues.folio.org/browse/MODELINKS-8))
* Create endpoint for retrieving instance-authority linking links ([MODELINKS-24](https://issues.folio.org/browse/MODELINKS-24))
* Create endpoint for retrieving authority update statistics ([MODELINKS-34](https://issues.folio.org/browse/MODELINKS-34))
* Create endpoint for retrieving links statistics ([MODELINKS-35](https://issues.folio.org/browse/MODELINKS-35))
* Consume authority updates and trigger updates for linked instances ([MODELINKS-18](https://issues.folio.org/browse/MODELINKS-18))

### Dependencies
* Add `java` `17`
* Add `folio-spring-base` `6.0.1`
* Add `folio-service-tools` `3.0.0`
* Add `spring-boot` `3.0.2`
* Add `spring-kafka` `3.0.2`
* Add `hypersistence-utils` `3.2.0`
* Add `mapstruct` `1.5.3.Final`
* Add `lombok` `1.18.24`
* Add `marc4j` `2.9.2`
* Add `liquibase` `4.19.0`
* Add `testcontainers` `1.17.6`
* Add `wiremock` `2.27.2`
* Add `awaitility` `4.2.0`
