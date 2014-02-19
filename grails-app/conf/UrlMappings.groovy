class UrlMappings {

	static mappings = {
        // Default Grails mapping
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/api/users"(resources: "user")
        "/api/users/login"(controller: "user", action: "login")
        "/api/users/logout"(controller: "user", action: "logout")

        "/api/releases"(resources: "release")
        "/api/modules"(resources: "module")
        "/api/screenshots"(resources: "screenshot")

        name downloadResource: "/api/${controller}s/$id/download/$filename?"(action: "download")

//        "/api/upload/files/"(controller: "file", action: "uploadNewFile", parseRequest: false)
//        "/api/upload/files/$id"(controller: "file", action: "uploadToId", parseRequest: false)
        "/api/upload/$controller/"(action: "uploadNewFile", parseRequest: false)
        "/api/upload/$controller/$id"(action: "uploadToId", parseRequest: false)


        "/admin/module/$action?/$id?(.$format)?"(controller: "adminModule")
        "/admin/release/$action?/$id?(.$format)?"(controller: "adminRelease")
        "/admin/screenshot/$action?/$id?(.$format)?"(controller: "adminScreenshot")
        "/admin/user/$action?/$id?(.$format)?"(controller: "adminUser")



        "/"(view:"/index")
        "500"(view:'/error')
	}
}
