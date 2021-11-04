package com.rafflenet

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class SorteadorSpec extends Specification implements DomainUnitTest<Sorteador> {

    def setup() {
    }

    def cleanup() {
    }

    void "Test 1 sorteo creado es el esperado"() {
        Sorteador sorteador = new Sorteador(logoNegocio:"", nombreRepresentante:"Nicolas", misSorteos:[:])

        sorteador.mostrarDatosSorteador()

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
            cuponesBeneficio: "",
            participantes: [],
            ganadorSorteo: ""
        )

        Sorteo sorteoCreado = sorteador.crearSorteo(descripPremio,imgPremio, durDias, tipo, tematicas)


        expect: "Sorteo creado es el esperado"
            sorteoCreado.descripcionPremio.equals(sorteoEsperado.descripcionPremio)
            sorteoCreado.imagenPremio.equals(sorteoEsperado.imagenPremio)
            sorteoCreado.duracionDias.equals(sorteoEsperado.duracionDias)
            sorteoCreado.tipo.equals(sorteoEsperado.tipo)
            sorteoCreado.tematicas.equals(sorteoEsperado.tematicas)
    }

    void "Test 2 Sorteador finaliza sorteo manual correctamente"() {
        Sorteador sorteador = new Sorteador(logoNegocio:"", nombreRepresentante:"Nicolas", misSorteos:[:])
        
        String descripPremio = "Premio1"
        String imgPremio = "ImgPremio"
        int durDias = 10 
        int tipo = 0
        String tematicas = "Tematica1"

        sorteador.crearSorteo(descripPremio,imgPremio, durDias, tipo, tematicas)
    }
}
