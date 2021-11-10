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
            int limiteParticipante = 100
            String localidad = "LocalidadTest1"
            String descripSorteo = "Sorteo interesante Test1"

            Tematica tematica1 = new Tematica(
                nombre: "TematicaTest1"
            )
            Set<Tematica> tematicas = [tematica1]
        
        when:
            Sorteo sorteoCreado = sorteador.crearSorteo(descripPremio, imgPremio, durDias, tipo, tematicas,
             limiteParticipante, localidad, descripSorteo)

        then:
            descripPremio.equals(sorteoCreado.descripcionPremio)
            imgPremio.equals(sorteoCreado.imagenPremio)
            durDias.equals(sorteoCreado.duracionDias)
            tipo.equals(sorteoCreado.tipo)
            limiteParticipante.equals(sorteoCreado.detalle.limiteParticipante)
            localidad.equals(sorteoCreado.detalle.localidad)
            descripSorteo.equals(sorteoCreado.detalle.descripSorteo)
            dateNow.year.equals(sorteoCreado.detalle.fechaCreacion.year)
            dateNow.month.equals(sorteoCreado.detalle.fechaCreacion.month)
            dateNow.day.equals(sorteoCreado.detalle.fechaCreacion.day)

            sorteador.cantidadSorteos().equals(1)
        
    }

// Dado que el sorteador ingresa un código de cupón único en estado Vigente
// Y que el cupón pertenece a un sorteo del sorteador
// Y que el sorteo en cuestión ya finalizó
// Y que el sorteador visualiza la descripción del cupón y la fecha de vencimiento
// Cuando el sorteador canjea el código de cupón de beneficio en la aplicación
// Entonces la aplicación cambia el estado del cupón de Vigente a Canjeado y le informa al sorteador que el cupón fue canjeado.

    void "Test Sorteador - CA1 - Utilización de cupón de beneficio por participación"() {
        Sorteador sorteador = new Sorteador(logoNegocio:"", nombreRepresentante:"Nicolas", misSorteos:[:])
        
        String descripPremio = "Premio1"
        String imgPremio = "ImgPremio1"
        int durDias = 10 
        int tipo = 0
        int limiteParticipante = 150
        String localidad = "LocalidadTest1"
        String descripSorteo = "Sorteo interesante Test1"


        Tematica tematica1 = new Tematica(
            nombre: "TematicaTest1"
        )
        Set<Tematica> tematicas = [tematica1]

        Sorteo sorteoCreado = sorteador.crearSorteo(descripPremio, imgPremio, durDias, 
            tipo, tematicas, limiteParticipante, localidad, descripSorteo)
        
        CuponBeneficio cuponTest1 = new CuponBeneficio(
            codigoCupon: "4ABX23S",
            descripcionCupon: "Descripcion test del cupon",
            fechaVencimiento: new Date(),
            estado: 1 // Vigente
        )

        CuponBeneficio cuponTest2 = new CuponBeneficio(
            codigoCupon: "4AK3L3O",
            descripcionCupon: "Descripcion test 2 del cupon",
            fechaVencimiento: new Date(),
            estado: 1 // Vigente
        )
        
        Set<CuponBeneficio> cupones = [cuponTest1,cuponTest2]
        sorteoCreado.setCuponesBeneficio(cupones) //Metodo solo para tests

        given:
            sorteoCreado.generarGanador()//Esto finaliza sorteo
        when:
            sorteador.canjearCupon(sorteoCreado,"4ABX23S")
        then:
            Set<CuponBeneficio> cuponesSorteo = sorteoCreado.obtenerCuponesBeneficio()
            cuponesSorteo[0].obtenerEstado().equals(2)
    }
}
