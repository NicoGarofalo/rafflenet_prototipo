package com.rafflenet

class Sorteador {

    private String logoNegocio
    private String nombreRepresentante
    private Set<Sorteo> misSorteos

    static constraints = {
        nombreRepresentante blank: false, nullable: false
        misSorteos nullable: false
    }

    // Sorteador(String logoNegocio, String nombreRepresentante) {
    //     //Execute post creation code
    //     logoNegocio = logoNegocio == null ? "" : logoNegocio
    //     nombreRepresentante = nombreRepresentante == null ? "" : nombreRepresentante
    //     misSorteos=[:]
    // }

    // Sorteador(String _logoNegocio, String _nombreRepresentante) {
    //     this.logoNegocio = _logoNegocio ? "" : _logoNegocio
    //     this.nombreRepresentante = _nombreRepresentante ? "" : _nombreRepresentante
    //     this.misSorteos=[:]
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
            cuponesBeneficio: "",
            participantes: [],
            ganadorSorteo: ""
        )
        //misSorteos.add(nuevoSorteo)

        return nuevoSorteo
    }
    
    def mostrarDatosSorteador(){
        println this.dump()
    }

    def finalizarSorteoManual() {}

    def obtenerGanador() {}

    def canjearCupon() {}

    def obtenerSorteo() {}

}
