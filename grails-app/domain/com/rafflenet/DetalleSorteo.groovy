package com.rafflenet

class DetalleSorteo {

    Date fechaCreacion = new Date()
    int limiteParticipante
    int cantParticipantesActual = 0
    int cantVisualizaciones = 0
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

    def generarEstadisticaCuponVigenteVsCanjeado(Set<CuponBeneficio> cuponesBeneficio) {
        
        def estadoCuponesBeneficio = ["Vigentes":0,"Canjeados":0]

        for (c in cuponesBeneficio) {
            if (c.estado == 2)
                estadoCuponesBeneficio["Canjeados"] += 1
            if (c.estado == 1)
                estadoCuponesBeneficio["Vigentes"] += 1
        }
        
        int totalCupones = cuponesBeneficio.size()
        return estadoCuponesBeneficio.each{it.value = Math.round(it.value / totalCupones * 100)}
    }
}
