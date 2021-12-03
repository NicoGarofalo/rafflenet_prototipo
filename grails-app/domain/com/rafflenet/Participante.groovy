package com.rafflenet

class Participante {

    Set<Sorteo> misSorteos = []
    //Set<CuponBeneficio> misCupones = []
    Set<Tematica> tematicas = []
    

    static constraints = {
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

}
