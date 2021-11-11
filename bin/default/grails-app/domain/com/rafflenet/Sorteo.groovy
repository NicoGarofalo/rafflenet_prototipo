package com.rafflenet
import java.lang.Math

class Sorteo {

    private String descripcionPremio
    private String imagenPremio
    private int duracionDias
    private int tipo
    private Set<Tematica> tematicas = []
    private Set<CuponBeneficio> cuponesBeneficio = []
    private Set<Participante> participantes = []
    private Participante ganadorSorteo
    private int estado = 0
    public  DetalleSorteo detalle

    static constraints = {
        descripcionPremio blank: false, nullable: false
        duracionDias blank: false, nullable: false
        tipo blank: false, nullable: false
        tematicas blank: false, nullable: false
        detalle nullable: false
    }

    def generarGanador() {
        int rango = this.obtenerCantidadParticipante()
        int posicionGanador = (int) Math.random() * rango

        this.ganadorSorteo = participantes[posicionGanador]
        this.estado = 1 
        return participantes[posicionGanador]
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
