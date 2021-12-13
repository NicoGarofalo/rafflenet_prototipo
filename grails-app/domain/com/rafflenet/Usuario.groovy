package com.rafflenet

import java.time.*

class Usuario {    

    String nombre
    String constrasenia
    String email
    Rol rol
    String telefono
    String logoNegocio
    String nombreRepresentante
    Set<Tematica> tematicas = []
    Set<Vinculo> misVinculos = []

    static constraints = {
        nombre blank: false, nullable: false
        constrasenia blank: false, nullable: false
        email email: true, blank: false, nullable: false, unique: true
        telefono blank: false, nullable: false, unique: true
    }

    def obtenerCantidadVinculos() {
        return misVinculos.size()
    }

    def elegirTematica(Tematica tematica) {
        this.tematicas << tematica
    }

    def eliminarTematica(Tematica tematica) {

    }


    def crearSorteo( String descripPremio, String imgPremio, LocalDate fechaVencimiento, 
    int tipo, Set<Tematica> tematicas,int limiteParticipante, String descripSorteo) {

        if(this.rol == Rol.PARTICIPANTE) {
            throw new Exception("El usuario no es sorteador.")
        }

        EstadisticaSorteo nuevaEstadistica = new EstadisticaSorteo(
            limiteParticipante: limiteParticipante,
            descripcion: descripSorteo
        )

        Sorteo sorteo = new Sorteo(
            descripcionPremio: descripPremio,
            imagenPremio: imgPremio,
            tipo: tipo,
            tematicas: tematicas,
            participantes: [],
            ganadorSorteo: "",
            fechaVencimiento: fechaVencimiento,
            estadistica: nuevaEstadistica
        )

        return sorteo
    }

}
