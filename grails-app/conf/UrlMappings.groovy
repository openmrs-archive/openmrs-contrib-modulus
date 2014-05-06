class UrlMappings {

	static mappings = {
        "/api/users"(resources: "user")
        "/api/users/login"(controller: "user", action: "login")
        "/api/users/logout"(controller: "user", action: "logout")

        "/api/releases"(resources: "release")
        "/api/screenshots"(resources: "screenshot")

        "/api/modules"(resources: "module") {

            "/releases"(resources: "release")
            "/releases/upload"(controller: "release", action: "uploadNew", parseRequest: false)
        }
        // has to be listed outside the nested block (for some reason)
        "/api/modules/${moduleId}/releases/upload/${id}"(controller: "release", action: "uploadExisting", parseRequest: false)

        "/api/search"(controller: "search", action:"search")

        name downloadResource: "/api/${controller}s/$id/download/$filename?"(action: "download")

        // Legacy RDF endpoint. See https://tickets.openmrs.org/browse/MOD-5
        "/modules/download/$id/update.rdf"(controller: "rdf", action: "showModuleRdf")

        "/feeds/$id/update.rdf"(controller: "rdf", action: "showModuleRdf")
        "/feeds/$id/update.rss"(controller: "feed", action: "showModuleUpdateFeed")
        "/feeds/all.rss"(conroller: "feed", action: "allUpdatesFeed")


        "/"(view:'/index')
        "500"(view:'/error')
        "404"(view:'/404')
        "403"(view:'/403')

	}
}
