package com.rafflenet

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class EstadisticaSorteoSpec extends Specification implements DomainUnitTest<EstadisticaSorteo> {

    def setup() {
    }

    def cleanup() {
    }

    // Como sorteador
    // Quiero visualizar el análisis de un sorteo finalizado
    // Para analizar las estadísticas generales obtenidas del mismo

    // Dado que un sorteo finalizó
    // Y que se cuenta con el número de participantes y el número de visualizaciones del sorteo
    // Cuando se pide generar un análisis de cantidad de participantes contra cantidad de 
    // visualizaciones totales del sorteo
    // Entonces la aplicación realizará la división entre cantidad 
    // de participantes y cantidad de visualizaciones totales del sorteo, y lo convertirá en porcentaje
    

    void "Test Sorteador - CA2 - Visualización de análisis sorteo finalizado" () {

        Tematica tematica1 = new Tematica(
            nombre: "TematicaTest1"
        )
        Set<Tematica> tematicas = [tematica1]
        
        given:
            //Crear sorteo y agregarlo al sorteador
            Vinculo vinculoSorteador = new Vinculo()
            Sorteo sorteoCreado = vinculoSorteador.crearSorteo(descripPremio, imgPremio, fechaVencimiento, tipo, 
            tematicas, limiteParticipante, descripSorteo)
            vinculoSorteador.vincular(sorteador, sorteoCreado)

            //Crear participacion y agregarla al participante
            Participacion nuevaParticipacion = new Participacion()
            nuevaParticipacion.vincular(participante, sorteoCreado)
            Participacion nuevaParticipacion = new Participacion()
            nuevaParticipacion.vincular(participante, sorteoCreado)
            Participacion nuevaParticipacion = new Participacion()
            nuevaParticipacion.vincular(participante, sorteoCreado)
            Participacion nuevaParticipacion = new Participacion()
            nuevaParticipacion.vincular(participante, sorteoCreado)
            Participacion nuevaParticipacion = new Participacion()
            nuevaParticipacion.vincular(participante, sorteoCreado)

            sorteoCreado.detalle.cantVisualizaciones = 15 //hardcodeo cant visualizaciones
        when:
            int resultados = sorteoCreado.detalle.generarEstadisticaParticipanteVsVisualizaciones()
        then:
            int resultadoEsperado = 5 / 15 * 100
            resultados.equals(resultadoEsperado)
    }

    // Dado que un sorteo finalizó
    // Y que se cuenta con los participantes de un sorteo y qué temáticas está interesado cada uno
    // Cuando se pide generar un análisis del porcentaje de participantes por temática 
    // Entonces la aplicación calculará el porcentaje de participantes por cada temática relacionado con el sorteo en cuestión
    // Y el sorteador podrá visualizar estos porcentajes, 
    // Y la suma de los mismos deberá ser 100%

 void "Test Sorteador - CA3 - Visualización de sorteo finalizado" () {
        Sorteador sorteador = new Sorteador(logoNegocio:"", nombreRepresentante:"Nicolas", misSorteos:[:])

        Tematica tematica1 = new Tematica(
            nombre: "TematicaTest1"
        )
        Tematica tematica2 = new Tematica(
            nombre: "TematicaTest2"
        )
        Tematica tematica3 = new Tematica(
            nombre: "TematicaTest3"
        )
        Set<Tematica> tematicasSorteo = [tematica1,tematica2,tematica3]

        Participante p1 = new Participante()
        Participante p2 = new Participante()
        Participante p3 = new Participante()

        p1.elegirTematica(tematica1)
        p2.elegirTematica(tematica2)
        p3.elegirTematica(tematica1)

        given:
            Sorteo sorteoCreado = sorteador.crearSorteo(
                "DescripcionPremio1", 
                "ImgPremio1",
                LocalDate.now(), 
                0, 
                tematicasSorteo, 
                150,
                "Sorteo interesante Test1"
            )
            sorteoCreado.agregarParticipante(p1)
            sorteoCreado.agregarParticipante(p2)
            sorteoCreado.agregarParticipante(p3)
            sorteoCreado.generarGanador() // significa que finalizo el sorteo

        when:
            Map resultados = sorteoCreado.generarEstadisticaPonderacionPorTematica()
        then:
            resultados['TematicaTest1'] == 67
            resultados['TematicaTest2'] == 33
            resultados['TematicaTest3'] == 0
    }

    // Dado que un sorteo finalizó
    // Y que se tiene la lista de cupones de beneficio con sus estados
    // Cuando se pide generar un análisis de cupones de beneficio vigentes contra canjeados
    // Entonces la aplicación generará la división entre la cantidad de cupones de beneficios vigentes a canjear y los canjeados 
    // , y se convertirá en porcentaje (multiplicando por 100)
    // Y el sorteador podrá visualizar este número generado


    void "Test Sorteador - CA1 - Visualización de sorteo finalizado" () {
        Sorteador sorteador = new Sorteador(logoNegocio:"", nombreRepresentante:"Nicolas", misSorteos:[:])

        Tematica tematica1 = new Tematica(
            nombre: "TematicaTest1"
        )
        Set<Tematica> tematicasSorteo = [tematica1]

        Sorteo sorteoCreado = sorteador.crearSorteo(
            "DescripcionPremio1", 
            "ImgPremio1",
            LocalDate.now().plusDays(10), 
            0, 
            tematicasSorteo, 
            150,
            "Sorteo interesante Test1"
        )

        CuponBeneficio cuponTest1 = new CuponBeneficio(
            codigoCupon: "4ABX23S",
            descripcionCupon: "Descripcion test del cupon",
            fechaVencimiento: new Date().toInstant().plus(1),
            estado: 1 // Vigente
        )
        CuponBeneficio cuponTest2 = new CuponBeneficio(
            codigoCupon: "4AK3L3O",
            descripcionCupon: "Descripcion test 2 del cupon",
            fechaVencimiento: new Date().toInstant().plus(1),
            estado: 1 // Vigente
        )
        CuponBeneficio cuponTest3 = new CuponBeneficio(
            codigoCupon: "4AT9D14",
            descripcionCupon: "Descripcion test 3 del cupon",
            fechaVencimiento: new Date().toInstant().plus(1),
            estado: 1 // Vigente
        )
        CuponBeneficio cuponTest4 = new CuponBeneficio(
            codigoCupon: "4AP1U55",
            descripcionCupon: "Descripcion test 3 del cupon",
            fechaVencimiento: new Date().toInstant().plus(1),
            estado: 1 // Vigente
        )
        
        Set<CuponBeneficio> cupones = [cuponTest1,cuponTest2,cuponTest3,cuponTest4]
        sorteoCreado.setCuponesBeneficio(cupones) //Metodo solo para tests

        given:
            sorteoCreado.generarGanador()//Esto finaliza sorteo
        when:
            sorteador.canjearCupon(sorteoCreado,"4ABX23S")
            sorteador.canjearCupon(sorteoCreado,"4AT9D14")
            sorteador.canjearCupon(sorteoCreado,"4AP1U55")
            Map resultados = sorteoCreado.generarEstadisticaCuponVigenteVsCanjeado()
        then:
            resultados['Vigentes'] == 25
            resultados['Canjeados'] == 75
    }

}
