package com.rafflenet
import java.lang.Math
import java.time.*

class Sorteo {

    String descripcionPremio
    String imagenPremio
    LocalDate fechaVencimiento
    int tipo
    Set<Tematica> tematicas = []
    Set<Cupon> cupones = []
    Set<Usuario> Usuarios = []
    Usuario ganadorSorteo
    int estado = 0
    DetalleSorteo detalle

    static constraints = {
        descripcionPremio blank: false, nullable: false
        imagenPremio blank: false, nullable: false
        fechaVencimiento blank: false, nullable: false
        tipo blank: false, nullable: false
        tematicas blank: false, nullable: false
        detalle nullable: false
    }

    def finalizar() {
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
    def setCupones(Set<Cupon> cupones) {
        this.cupones = cupones
    }
    //Para tests
    def obtenerCupones(Set<Cupon> cupones) {
        return this.cupones
    }

    def agregarParticipante(Usuario participante) {
        participantes << participante
        this.detalle.cantVisualizaciones += 1
        this.detalle.cantParticipantesActual += 1
    }

    def obtenerCantidadParticipante() {
        return participantes.size()
    }

    def crearCupon() {
        Cupon cupon = new Cupon(
            codigoCupon: "4AK3L3O",
            descripcionCupon: "Descripcion test 1 del cupon",
            fechaVencimiento: new Date(),
            estado: 1 // Vigente
        )
        cupones << cupon

        return cupon
    }
    // Para test
    def crearCuponCanjeado() {
        Cupon cupon = new Cupon(
            codigoCupon: "4AK2A66",
            descripcionCupon: "Descripcion test 2 del cupon",
            fechaVencimiento: new Date(),
            estado: 2 // Canjeado
        )
        cupones << cupon

        return cupon
    }
    // Para test
    def crearCuponVencido() {
        Cupon cupon = new Cupon(
            codigoCupon: "4AK6U97",
            descripcionCupon: "Descripcion test 3 del cupon",
            fechaVencimiento: new Date(),
            estado: 3 // Vencido
        )
        cupones << cupon

        return cupon
    }

    def obtenerCupon(String codigoCupon) {
        return cupones.find{cupon -> cupon.codigoCupon == codigoCupon}
    }

    def generarEstadisticaPonderacionPorTematica() {
        return this.detalle.generarEstadisticaPonderacionPorTematica(this.tematicas, this.participantes)
    }

    def generarEstadisticaCuponVigenteVsCanjeado() {
        return this.detalle.generarEstadisticaCuponVigenteVsCanjeado(this.cupones)
    }

}
