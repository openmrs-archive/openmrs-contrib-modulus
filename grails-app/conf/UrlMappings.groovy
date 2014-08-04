class UrlMappings {

	static mappings = {
        "/api/users"(resources: "user")
        "/api/users/current"(controller: "currentUser", excludes: ["save", "delete"])

        "/api/releases"(resources: "release")
        "/api/screenshots"(resources: "screenshot")

        "/api/modules"(resources: "module") {

            "/releases"(resources: "release")
            "/releases/upload"(controller: "release", action: "uploadNew", parseRequest: false)

            "/tags"(resources: "tag")
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

        "/login"(controller: "login", action: "login")
        "/login/success"(controller: "login", action: "success")
        "/login/failure"(controller: "login", action: "failure")

        "/$controller/$action?/$id?"()

	}
}
