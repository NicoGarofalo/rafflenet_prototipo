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

    def obtenerCantidadVinculos() {
        return misVinculos.size()
    }

    def elegirTematica(Tematica tematica) {
        this.tematicas << tematica
    }

    def eliminarTematica(Tematica tematica) {

    }

}
