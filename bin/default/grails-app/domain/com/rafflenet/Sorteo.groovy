package com.rafflenet

class Sorteo {

    private String descripcionPremio
    private String imagenPremio
    private int duracionDias
    private int tipo
    private String tematicas
    private String cuponesBeneficio
    private List<Participante> participantes
    private Participante ganadorSorteo

    static constraints = {
        descripcionPremio blank: false, nullable: false
        duracionDias blank: false, nullable: false
        tipo blank: false, nullable: false
        tematicas blank: false, nullable: false
    }

    def generarGanador() {}

    def agregarParticipante() {}

    def crearCuponBeneficio() {}

    def agregarTematica() {}

    def eliminarTematica() {}

    def canjearCupon() {}

}
