package com.rafflenet

class Tematica {

    String nombre
    Set<Tematica> tematicasRelacionadas = []

    static constraints = {
        nombre blank: false, nullable: false, unique: true
    }

    def vincular() {}

    def desvincunlar() {}
}
