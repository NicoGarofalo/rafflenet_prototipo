package com.rafflenet

class Usuario {

    String nombre
    String constrasenia
    String email
    String telefono
    int rol
    Set<Participante> participantes = []
    Set<Sorteador> sorteadores = []

    static constraints = {
        nombre blank: false, nullable: false
        constrasenia blank: false, nullable: false
        email email: true, blank: false, nullable: false, unique: true
        telefono blank: false, nullable: false, unique: true
        rol nullable: false
    }

    def cambiarRol() {
        
        // rol == 0 ? rol = 1 : rol = 0

    }

}
