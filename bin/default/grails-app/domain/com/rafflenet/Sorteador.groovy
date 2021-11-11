package com.rafflenet

class Sorteador {

    private String logoNegocio
    private String nombreRepresentante
    public Set<Sorteo> misSorteos = []

    // static hasMany = [
    //     misSorteos: Sorteo,
    // ]

    static constraints = {
        nombreRepresentante blank: false, nullable: false
        misSorteos nullable: false
    }

    def crearSorteo( String descripPremio, String imgPremio, int durDias, 
    int tipo, Set<Tematica> tematicas,int limiteParticipante, String localidad, String descripSorteo) {

        DetalleSorteo nuevoDetalle = new DetalleSorteo(
            limiteParticipante: limiteParticipante,
            localidad: localidad,
            descripSorteo: descripSorteo
        )

        Sorteo nuevoSorteo = new Sorteo(
            descripcionPremio: descripPremio,
            imagenPremio: imgPremio,
            duracionDias: durDias,
            tipo: tipo,
            tematicas: tematicas,
            cuponesBeneficio: "",
            participantes: [],
            ganadorSorteo: "",
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

    def finalizarSorteoManual() {

    }

    def obtenerGanador() {}

    def canjearCupon(Sorteo sorteo, String codigoCupon) {
        CuponBeneficio cupon = sorteo.obtenerCupon(codigoCupon)
        if(!cupon)  return false
        
        //aca iria un verificarVencimiento()
        return cupon.canjear()
    }

    def obtenerSorteo() {}

}
