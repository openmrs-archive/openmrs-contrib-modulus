MODULUS
=====

A repository of add-ons for the [OpenMRS platform](https://wiki.openmrs.org/x/RAEr). This repository contains the backend of the OpenMRS Modules directory at [http://modules.openmrs.org](http://modules.openmrs.org).


QUICK LINKS
-----

**API Documentation**: [Browse the API Docs][]

**Frontend UI Application**: [Modulus UI][]

**Design and Discussion**: [Modulus on OpenMRS Talk][]

**Dev Environment Setup Guide**: [How to install Modulus and Modulus UI][guide] (covers setting up a Modulus server, OpenMRS ID OAuth, and the Modulus UI frontend)

-----

Modulus provides a data model and a REST API for representing OpenMRS modules. [Modulus UI] is developed separately, and provides the front-end web application that consumes this REST api.

Building
-----

To prepare a dev environment:

1. Build requirements: MySQL, Java EE JDK 7. **As of early 2016, only JDK 7 is supported. We'll need to update our version of Grails to move to JDK 8.** **You may experience problems running on a server with less than 2GB RAM.**
2. Install **Grails 2.3.7**. [sdkman][] is the simplest way:
       
        $ sdk install grails 2.3.7

3. Clone this repo and its submodules:

        $ git clone https://github.com/openmrs/openmrs-contrib-modulus.git
        $ cd openmrs-contrib-modulus
        $ git submodule init
        $ git submodule update
        
4. Create a MySQL database:

        $ mysql -u root -p -e "CREATE DATABASE modulus"

5. Configure Modulus. Modulus looks for config files in `~/.grails/modulus-config.groovy`, `/opt/modulus/modulus-config.groovy`, or in the classpath. It will also look for `.properties` files in the same locations. Custom config locations can be passed with `-Dmodulus.config.location=CONFIG_PATH` as a run argument. See example config file below.

6. Run grails. The final command will start a dev server:

        $ grails clean
        $ grails refresh-dependencies
        $ grails run-app
        
**NOTE:** Modulus only provides the back-end and REST API of the OpenMRS Modules application. To get an actual web interface for Modulus, build and install [Modulus UI][].
        
[sdkman]: http://sdkman.io/
[Modulus-UI]: https://github.com/openmrs/openmrs-contrib-modulus-ui

OAuth
-----

To sign in to Modulus and Modulus-UI, you need an OAuth key with an [OpenMRS ID](https://github.com/openmrs/openmrs-contrib-id) server. You can set up a local ID server, or [request a key from the server at id.openmrs.org](http://om.rs/oauthrequest).

Once you have this key, add it to your `modulus-config.groovy` file via the `oauth.providers.openmrsid` object. See example below:

Example `modulus-config.groovy`:
-----

```groovy
import org.openmrs.modulus.oauth.OpenMrsIdApi

grails.serverURL = "http://localhost:8080"
modulus {
       uploadDestionation = "/tmp/uploads"
       openmrsid.hostname = "https://id.openmrs.org"
}

// Overrides the default configuration in grails-app/conf/DataSource.groovy
dataSource.url = "jdbc:mysql://localhost/modulus"
dataSource.username = "root"
dataSource.password = ""

// OpenMRS ID Provider. These keys correspond to keys issued by your OpenMRS ID server.
oauth.providers.openmrsid = [
        api: OpenMrsIdApi,
        key: "YOUR_OAUTH_APPLICATION_KEY",
        secret: "YOUR_OAUTH_SECRET",
        successUri: "${grails.serverURL}/login/success",
        failureUri: "${grails.serverURL}/login/failure",
        callback: "${grails.serverURL}/oauth/openmrsid/callback",
        scope: "profile"
]

// Modulus UI Client. These keys must be known by a Modulus UI client to connect.
grails.plugin.springsecurity.oauthProvider.clients = [
        [
                clientId: "modulus123",
                clientSecret: "modulus123",
                registeredRedirectUri: ["http://localhost:8083/auth-success.html"],
                additionalInformation: [
                        name: "Dev Modulus UI",
                        preApproved: true
                ]
        ]
]
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
[Modulus UI]: https://github.com/openmrs/openmrs-contrib-modulus-ui
[Browse the API Docs]: https://openmrs.github.io/openmrs-contrib-modulus/
[guide]: https://wiki.openmrs.org/x/R5K-B
