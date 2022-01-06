package com.rafflenet

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification
import java.time.*

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

        Usuario sorteador = new Usuario(
            nombre: 'FulanitoSort',
            constrasenia: '1234hola',
            email: 'fulanito@correoElectronico.com',
            telefono: '115584993',
            rol: 1
        )
        Usuario p1 = new Usuario(
            nombre: 'Fulanito1',
            constrasenia: 'contra',
            email: 'fulanito1@correo.com',
            telefono: '115585993',
            rol: 0
        )
        Usuario p2 = new Usuario(
            nombre: 'Fulanito2',
            constrasenia: '1234',
            email: 'fulanito2@mail.com',
            telefono: '115584993',
            rol: 0
        )
        Usuario p3 = new Usuario(
            nombre: 'Fulanito3',
            constrasenia: 'hola',
            email: 'fulanito3@mailfiuba.com',
            telefono: '1234567890',
            rol: 0
        )
        Usuario p4 = new Usuario(
            nombre: 'Fulanito4',
            constrasenia: '1234567',
            email: 'fulanito4@fiubamail.com',
            telefono: '115558253',
            rol: 0
        )
        Usuario p5 = new Usuario(
            nombre: 'Fulanito5',
            constrasenia: '1234hola',
            email: 'fulanito5@mailuca.com',
            telefono: '115542578',
            rol: 0
        )

        String descripPremio = "PremioTest1"
        String imgPremio = "ImgPremioTest1"
        LocalDate fechaVencimiento = LocalDate.now()
        int tipo = 0
        int limiteParticipante = 100
        String descripSorteo = "Sorteo interesante Test1"

        Tematica tematica1 = new Tematica(
            nombre: "TematicaTest1"
        )
        Set<Tematica> tematicas = [tematica1]

        given:
            //Crear sorteo y agregarlo al sorteador
            Sorteo sorteoCreado = sorteador.crearSorteo(descripPremio, imgPremio, fechaVencimiento, tipo, 
            tematicas, limiteParticipante, descripSorteo)

            Vinculo vinculoSorteador = new Vinculo(sorteador, sorteoCreado)
            vinculoSorteador.vincular()

            //Crear participacion y agregarla al participante
            Vinculo vinculo1 = new Vinculo(p1, sorteoCreado)
            Vinculo vinculo2 = new Vinculo(p2, sorteoCreado)
            Vinculo vinculo3 = new Vinculo(p3, sorteoCreado)
            Vinculo vinculo4 = new Vinculo(p4, sorteoCreado)
            Vinculo vinculo5 = new Vinculo(p5, sorteoCreado)

            vinculo1.vincular()
            vinculo2.vincular()
            vinculo3.vincular()
            vinculo4.vincular()
            vinculo5.vincular()

            vinculoSorteador.sorteo.estadistica.cantVisualizaciones = 15 //hardcodeo cant visualizaciones
            vinculoSorteador.finalizarSorteo()//Esto finaliza sorteo
        when:
            int resultados = vinculoSorteador.sorteo.estadistica.generarEstadisticaParticipanteVsVisualizaciones()
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
     
        Usuario sorteador = new Usuario(
            nombre: 'FulanitoSort',
            constrasenia: '1234hola',
            email: 'fulanito@correoElectronico.com',
            telefono: '115584993',
            rol: 1
        )

        Usuario p1 = new Usuario(
            nombre: 'Fulanito1',
            constrasenia: 'contra',
            email: 'fulanito1@correo.com',
            telefono: '115585993',
            rol: 0
        )
        Usuario p2 = new Usuario(
            nombre: 'Fulanito2',
            constrasenia: '1234',
            email: 'fulanito2@mail.com',
            telefono: '115584993',
            rol: 0
        )
        Usuario p3 = new Usuario(
            nombre: 'Fulanito3',
            constrasenia: 'hola',
            email: 'fulanito3@mailfiuba.com',
            telefono: '1234567890',
            rol: 0
        )

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

            Vinculo vinculoSorteador = new Vinculo(sorteador, sorteoCreado)
            vinculoSorteador.vincular()
            
            //Crear vinculos y agregarla al participante
            Vinculo vinculo1 = new Vinculo(p1, sorteoCreado)
            Vinculo vinculo2 = new Vinculo(p2, sorteoCreado)
            Vinculo vinculo3 = new Vinculo(p3, sorteoCreado)
            
            vinculo1.vincular()
            vinculo2.vincular()
            vinculo3.vincular()

            vinculoSorteador.finalizarSorteo()//Esto finaliza sorteo

        when:
            Map resultados = vinculoSorteador.sorteo.generarEstadisticaPonderacionPorTematica()
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
        
        Usuario sorteador = new Usuario(
            nombre: 'FulanitoSort',
            constrasenia: '1234hola',
            email: 'fulanito@correoElectronico.com',
            telefono: '115584993',
            rol: 1
        )
        Usuario p1 = new Usuario(
            nombre: 'Fulanito1',
            constrasenia: 'contra',
            email: 'fulanito1@correo.com',
            telefono: '115585993',
            rol: 0
        )
        Usuario p2 = new Usuario(
            nombre: 'Fulanito2',
            constrasenia: '1234',
            email: 'fulanito2@mail.com',
            telefono: '115584993',
            rol: 0
        )
        Usuario p3 = new Usuario(
            nombre: 'Fulanito3',
            constrasenia: 'hola',
            email: 'fulanito3@mailfiuba.com',
            telefono: '1234567890',
            rol: 0
        )
        Usuario p4 = new Usuario(
            nombre: 'Fulanito4',
            constrasenia: '1234567',
            email: 'fulanito4@fiubamail.com',
            telefono: '115558253',
            rol: 0
        )
        Usuario p5 = new Usuario(
            nombre: 'Fulanito5',
            constrasenia: '1234hola',
            email: 'fulanito5@mailuca.com',
            telefono: '115542578',
            rol: 0
        )

        Tematica tematica1 = new Tematica(
            nombre: "TematicaTest1"
        )
        Set<Tematica> tematicasSorteo = [tematica1]

        Sorteo sorteoCreado = sorteador.crearSorteo(
            "DescripcionPremio1", 
            "ImgPremio1",
            LocalDate.now(), 
            0, 
            tematicasSorteo, 
            150,
            "Sorteo interesante Test1"
        )

        Vinculo vinculoSorteador = new Vinculo(sorteador, sorteoCreado)
        vinculoSorteador.vincular()


        //Crear vinculos y agregarla al participante
        Vinculo vinculo1 = new Vinculo(p1, sorteoCreado)
        Vinculo vinculo2 = new Vinculo(p2, sorteoCreado)
        Vinculo vinculo3 = new Vinculo(p3, sorteoCreado)
        Vinculo vinculo4 = new Vinculo(p4, sorteoCreado)
        Vinculo vinculo5 = new Vinculo(p5, sorteoCreado)

        vinculo1.vincular()
        vinculo2.vincular()
        vinculo3.vincular()
        vinculo4.vincular()
        vinculo5.vincular()

        given:
            vinculoSorteador.finalizarSorteo()//Esto finaliza sorteo
        when:
            //Obtener codigo de cupon y canjearlo (porque no ta hardcodeao)
            String codigo1 = vinculo1.obtenerCodigoCupon()
            String codigo2 = vinculo2.obtenerCodigoCupon()
            String codigo3 = vinculo3.obtenerCodigoCupon()
            String codigo4 = vinculo4.obtenerCodigoCupon()

            for (c in vinculoSorteador.sorteo.cupones){
                println c.dump()
            }

            EstadoCupon estado1 = vinculo1.canjearCupon(codigo1)
            EstadoCupon estado2 = vinculo2.canjearCupon(codigo2)
            EstadoCupon estado3 = vinculo3.canjearCupon(codigo3)
            EstadoCupon estado4 = vinculo4.canjearCupon(codigo4)

            for (c in vinculoSorteador.sorteo.cupones){
                println c.dump()
            }

            Map resultados = vinculoSorteador.sorteo.generarEstadisticaCuponVigenteVsCanjeado()
        then:
            estado1 == EstadoCupon.CANJEADO
            estado2 == EstadoCupon.CANJEADO
            estado3 == EstadoCupon.CANJEADO
            estado4 == EstadoCupon.CANJEADO
            
            resultados['Vigentes'] == 20
            resultados['Canjeados'] == 80
    }

}
