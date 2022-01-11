package com.rafflenet

class ParticipanteController {

    def ParticipanteService participanteService
    def sorteos = Sorteo.list()

    def index() {
        [
            sorteoActual: sorteos,
            cantSorteos: sorteos.size(),
            participante: Usuario.get(1)
        ]
    }

    def participar(Long usuarioId, Long sorteoId) {
        participanteService.participarSorteo(usuarioId,sorteoId);
    }
}