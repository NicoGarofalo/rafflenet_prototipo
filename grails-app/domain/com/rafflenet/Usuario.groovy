package com.rafflenet

class Usuario {

    String nombre
    String constrasenia
    String email
    String telefono
    int rol
    String logoNegocio
    String nombreRepresentante
    Set<Tematica> tematicas = []
    Set<Vinculo> misVinculos = []

    static constraints = {
        nombre blank: false, nullable: false
        constrasenia blank: false, nullable: false
        email email: true, blank: false, nullable: false, unique: true
        telefono blank: false, nullable: false, unique: true
        rol nullable: false
    }

    def obtenerCantidadParticipaciones() {
        return participaciones.size()
    }

    def cantidadAdministrdorSorteo() {
        return administradores.size()
    }

    def elegirTematica(Tematica tmtica) {}

    def eliminarTematica(Tematica tmtica) {}

}
