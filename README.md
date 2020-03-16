# enviroCar Server [![Build Status](https://travis-ci.org/enviroCar/enviroCar-server.png)](https://travis-ci.org/enviroCar/enviroCar-server) #

## Schema ##

The [JSON Schema](http://json-schema.org/) is maintained in [`rest/src/main/resources/schema`](rest/src/main/resources/schema).

## API Reference ##

See [envirocar.github.io/enviroCar-server](http://envirocar.github.io/enviroCar-server/api/).

The documentation is a GitHub page and managed in the branch ``gh-pages``, see https://github.com/enviroCar/enviroCar-server/tree/gh-pages.

## Deployments ##

* **Stable API**: https://envirocar.org/api/stable/ (the master branch)
* **Development API**: https://envirocar.org/api/dev/ (the dev branch)

## Building & Installation ##

* Install and run [MongoDB](http://www.mongodb.org/downloads) >2.4.
* Clone the repository and switch to the directory.
* Configure the connection in `mongo/src/main/resources/mongo.properties` (if needed).
* Run `mvn clean install`.
* Deploy the `war` file in `webapp/target` to a application server of your choice (e.g. [Apache Tomcat](http://tomcat.apache.org/)).

Yash starts working!

## [License](LICENSE) ##

    Copyright (C) 2013-2019 The enviroCar project
    
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation, either version 3 of the
    License, or (at your option) any later version.
    
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.
    
