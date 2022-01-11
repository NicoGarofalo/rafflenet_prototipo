package com.rafflenet

class ParticipanteController {

    def sorteos = Sorteo.list();
    def sorteoActual;
    def contAct = 0;

    def index() {
        [
            sorteoActual: sorteos,
            contActual: contAct
        ]
    }
    def getSorteo() {
        if(contAct >= sorteos.size()) render (view:'index', model:[sorteoActual: sorteos[contAct]])
        sorteoActual = sorteos[contAct]
        contAct++
        render (view:'index', model:[sorteoActual: sorteos[contAct]])
    }

    def test(){
        render "hola de nuevo"
    }
}