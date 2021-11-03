package com.rafflenet

class Participante {

    private String localidad
    private int codigoPostal
    private Map<String,Sorteo> misSorteos
    private String misCupones
    private String tematicas
    

    static constraints = {
        localidad blank: false, nullable: false
        codigoPostal min: 4, blank: false, nullable: false, unique: true
        misSorteos nullable: false
        misCupones nullable: false
        tematicas nullable: false
    }

    def participar(Sorteo sorteo) {

        misSorteos << sorteo
        
    }
    
    def elegirTematica() {}

    def eliminarTematica() {}

    def abandonar() {}

}
