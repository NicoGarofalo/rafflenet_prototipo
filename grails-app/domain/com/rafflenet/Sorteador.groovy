package com.rafflenet
import java.time.*

class Sorteador {

    String logoNegocio
    String nombreRepresentante
    Set<Sorteo> misSorteos = []

    // static hasMany = [
    //     misSorteos: Sorteo,
    // ]

    static constraints = {
        nombreRepresentante blank: false, nullable: false
        misSorteos nullable: false
    }

    def crearSorteo( String descripPremio, String imgPremio, LocalDate fechaVencimiento, 
    int tipo, Set<Tematica> tematicas,int limiteParticipante, String descripSorteo) {

        DetalleSorteo nuevoDetalle = new DetalleSorteo(
            limiteParticipante: limiteParticipante,
            descripSorteo: descripSorteo
        )

        Sorteo nuevoSorteo = new Sorteo(
            descripcionPremio: descripPremio,
            imagenPremio: imgPremio,
            tipo: tipo,
            tematicas: tematicas,
            cuponesBeneficio: "",
            participantes: [],
            ganadorSorteo: "",
            fechaVencimiento: fechaVencimiento,
            detalle: nuevoDetalle
        )
        misSorteos << nuevoSorteo

        return nuevoSorteo
    }
    
    def mostrarDatosSorteador() {
        println this.dump()
    }

    def cantidadSorteos() {
        return misSorteos.size()
    }

    def finalizarSorteoManual() {}

    def obtenerGanador() {}

    def canjearCupon(Sorteo sorteo, String codigoCupon) {
        CuponBeneficio cupon = sorteo.obtenerCupon(codigoCupon)
        if(!cupon)  return 'Cupon no encontrado'
        
        //aca iria un verificarVencimiento()
        if (cupon.verificarVencimiento()) return 'Cupon vencido' 
        if (cupon.canjear()) {
            return 'Cupon canjeado exitosamente'
        } else {
            return 'Cupon ya canjeado'
        }

        return cupon.canjear()
    }

    def obtenerSorteo() {}

}
