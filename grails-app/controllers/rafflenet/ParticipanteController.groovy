package com.rafflenet

class ParticipanteController {

    def sorteos = Sorteo.list();
    def sorteoActual;
    def contAct = 0;

    def index() {
        [
            sorteoActual: Sorteo.list()
        ]
    }

    // def index() {
    //     [
    //         sorteos: Sorteo.list()
    //     ]
    // }
    def getSorteo() {
        // contAct = 0;
        // if(contAct >= sorteos.size()) return sorteoActual;
        // contAct++; 
        // println contAct;
        // println "Hola!";
        // println sorteoActual
        // sorteoActual = sorteos[contAct];
        // return sorteoActual;
        return Sorteo.list()[0];
    }
}