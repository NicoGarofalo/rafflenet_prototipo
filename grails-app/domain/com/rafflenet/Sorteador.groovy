package com.rafflenet

class Sorteador {

    private String logoNegocio
    private String nombreRepresentante
    private Map<String,Sorteo> misSorteos

    static constraints = {
        nombreRepresentante blank: false, nullable: false
        misSorteos nullable: false
    }

    def crearSorteo( String descripPremio, String imgPremio, int durDias, 
    int tipo, String temtics) {

        Sorteo nuevoSorteo = new Sorteo(
            descripcionPremio: descripPremio,
            imagenPremio: imgPremio,
            duracionDias: durDias,
            tipo: tipo,
            tematicas: temtics,
            cuponesBeneficio: ''
            participantes: []
            ganadorSorteo: null
        )

        return nuevoSorteo
    }
    
    def finalizarSorteoManual() {}

    def obtenerGanador() {}

    def canjearCupon() {}

    def obtenerSorteo() {}

}
