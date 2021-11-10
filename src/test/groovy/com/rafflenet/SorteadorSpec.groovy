package com.rafflenet

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class SorteadorSpec extends Specification implements DomainUnitTest<Sorteador> {

    def setup() {
    }

    def cleanup() {
    }

// Incluimos US y CA Trivial con fines de práctica para Test Spock

// US Creación de sorteo no exprés: Como sorteador quiero crear un sorteo para que los participantes puedan inscribirse al mismo

// CA: Dado que el sorteador define los datos del sorteo: descripción e imagen del premio, duración del sorteo en días,
//     temáticas, limite de participantes, localidad, descripción del sorteo
//     Y que el tipo de sorteo seleccionado por el sorteador es no exprés
//     Y que todos los campos son obligatorios
//     Cuando el sorteador decide crear el sorteo
//     Entonces la aplicación verifica que todos los campos estan completos, crea el sorteo con fecha de creación
//     correspondiente al día actual y lo agrega a la lista de sorteos del sorteador


    void "Test Sorteador - Creación de sorteo no exprés"() {
        Sorteador sorteador = new Sorteador(logoNegocio:"", nombreRepresentante:"Nicolas", misSorteos:[:])

        sorteador.mostrarDatosSorteador()
        Date dateNow = new Date()

        given: 
            String descripPremio = "PremioTest1"
            String imgPremio = "ImgPremioTest1"
            int durDias = 10 
            int tipo = 0
            String tematicas = "TematicaTest1"
            int limiteParticipante = 100
            String localidad = "LocalidadTest1"
            String descripSorteo = "Sorteo interesante Test1"
        
        when:
            Sorteo sorteoCreado = sorteador.crearSorteo(descripPremio, imgPremio, durDias, tipo, tematicas,
             limiteParticipante, localidad, descripSorteo)

        then:
            descripPremio.equals(sorteoCreado.descripcionPremio)
            imgPremio.equals(sorteoCreado.imagenPremio)
            durDias.equals(sorteoCreado.duracionDias)
            tipo.equals(sorteoCreado.tipo)
            tematicas.equals(sorteoCreado.tematicas)
            limiteParticipante.equals(sorteoCreado.detalle.limiteParticipante)
            localidad.equals(sorteoCreado.detalle.localidad)
            descripSorteo.equals(sorteoCreado.detalle.descripSorteo)

            dateNow.year.equals(sorteoCreado.detalle.fechaCreacion.year)
            dateNow.month.equals(sorteoCreado.detalle.fechaCreacion.month)
            dateNow.day.equals(sorteoCreado.detalle.fechaCreacion.day)

            sorteador.cantidadSorteos().equals(1)
        
    }

    // void "Test 2 Sorteador finaliza sorteo manual correctamente"() {
    //     Sorteador sorteador = new Sorteador(logoNegocio:"", nombreRepresentante:"Nicolas", misSorteos:[:])
        
    //     String descripPremio = "Premio1"
    //     String imgPremio = "ImgPremio"
    //     int durDias = 10 
    //     int tipo = 0
    //     String tematicas = "Tematica1"

    //     sorteador.crearSorteo(descripPremio,imgPremio, durDias, tipo, tematicas)
    // }
}
