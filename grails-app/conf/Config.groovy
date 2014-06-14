import org.openmrs.modulus.oauth.OpenMrsIdApi

// locations to search for config files that get merged into the main config;
// config files can be ConfigSlurper scripts, Java properties files, or classes
// in the classpath in ConfigSlurper format

grails.config.locations = [ "classpath:${appName}-config.properties",
                            "classpath:${appName}-config.groovy",
                            "file:${userHome}/.grails/${appName}-config.properties",
                            "file:${userHome}/.grails/${appName}-config.groovy",
                            "file:/opt/${appName}/${appName}-config.properties",
                            "file:/opt/${appName}/${appName}-config.groovy"]

if (System.properties["${appName}.config.location"]) {
    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
}

grails.project.groupId = "org.openmrs.modulus" // change this to alter the default package name and Maven publishing destination

grails.app.context = '/'

// The ACCEPT header will not be used for content negotiation for user agents containing the following strings (defaults to the 4 major rendering engines)
grails.mime.disable.accept.header.userAgents = ['Gecko', 'WebKit', 'Presto', 'Trident']
grails.mime.types = [ // the first one is the default format
    all:           '*/*', // 'all' maps to '*' or the first available format in withFormat
    atom:          'application/atom+xml',
    css:           'text/css',
    csv:           'text/csv',
    form:          'application/x-www-form-urlencoded',
    html:          ['text/html','application/xhtml+xml'],
    js:            'text/javascript',
    json:          ['application/json', 'text/json'],
    multipartForm: 'multipart/form-data',
    rss:           'application/rss+xml',
    text:          'text/plain',
    hal:           ['application/hal+json','application/hal+xml'],
    xml:           ['text/xml', 'application/xml']
]

// Workaround for http://jira.grails.org/browse/GRAILS-10392, disables all UA sniffing
grails.mime.disable.accept.header.userAgents = ['None']

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// What URL patterns should be processed by the resources plugin
grails.resources.adhoc.patterns = ['/images/*', '/css/*', '/js/*', '/plugins/*']
grails.resources.adhoc.excludes = ['**/WEB-INF/**','**/META-INF/**']

// Legacy setting for codec used to encode data with ${}
grails.views.default.codec = "html"

// The default scope for controllers. May be prototype, session or singleton.
// If unspecified, controllers are prototype scoped.
grails.controllers.defaultScope = 'singleton'

// GSP settings
grails {
    views {
        gsp {
            encoding = 'UTF-8'
            htmlcodec = 'xml' // use xml escaping instead of HTML4 escaping
            codecs {
                expression = 'html' // escapes values inside ${}
                scriptlet = 'html' // escapes output from scriptlets in GSPs
                taglib = 'none' // escapes output from taglibs
                staticparts = 'none' // escapes output from static template parts
            }
        }
        // escapes all not-encoded output at final stage of outputting
        filteringCodecForContentType {
            //'text/html' = 'html'
        }
    }
}
 
grails.converters.encoding = "UTF-8"
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart=false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.cache.queries = false

environments {
    development {
        grails.logging.jul.usebridge = true
        grails.serverURL = "http://localhost:8080"
    }
    production {
        grails.logging.jul.usebridge = false
        grails.serverURL = "http://modules.openmrs.org"
    }
    heroku {
        grails.logging.jul.usebridge = false
        grails.serverURL = "http://modulus.herokuapp.com"
    }
}

// log4j configuration
log4j = {
    // Example of changing the log pattern for the default console appender:
    //
    //appenders {
    //    console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
    //}

    error  'org.codehaus.groovy.grails.web.servlet',        // controllers
           'org.codehaus.groovy.grails.web.pages',          // GSP
           'org.codehaus.groovy.grails.web.sitemesh',       // layouts
           'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
           'org.codehaus.groovy.grails.web.mapping',        // URL mapping
           'org.codehaus.groovy.grails.commons',            // core / classloading
           'org.codehaus.groovy.grails.plugins',            // plugins
           'org.codehaus.groovy.grails.orm.hibernate',      // hibernate integration
           'org.springframework',
           'org.hibernate',
           'net.sf.ehcache.hibernate'

    debug  'grails.app.controllers.org.openmrs.modulus'
    debug  'grails.app.domain.org.openmrs.modulus'
    debug  'grails.app.services.org.openmrs.modulus'
    info   'grails.app.conf'

    info  'org.openmrs.modulus.servlet.LegacyFindModuleServlet'
    info  'org.openmrs.modulus.oauth.ModulusUserApprovalHandler'
}

// DBM (database migration) config
grails.plugin.databasemigration.updateOnStart = true
grails.plugin.databasemigration.updateOnStartFileNames = ["changelog.groovy"]
grails.plugin.databasemigration.autoMigrateScripts = ['RunApp']

environments {
    test { grails.plugin.databasemigration.updateOnStart = false }
}

modulus.uploadDestination = "/tmp/modulus_uploads"

// Searchable config
searchable {
    // disable the plugin's operations here.  we'll re-enable them in the Bootstrap to avoid conflicts with database-migration
    mirrorChanges = false
    bulkIndexOnStartup = false
}


// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'org.openmrs.modulus.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'org.openmrs.modulus.UserRole'
grails.plugin.springsecurity.authority.className = 'org.openmrs.modulus.Role'
grails.plugin.springsecurity.controllerAnnotations.staticRules = [
	'/':                              ['permitAll'],
	'/index':                         ['permitAll'],
	'/index.gsp':                     ['permitAll'],
	'/**/js/**':                      ['permitAll'],
	'/**/css/**':                     ['permitAll'],
	'/**/images/**':                  ['permitAll'],
	'/**/favicon.ico':                ['permitAll'],
    '/oauth/authorize.dispatch':      ['IS_AUTHENTICATED_REMEMBERED'],
    '/oauth/token.dispatch':          ['IS_AUTHENTICATED_REMEMBERED'],
]
grails.plugin.springsecurity.rejectIfNoRule = false
grails.plugin.springsecurity.fii.rejectPublicInvocations = false


// OAuth Providers -- how Modulus gets user authorizations (e.g. OpenMRS ID)
// These keys are demos, and are NOT used in production :-)
oauth {
    providers {
        openmrsid {
            api = OpenMrsIdApi
            key = 'd6f53aa0-dba1-11e3-be19-23bb5232b6a9'
            secret = 'FeNiHVUQadHN'
            successUri = "${grails.serverURL}/login/success"
            failureUri = "${grails.serverURL}/login/failure"
            callback = "${grails.serverURL}/oauth/openmrsid/callback"
            scope = 'profile'
        }
    }
}
grails.plugin.springsecurity.oauth.domainClass = 'org.openmrs.modulus.OAuthID'

// Default roles
grails.plugin.springsecurity.oauth.registration.roleNames = ["ROLE_USER"]

// OAuth Clients -- the authorization Modulus facilitates (e.g. Modulus UI)
grails.plugin.springsecurity.providerNames = [ // Do we need the top three?
//        'daoAuthenticationProvider',
//        'anonymousAuthenticationProvider',
//        'rememberMeAuthenticationProvider',
        'clientCredentialsAuthenticationProvider'
]
grails.plugin.springsecurity.oauthProvider.defaultClientConfig = [
        authorizedGrantTypes: ["implicit", "authorization_code", "refresh_token"]
]
grails.plugin.springsecurity.oauthProvider

// TODO: move to an external file
// These keys are demos, and are NOT used in production :-)
grails.plugin.springsecurity.oauthProvider.clients = [
        [
                clientId: "8fa0753531217077ab449c37a4d0bd5b",
                clientSecret: "d43a6222569a5930f7ddc5ef669ed9b1",
                registeredRedirectUri: ["http://example.com",
                                        "http://localhost:8083/auth-success.html"],
                additionalInformation: [
                        name: "OpenMRS Modules",
                        preApproved: true
                ]
        ]
]