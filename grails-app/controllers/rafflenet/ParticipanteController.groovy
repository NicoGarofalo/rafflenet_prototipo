package com.rafflenet

class ParticipanteController {

    def sorteos = Sorteo.list();
    def sorteoActual;
    def contAct = 0;

    def index() {
        [
            sorteoActual: getSorteo()
        ]
    }
    def getSorteo() {
        if(contAct >= sorteos.size()) return sorteoActual;
        sorteoActual = sorteos[contAct];
        contAct++;
        return sorteoActual;
    }
}