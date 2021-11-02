package com.rafflenet

class Usuario {

    private String nombre
    private String constrasenia
    private String email
    private String telefono
    private int rol
    private Participante participante
    private Sorteador sorteador

    static constraints = {
        nombre blank: false, nullable: false
        constrasenia min: 5, blank: false, nullable: false
        email email: true, blank: false, nullable: false unique: true
        telefono min: 18, blank: false, nullable: false unique: true
        rol nullable: false
    }

    def cambiarRol() {
        
        rol == 0 ? rol = 1 : rol = 0

    }

    // def participar() {
    //     rol == 0 && participante.participar()
    // }

    // def elegirTematica() {
    //     rol == 0 && participante.elegirTematica()
    // }

    // def eliminarTematica() {
    //     rol == 0 && participante.eliminarTematica()
    // }

    // def abandonar() {
    //     rol == 0 && participante.abandonar()
    // }

}
