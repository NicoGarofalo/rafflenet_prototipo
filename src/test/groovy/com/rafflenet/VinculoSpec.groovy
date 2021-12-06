package com.rafflenet

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification
import java.time.*

class VinculoSpec extends Specification implements DomainUnitTest<Vinculo> {

    def setup() {
    }

    def cleanup() {
    }

    // Dado que los sorteos están publicados
    // Y que el participante puede visualizar información básica de cada sorteo
    // Y que está la opción de ver más detalles de cada sorteo
    // Y que se le permita al participante participar o no
    // Cuando el participante elige participar
    // Entonces la aplicación lo agrega a la lista de participantes del sorteo
    // Y el participante agrega al sorteo a su lista de sorteos

    void "Test Participante - Participar en un sorteo"() {

        Usuario participante = new Usuario(
            nombre: 'Fulanito',
            constrasenia: '1234hola',
            email: 'fulanito@correoElectronico.com',
            telefono: '115584993',
            rol: 0
        )

        given:
            EstadisticaSorteo nuevaEstadistica = new EstadisticaSorteo(
                limiteParticipante: 100,
                descripSorteo: "Sorteo interesante Test1"
            )

            Sorteo sorteoCreado =  new Sorteo(
                descripcionPremio: "PremioTest1",
                imagenPremio: "ImgPremioTest1",
                duracionDias: 10,
                tipo: 0,
                tematicas: "TematicaTest1",
                ganadorSorteo: "",
                estadistica: nuevaEstadistica
            )
        when:
            Vinculo vinculoParticipante = new Vinculo()
            vinculoParticipante.vincular(participante,sorteoCreado)
            participante.misVinculos << vinculoParticipante
        then:
            sorteoCreado.obtenerCantidadParticipante().equals(1)
            participante.obtenerCantidadVinculos().equals(1)

    }

    // US Creación de sorteo no exprés: Como sorteador quiero crear un sorteo para que los participantes puedan inscribirse al mismo

    // CA: Dado que el sorteador define los datos del sorteo: descripción e imagen del premio, duración del sorteo en días,
    //     temáticas, limite de participantes, descripción del sorteo
    //     Y que el tipo de sorteo seleccionado por el sorteador es no exprés
    //     Y que todos los campos son obligatorios
    //     Cuando el sorteador decide crear el sorteo
    //     Entonces la aplicación verifica que todos los campos estan completos, crea el sorteo con fecha de creación
    //     correspondiente al día actual y lo agrega a la lista de sorteos del sorteador

    void "Test Sorteador - Creación de sorteo no exprés"() {

        Usuario sorteador = new Usuario(
            nombre: 'Fulanito',
            constrasenia: '1234hola',
            email: 'fulanito@correoElectronico.com',
            telefono: '115584993',
            rol: 1
        )
        given: 
            String descripPremio = "PremioTest1"
            String imgPremio = "ImgPremioTest1"
            LocalDate fechaVencimiento = LocalDate.now().plusDays(10)
            int tipo = 0
            int limiteParticipante = 100
            String descripSorteo = "Sorteo interesante Test1"

            Tematica tematica1 = new Tematica(
                nombre: "TematicaTest1"
            )
            Set<Tematica> tematicas = [tematica1]
        
        when:
            Vinculo vinculoSorteador = new Vinculo()

            Sorteo sorteoCreado = vinculoSorteador.crearSorteo(descripPremio, imgPremio, fechaVencimiento, tipo, tematicas,
            limiteParticipante, descripSorteo)
            vinculoSorteador.vincular(sorteador, sorteoCreado)

        then:
            descripPremio.equals(sorteoCreado.descripcionPremio)
            imgPremio.equals(sorteoCreado.imagenPremio)
            fechaVencimiento.equals(sorteoCreado.fechaVencimiento)
            tipo.equals(sorteoCreado.tipo)
            limiteParticipante.equals(sorteoCreado.estadistica.limiteParticipante)
            descripSorteo.equals(sorteoCreado.estadistica.descripcion)
            sorteador.obtenerCantidadVinculos().equals(1)
    }

    // Dado que el sorteador ingresa un código de cupón único en estado Vigente
    // Y que el cupón pertenece a un sorteo del sorteador
    // Y que el sorteo en cuestión ya finalizó
    // Y que el sorteador visualiza la descripción del cupón y la fecha de vencimiento
    // Cuando el sorteador canjea el código de cupón de beneficio en la aplicación
    // Entonces la aplicación cambia el estado del cupón de Vigente a Canjeado y le informa al sorteador que el cupón fue canjeado.

    void "Test Sorteador - CA1 - Utilización de cupón de beneficio por participación"() {

        // Crear sorteador y participante
        Usuario sorteador = new Usuario(
            nombre: 'FulanitoSorteador',
            constrasenia: '1234Sorteador',
            email: 'fulanitoSort@correoElectronico.com',
            telefono: '115584993',
            rol: 1
        )
        Usuario participante = new Usuario(
            nombre: 'FulanitaParticipante',
            constrasenia: '1234Participante',
            email: 'fulanitaP@correoElectronico.com',
            telefono: '115636622',
            rol: 0
        )

        String descripPremio = "PremioTest1"
        String imgPremio = "ImgPremioTest1"
        LocalDate fechaVencimiento = LocalDate.now().plusDays(10)
        int tipo = 0
        int limiteParticipante = 100
        String descripSorteo = "Sorteo interesante Test1"

        Tematica tematica1 = new Tematica(
            nombre: "TematicaTest1"
        )
        Set<Tematica> tematicas = [tematica1]

        //Crear sorteo y agregarlo al sorteador
        Vinculo vinculoSorteador = new Vinculo()
        Sorteo sorteoCreado = vinculoSorteador.crearSorteo(descripPremio, imgPremio, fechaVencimiento, tipo, 
        tematicas, limiteParticipante, descripSorteo)
        vinculoSorteador.vincular(sorteador, sorteoCreado)

        //Crear participacion y agregarla al participante
        Vinculo vinculoParticipante = new Vinculo()
        vinculoParticipante.vincular(participante, sorteoCreado)

        given:
            vinculoSorteador.finalizarSorteo()
        when:
            String codigoCupon = vinculoParticipante.obtenerCodigoCupon()
            String mensajeCupon = vinculoSorteador.canjearCupon(codigoCupon)
        then:
            mensajeCupon.equals("Cupon canjeado exitosamente")
            Set<Cupon> cuponesSorteo = sorteoCreado.obtenerCupones()
            cuponesSorteo[0].obtenerEstado().equals(2)
    }

    // Dado que el sorteador ingresa un código de cupón único en estado Vencido
    // Y que el cupón pertenece a un sorteo del sorteador
    // Y que el sorteador visualiza la descripción del cupón y la fecha de vencimiento
    // Cuando el sorteador ingresa el código de cupón de beneficio en la aplicación para canjearlo
    // Entonces la aplicación le informa al sorteador que el estado del cupón es Vencido y que ya no puede ser canjeado

    void "Test Sorteador - CA2 - Utilización de cupón de beneficio por participación"() {
        
        // Crear sorteador y participante
        Usuario sorteador = new Usuario(
            nombre: 'FulanitoSorteador',
            constrasenia: '1234Sorteador',
            email: 'fulanitoSort@correoElectronico.com',
            telefono: '115584993',
            rol: 1
        )
        Usuario participante = new Usuario(
            nombre: 'FulanitaParticipante',
            constrasenia: '1234Participante',
            email: 'fulanitaP@correoElectronico.com',
            telefono: '115636622',
            rol: 0
        )
        
        String descripPremio = "Premio1"
        String imgPremio = "ImgPremio1"
        LocalDate fechaVencimiento = LocalDate.now().plusDays(10)
        int tipo = 0
        int limiteParticipante = 150
        String descripSorteo = "Sorteo interesante Test1"

        Tematica tematica1 = new Tematica(
            nombre: "TematicaTest1"
        )
        Set<Tematica> tematicas = [tematica1]

        //Crear sorteo y agregarlo al sorteador
        Vinculo vinculoSorteador = new Vinculo()
        Sorteo sorteoCreado = vinculoSorteador.crearSorteo(descripPremio, imgPremio, fechaVencimiento, tipo, 
        tematicas, limiteParticipante, descripSorteo)
        vinculoSorteador.vincular(sorteador, sorteoCreado)

        //Crear participacion y agregarla al participante
        Vinculo vinculoParticipante = new Vinculo()
        vinculoParticipante.vincularConCuponVencido(participante, sorteoCreado)

        given:
            vinculoSorteador.finalizarSorteo()
        when:
            String codigoCupon = vinculoParticipante.obtenerCodigoCupon()
            String mensajeCupon = vinculoSorteador.canjearCupon(codigoCupon)
        then:
            mensajeCupon.equals('Cupon vencido')
            vinculoParticipante.miCupon.obtenerEstado().equals(3)
    }


    // Dado que el sorteador ingresa un código de cupón único en estado Canjeado
    // Y que el cupón pertenece a un sorteo del sorteador
    // Y que el sorteador visualiza la descripción del cupón y la fecha de vencimiento
    // Cuando el sorteador ingresa el código de cupón de beneficio en la aplicación para canjearlo
    // Entonces la aplicación le informa al sorteador que el estado del cupón es Canjeado y que ya no puede ser canjeado

    void "Test Sorteador - CA4 - Utilización de cupón de beneficio por participación"() {
        
        // Crear sorteador y participante
        Usuario sorteador = new Usuario(
            nombre: 'FulanitoSorteador',
            constrasenia: '1234Sorteador',
            email: 'fulanitoSort@correoElectronico.com',
            telefono: '115584993',
            rol: 1
        )
        Usuario participante = new Usuario(
            nombre: 'FulanitaParticipante',
            constrasenia: '1234Participante',
            email: 'fulanitaP@correoElectronico.com',
            telefono: '115636622',
            rol: 0
        )
        
        String descripPremio = "Premio1"
        String imgPremio = "ImgPremio1"
        LocalDate fechaVencimiento = LocalDate.now().plusDays(10)
        int tipo = 0
        int limiteParticipante = 150
        String descripSorteo = "Sorteo interesante Test1"


        Tematica tematica1 = new Tematica(
            nombre: "TematicaTest1"
        )
        Set<Tematica> tematicas = [tematica1]

        //Crear sorteo y agregarlo al sorteador
        Vinculo vinculoSorteador = new Vinculo()
        Sorteo sorteoCreado = vinculoSorteador.crearSorteo(descripPremio, imgPremio, fechaVencimiento, tipo, 
        tematicas, limiteParticipante, descripSorteo)
        vinculoSorteador.vincular(sorteador, sorteoCreado)

        //Crear participacion y agregarla al participante
        Vinculo nuevoVinculo = new Vinculo()
        nuevoVinculo.vincularConCuponCanjeado(participante, sorteoCreado)

        given:
            vinculoSorteador.finalizarSorteo()
        when:
            String codigoCupon = nuevoVinculo.obtenerCodigoCupon()
            String mensajeCupon = vinculoSorteador.canjearCupon(codigoCupon)
        then:
            mensajeCupon.equals("Cupon ya canjeado")
            Set<Cupon> cuponesSorteo = sorteoCreado.obtenerCupones()
            cuponesSorteo[0].obtenerEstado().equals(2)
    }


    // Dado que el sorteador ingresa un código de cupón único que no pertenece a ningún sorteo del sorteador
    // Cuando el sorteador ingresa el código de cupón de beneficio en la aplicación para canjearlo
    // Entonces la aplicación le notificará al sorteador que este cupón no pertenece a ninguno de sus sorteos


    void "Test Sorteador - CA5 - Utilización de cupón de beneficio por participación"() {
        
        // Crear sorteador y participante
        Usuario sorteador = new Usuario(
            nombre: 'FulanitoSorteador',
            constrasenia: '1234Sorteador',
            email: 'fulanitoSort@correoElectronico.com',
            telefono: '115584993',
            rol: 1
        )
        Usuario participante = new Usuario(
            nombre: 'FulanitaParticipante',
            constrasenia: '1234Participante',
            email: 'fulanitaP@correoElectronico.com',
            telefono: '115636622',
            rol: 0
        )
        
        String descripPremio = "Premio1"
        String imgPremio = "ImgPremio1"
        LocalDate fechaVencimiento = LocalDate.now().plusDays(10)
        int tipo = 0
        int limiteParticipante = 150
        String descripSorteo = "Sorteo interesante Test1"


        Tematica tematica1 = new Tematica(
            nombre: "TematicaTest1"
        )
        Set<Tematica> tematicas = [tematica1]

        //Crear sorteo y agregarlo al sorteador
        Vinculo vinculoSorteador = new Vinculo()
        Sorteo sorteoCreado = vinculoSorteador.crearSorteo(descripPremio, imgPremio, fechaVencimiento, tipo, 
        tematicas, limiteParticipante, descripSorteo)
        vinculoSorteador.vincular(sorteador, sorteoCreado)

        //Crear participacion y agregarla al participante
        Vinculo nuevoVinculo = new Vinculo()
        nuevoVinculo.vincular(participante, sorteoCreado)

        given:
            vinculoSorteador.finalizarSorteo()
        when:
            String mensajeCupon = vinculoSorteador.canjearCupon("4AK3V12")
        then:
            mensajeCupon.equals('Cupon no encontrado')
    }


    // Como sorteador
    // Quiero ser notificado del ganador que se sorteo automáticamente en el día y hora programado
    // Para saber quién es el ganador y poder contactarme con el mismo

    // Dado que el sorteador programó un sorteo automático cuando creó el sorteo
    // Cuando el sorteo alcanza la fecha de finalización
    // Entonces la aplicación sortea automáticamente y le notifica al sorteador cuál es
    // el ganador obtenido, y finaliza el sorteo


    void "Test Sorteador - Sorteo automático de ganador" () {
        
        // Crear sorteador y participante
        Usuario sorteador = new Usuario(
            nombre: 'FulanitoSorteador',
            constrasenia: '1234Sorteador',
            email: 'fulanitoSort@correoElectronico.com',
            telefono: '115584993',
            rol: 1
        )
        Usuario participante = new Usuario(
            nombre: 'FulanitaParticipante',
            constrasenia: '1234Participante',
            email: 'fulanitaP@correoElectronico.com',
            telefono: '115636622',
            rol: 0
        )

        Tematica tematica1 = new Tematica(
            nombre: "TematicaTest1"
        )
        Set<Tematica> tematicas = [tematica1]


        String descripPremio = "Premio1"
        String imgPremio = "ImgPremio1"
        LocalDate fechaVencimiento = LocalDate.now().plusDays(10)
        int tipo = 0
        int limiteParticipante = 150
        String descripSorteo = "Sorteo interesante Test1"

        given:
            //Crear sorteo y agregarlo al sorteador
            Vinculo vinculoSorteador = new Vinculo()
            Sorteo sorteoCreado = vinculoSorteador.crearSorteo(descripPremio, imgPremio, fechaVencimiento, tipo, 
            tematicas, limiteParticipante, descripSorteo)
            vinculoSorteador.vincular(sorteador, sorteoCreado)

            //Crear participacion y agregarla al participante
            Vinculo nuevoVinculo = new Vinculo()
            nuevoVinculo.vincular(participante, sorteoCreado)

        when:
            LocalDate fechaSimuladaVencimiento = LocalDate.now().plusDays(10)
            def vencioSorteo = vinculoSorteador.sorteo.validarFecha(fechaSimuladaVencimiento)
            Usuario ganador = vinculoSorteador.finalizarSorteo()
        then:
            vencioSorteo == true //El sorteo venció (en base a fechas)
            ganador != null //Obtengo ganador valido
            vinculoSorteador.sorteo.estado.equals(1)//Finalizado
    }

}
