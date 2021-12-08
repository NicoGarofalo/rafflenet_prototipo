package rafflenet

class UrlMappings {

    static mappings = {
        "/visualizoUsers"(view: '/index')

        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
