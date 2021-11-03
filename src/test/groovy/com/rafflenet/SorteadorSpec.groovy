package com.rafflenet

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class SorteadorSpec extends Specification implements DomainUnitTest<Sorteador> {

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
        expect:"fix me"
            true == true
    }

    void "Test sorteo creado es el esperado"() {
        Sorteador sorteador = new Sorteador(logoNegocio:"",nombreRepresentante:"Nicolas",misSorteos: [])

        String descripPremio = "Premio1"
        String imgPremio = "ImgPremio"
        int durDias = 10 
        int tipo = 0
        String tematicas = "Tematica1"
        
        Sorteo sorteoEsperado = new Sorteo(
            descripcionPremio: descripPremio,
            imagenPremio: imgPremio,
            duracionDias: durDias,
            tipo: tipo,
            tematicas: tematicas,
            cuponesBeneficio: '',
            participantes: [],
            ganadorSorteo: null
        )
        Sorteo sorteoCreado = sorteador.crearSorteo(descripPremio,imgPremio, durDias, tipo, tematicas)
        
        expect: "Sorteo creado es el esperado"
            sorteoCreado == sorteoEsperado
    }
}
