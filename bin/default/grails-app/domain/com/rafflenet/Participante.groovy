package com.rafflenet

class Participante {

    private String localidad
    private int codigoPostal
    private Set<Sorteo> misSorteos = []
    // private Set<CuponBeneficio> misCupones = []
    // private Set<Tematica> tematicas = []
    

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

    def elegirTematica() {}

    def eliminarTematica() {}

    def abandonar() {}

}
