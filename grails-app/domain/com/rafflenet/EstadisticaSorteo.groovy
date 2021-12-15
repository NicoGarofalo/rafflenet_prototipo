package com.rafflenet

class EstadisticaSorteo {

    Date fechaCreacion = new Date()
    int limiteParticipante
    int cantParticipantesActual = 0
    int cantVisualizaciones = 0
    String descripcion

    static constraints = {
        limiteParticipante nullable: false
        descripcion blank: false
    }

    def generarEstadisticaParticipanteVsVisualizaciones() {
        return cantParticipantesActual / cantVisualizaciones * 100
    }

    def generarEstadisticaPonderacionPorTematica(Set<Tematica> tematicas, Set<Usuario> participantes) {

        def estadisticasTematicas = [:]

        for (t in tematicas) {
            estadisticasTematicas[t.nombre] = 0
        }
        
        for (p in participantes) {
            for (t in p.tematicas) {
                if (estadisticasTematicas.containsKey(t.nombre))
                    estadisticasTematicas[t.nombre] += 1
            }
        }
        println estadisticasTematicas
        return estadisticasTematicas.each{it.value = Math.round(it.value / this.cantParticipantesActual * 100)}
    }

    def generarEstadisticaCuponVigenteVsCanjeado(Set<Cupon> cupones) {
        
        def estadoCupones = ["Vigentes":0,"Canjeados":0]

        for (c in cupones) {
            if (c.estaCanjeado())
                estadoCupones["Canjeados"] += 1
            if (c.estaVigente())
                estadoCupones["Vigentes"] += 1
        }
        
        int totalCupones = cupones.size()
        return estadoCupones.each{it.value = Math.round(it.value / totalCupones * 100)}
    }
}
