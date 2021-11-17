package com.rafflenet

class Tematica {

    String nombre
    Set<Tematica> tematicasRelacionadas = []

    static constraints = {
    }

    def vincular() {}

    def desvincunlar() {}
}
