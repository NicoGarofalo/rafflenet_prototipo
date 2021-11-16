package com.rafflenet
import java.lang.Math
import java.time.*

class Sorteo {

    String descripcionPremio
    String imagenPremio
    LocalDate fechaVencimiento
    int tipo
    Set<Tematica> tematicas = []
    Set<CuponBeneficio> cuponesBeneficio = []
    Set<Participante> participantes = []
    Participante ganadorSorteo
    int estado = 0
    DetalleSorteo detalle

    static constraints = {
        descripcionPremio blank: false, nullable: false
        tipo blank: false, nullable: false
        tematicas blank: false, nullable: false
        detalle nullable: false
    }

    def generarGanador() {
        int cantidadParticipantes = this.obtenerCantidadParticipante()
        if(cantidadParticipantes == 0){
            return null
        }
        int rango = cantidadParticipantes - 1
        int posicionGanador = (int) Math.random() * rango

        this.ganadorSorteo = participantes[posicionGanador]
        this.estado = 1 
        return participantes[posicionGanador]
    }

    def validarFecha(LocalDate fecha) {
        return fechaVencimiento <= fecha
    }

    //Para tests
    def setCuponesBeneficio(Set<CuponBeneficio> cupones) {
        this.cuponesBeneficio = cupones
    }
    //Para tests
    def obtenerCuponesBeneficio(Set<CuponBeneficio> cupones) {
        return this.cuponesBeneficio
    }

    def agregarParticipante(Participante participante) {
        participantes << participante
    }

    def obtenerCantidadParticipante() {
        return participantes.size()
    }

    def crearCuponBeneficio() {}

    def agregarTematica() {}

    def eliminarTematica() {}

    def obtenerCupon(String codigoCupon) {
        return cuponesBeneficio.find{cupon -> cupon.codigoCupon == codigoCupon}
    }

}
