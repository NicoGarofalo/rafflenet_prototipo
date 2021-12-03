package com.rafflenet

class Participacion {

    Usuario usuario
    Sorteo sorteo
    Cupon miCupon
    Set<Tematica> tematicasEncomun = []

    static constraints = {
    }

    def participar(Usuario usuario, Sorteo sorteo) {

        this.usuario = usuario
        this.sorteo = sorteo
        this.miCupon = sorteo.CrearCupon(usuario)

        //this.tematicasEncomun = matchearTematicas(usuario.tematicas, sorteo.teamticas)

        srteo.agregarParticipante(usuario)

    }

    

}
