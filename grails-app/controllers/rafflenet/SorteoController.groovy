package rafflenet

class SorteoController {

    def index() {
        [
            sorteos: Sorteo.list()
        ]
    }
}
