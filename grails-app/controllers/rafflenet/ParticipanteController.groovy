package com.rafflenet

class ParticipanteController {

    def index() {
        [
            sorteos: Sorteo.list()
        ]
    }
}