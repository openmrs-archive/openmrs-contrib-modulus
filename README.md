MODULUS
=====

Backend of the OpenMRS Modules directory at [http://modules.openmrs.org](http://modules.openmrs.org).

Provides a data model and a REST api for representing Modules. [Modulus UI] is developed separately, and provides the front-end web application that consumes this REST api.

Building
-----

To prepare a dev environment:

1. Build requirements: MySQL, Java EE JDK >= 7. **You may experience problems running on a server with less than 2GB RAM.**
2. Install **Grails 2.3.7**. [gvmtool][] is the simplest way:
       
        $ gvm install grails 2.3.7

3. Clone this repo:

        $ git clone https://github.com/openmrs/openmrs-contrib-modulus.git
        $ cd openmrs-contrib-modulus
        
4. Create a MySQL database:

        $ mysql -u root -p -e "CREATE DATABASE modulus"

5. Configure Modulus. Modulus looks for config files in `~/.grails/modulus-config.properties`, `/opt/modulus/modulus-config.properties`, or in the classpath. Custom config locations can be passed with `-Dmodulus.config.location=CONFIG_PATH` as a run argument. See example below.

6. Run grails. The final command will start a dev server:

        $ grails clean
        $ grails refresh-dependencies
        $ grails run-app
        
**NOTE:** Modulus only provides the back-end and REST API of the OpenMRS Modules application. To get an actual web interface for Modulus, build and install [Modulus UI][].
        
[gvmtool]: http://gvmtool.net/
[Modulus-UI]: https://github.com/openmrs/openmrs-contrib-modulus-ui

Example `modulus-config.properties`:
-----

```ini
# What Modulus expects its public-facing URL will be. Used whenever absolute URLs
# need to be generated.
grails.serverURL=http://localhost:8080

# Directory to store uploaded content. Needs to be writable by the user that
# grails runs as. Will be created automatically if necessary.
modulus.uploadDestination=/tmp/uploads 

# Configuration for the MySQL database layer. This example is on MySQL host
# `localhost` in the database `modulus`. The schema will be generated auto-
# matically, the DB user just needs write priveleges. 
dataSource.url=jdbc:mysql://localhost/modulus
dataSource.username=modulus
dataSource.password=secret
```



Project Resources
-----

- Design and discussion: [Modulus on OpenMRS Talk][]
- Issue tracking: [Modulus on OpenMRS JIRA][]
- Continuous Integration / Deployment: [Modulus on OpenMRS CI][]
- Wiki Page: [OpenMRS Modules][]

[Modulus on OpenMRS Talk]: https://talk.openmrs.org/category/projects/modulus
[Modulus on OpenMRS JIRA]: https://tickets.openmrs.org/browse/MOD
[Modulus on OpenMRS CI]: https://ci.openmrs.org/browse/MOD-ULUS
[OpenMRS Modules]: http://go.openmrs.org/modulerepositoryproject

