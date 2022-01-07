package com.rafflenet

class SorteoController {

    def index() {
        [
            sorteos: Sorteo.list()
        ]
    }
}
