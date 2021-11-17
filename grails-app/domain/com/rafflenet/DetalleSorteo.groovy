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

    def generarEstadisticaParticipanteVsVisualizaciones() {
        return cantParticipantesActual / cantVisualizaciones * 100
    }

    def generarEstadisticaPonderacionPorTematica(Set<Tematica> tematicas, Set<Participante> participantes) {

        def tematicasSorteo = [:]

        for (t in tematicas) {
            tematicasSorteo[t.nombre] = 0
        }
        
        for (p in participantes) {
            for (t in p.tematicas) {
                if (tematicasSorteo.containsKey(t.nombre))
                    tematicasSorteo[t.nombre] += 1
            }
        }
        return tematicasSorteo.each{it.value = Math.round(it.value / this.cantParticipantesActual * 100)}
    }
}
