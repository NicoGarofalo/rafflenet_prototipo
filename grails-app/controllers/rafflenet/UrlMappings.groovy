package rafflenet

class UrlMappings {

    static mappings = {
        "/participante/home"(view: '/index')
        "/sorteador/home"(view: '/sorteo/index')
        "/login"(view: '/index')
        "/register"(view: '/index')

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
