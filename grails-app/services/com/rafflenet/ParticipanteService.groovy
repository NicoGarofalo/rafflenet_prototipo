package com.rafflenet

import grails.gorm.transactions.Transactional

@Transactional
class ParticipanteService {

    def participarSorteo(Long participanteId, Long sorteoId) {
        Usuario participante = Usuario.get(participanteId)
        Sorteo sorteo = Sorteo.get(sorteoId)
        println "Algo deberia haber aca:"
        println participante.dump()
        println sorteo.dump()

        Vinculo vinculo = new Vinculo(participante,sorteo)
        vinculo.save(flush:true)
        println vinculo.dump()
        vinculo.vincular()
        // // participante.misVinculos << vinculo
        // return 'hola'
    }
}
