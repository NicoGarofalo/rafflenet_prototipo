package com.rafflenet

class Sorteador {

    private String logoNegocio
    private String nombreRepresentante
    private Map<String,Sorteo> misSorteos

    static constraints = {
        nombreRepresentante blank: false, nullable: false
        misSorteos nullable: false
    }

    Author() {
        //Execute post creation code
        logoNegocio = ""
        nombreRepresentante = ""
        misSorteos = []
    }

    // Author(String _name) {
    //     name = _name

    //     //Execute post creation code
    // }

    def crearSorteo( String descripPremio, String imgPremio, int durDias, 
    int tipo, String tematicas) {

        Sorteo nuevoSorteo = new Sorteo(
            descripcionPremio: descripPremio,
            imagenPremio: imgPremio,
            duracionDias: durDias,
            tipo: tipo,
            tematicas: tematicas,
            cuponesBeneficio: '',
            participantes: [],
            ganadorSorteo: null
        )

        misSorteos.add(nuevoSorteo)

        return nuevoSorteo
    }
    
    def decirNombre(){
        return nombreRepresentante
    }

    def finalizarSorteoManual() {}

    def obtenerGanador() {}

    def canjearCupon() {}

    def obtenerSorteo() {}

}
