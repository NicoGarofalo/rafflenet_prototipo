package com.rafflenet

class Sorteo {

    private String descripcionPremio
    private String imagenPremio
    private int duracionDias
    private int tipo
    private String tematicas
    private String cuponesBeneficio
    private Set<Participante> participantes = []
    private Participante ganadorSorteo
    public  DetalleSorteo detalle

    static constraints = {
        descripcionPremio blank: false, nullable: false
        duracionDias blank: false, nullable: false
        tipo blank: false, nullable: false
        tematicas blank: false, nullable: false
        detalle nullable: false
    }

    def generarGanador() {}

    def agregarParticipante(Participante participante) {
        participantes << participante
    }

    def obtenerCantidadParticipante() {
        return participantes.size()
    }

    def crearCuponBeneficio() {}

    def agregarTematica() {}

    def eliminarTematica() {}

    def canjearCupon() {}

}
