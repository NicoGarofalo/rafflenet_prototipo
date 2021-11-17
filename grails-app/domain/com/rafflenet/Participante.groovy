package com.rafflenet

class Participante {

    String localidad
    int codigoPostal
    Set<Sorteo> misSorteos = []
    // private Set<CuponBeneficio> misCupones = []
    Set<Tematica> tematicas = []
    

    static constraints = {
        localidad blank: false, nullable: false
        codigoPostal min: 4, blank: false, nullable: false, unique: true
        misSorteos nullable: false
    }

    def participar(Sorteo sorteo) {
        misSorteos << sorteo
    }
    
    def obtenerCantidadSorteos() {
        return misSorteos.size()
    }

    def elegirTematica(Tematica tematicaElegido) {
        tematicas << tematicaElegido
    }

    def eliminarTematica() {}

    def abandonar() {}

}
