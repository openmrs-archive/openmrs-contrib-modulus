class UrlMappings {

	static mappings = {
        "/api/users"(resources: "user")
        "/api/users/login"(controller: "user", action: "login")
        "/api/users/logout"(controller: "user", action: "logout")

        "/api/releases"(resources: "release")
        "/api/screenshots"(resources: "screenshot")

        /*"/api/modules"(resources: "module") {

            "/releases"(resources: "release")
            "/releases/upload"(controller: "release", action: "uploadNew", parseRequest: false)
        }*/
        // has to be listed outside the nested block (for some reason)
//        "/api/modules/${moduleId}/releases/upload/${id}"(controller: "release", action: "uploadExisting", parseRequest: false)

        name downloadResource: "/api/${controller}s/$id/download/$filename?"(action: "download")

        // Deprecated
        "/api/upload/$controller/"(action: "uploadNew", parseRequest: false)
        "/api/upload/$controller/$id"(action: "uploadExisting", parseRequest: false)


        "/admin/module/$action?/$id?(.$format)?"(controller: "adminModule")
        "/admin/release/$action?/$id?(.$format)?"(controller: "adminRelease")
        "/admin/screenshot/$action?/$id?(.$format)?"(controller: "adminScreenshot")
        "/admin/user/$action?/$id?(.$format)?"(controller: "adminUser")


        "/"(redirect: "/ui/app")
//        "500"(view:'/error')

        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }
	}
}
