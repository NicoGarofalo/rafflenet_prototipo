package com.rafflenet

import grails.gorm.transactions.Transactional

@Transactional
class SorteoService {

    def participarSorteo() {

    }

    def finalizarSorteoManual(int idVinculo) {

        Vinculo vinculo = Vinculo.get(idVinculo)
        Usuario ganadorSorteo = vinculo.finalizarSorteo()

        NotificacionService notificaciones = new NotificacionService()
        notificaciones.notificarFinalizacionAParticipantes(idVinculo)

    }
}
