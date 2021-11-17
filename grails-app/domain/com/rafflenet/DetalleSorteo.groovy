package com.rafflenet

class DetalleSorteo {

    Date fechaCreacion = new Date()
    int limiteParticipante
    int cantParticipantesActual = 0
    int cantVisualizaciones = 0
    String localidad
    String descripSorteo

    static constraints = {
    }

    def generarEstadisticaParticipanteVsVisualizaciones(){
        return cantParticipantesActual / cantVisualizaciones * 100
    }
}
