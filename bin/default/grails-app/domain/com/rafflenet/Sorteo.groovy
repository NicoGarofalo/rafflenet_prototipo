package com.rafflenet
import java.lang.Math
import java.time.*

class Sorteo {

    String descripcionPremio
    String imagenPremio
    LocalDate fechaVencimiento
    int tipo
    Set<Tematica> tematicas = []
    Set<Usuario> participantes = []
    Set<Cupon> cupones = []
    Usuario ganadorSorteo
    int estado = 0
    EstadisticaSorteo estadistica

    static constraints = {
        descripcionPremio blank: false, nullable: false
        imagenPremio blank: false, nullable: false
        fechaVencimiento blank: false, nullable: false
        tipo blank: false, nullable: false
        tematicas blank: false, nullable: false
        estadistica nullable: false
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
        this.participantes << participante
        this.estadistica.cantVisualizaciones += 1
        this.estadistica.cantParticipantesActual += 1
    }

    def obtenerCantidadParticipante() {
        return this.participantes.size()
    }

    def crearCupon() {
        Cupon cupon = new Cupon(
            descripcionCupon: "Descripcion test 1 del cupon",
            fechaVencimiento: LocalDate.now().plusDays(5),
            estado: 1 // Vigente
        )
        cupon.generarCodigo()
        this.cupones << cupon

        return cupon
    }
    // Para test
    def crearCuponCanjeado() {
        Cupon cupon = new Cupon(
            descripcionCupon: "Descripcion test 2 del cupon",
            fechaVencimiento: LocalDate.now().plusDays(5),
            estado: 2 // Canjeado
        )
        cupon.generarCodigo()
        this.cupones << cupon

        return cupon
    }
    // Para test
    def crearCuponVencido() {
        Cupon cupon = new Cupon(
            descripcionCupon: "Descripcion test 3 del cupon",
            fechaVencimiento:  LocalDate.now(),
            estado: 3 // Vencido
        )
        cupon.generarCodigo()
        this.cupones << cupon

        return cupon
    }

    def obtenerCupon(String codigoCupon) {
        return this.cupones.find{cupon -> cupon.codigo == codigoCupon}
    }

    def generarEstadisticaPonderacionPorTematica() {
        return this.estadistica.generarEstadisticaPonderacionPorTematica(this.tematicas, this.participantes)
    }

    def generarEstadisticaCuponVigenteVsCanjeado() {
        return this.estadistica.generarEstadisticaCuponVigenteVsCanjeado(this.cupones)
    }

}
