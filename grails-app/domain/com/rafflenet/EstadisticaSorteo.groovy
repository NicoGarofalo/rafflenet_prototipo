package com.rafflenet

class EstadisticaSorteo {

    Date fechaCreacion = new Date()
    int limiteParticipante
    int cantParticipantesActual = 0
    int cantVisualizaciones = 0
    String descripcion

    static constraints = {
        limiteParticipante nullable: false
        descripcioo blank: false
    }

    def generarEstadisticaParticipanteVsVisualizaciones() {
        return cantParticipantesActual / cantVisualizaciones * 100
    }

    def generarEstadisticaPonderacionPorTematica(Set<Tematica> tematicas, Set<Usuario> participantes) {

        def tematicas = [:]

        for (t in tematicas) {
            tematicas[t.nombre] = 0
        }
        
        for (p in participantes) {
            for (t in p.tematicas) {
                if (tematicas.containsKey(t.nombre))
                    tematicas[t.nombre] += 1
            }
        }
        return tematicas.each{it.value = Math.round(it.value / this.cantParticipantesActual * 100)}
    }

    def generarEstadisticaCuponVigenteVsCanjeado(Set<Cupon> cupones) {
        
        def estadoCupones = ["Vigentes":0,"Canjeados":0]

        for (c in cupones) {
            if (c.estado == 2)
                estadoCupones["Canjeados"] += 1
            if (c.estado == 1)
                estadoCupones["Vigentes"] += 1
        }
        
        int totalCupones = cupones.size()
        return estadoCupones.each{it.value = Math.round(it.value / totalCupones * 100)}
    }
}
