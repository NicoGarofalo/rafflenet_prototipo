package com.rafflenet

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class AdministradorDeSorteoSpec extends Specification implements DomainUnitTest<AdministradorDeSorteo> {

    def setup() {
    }

    def cleanup() {
    }

// Incluimos US y CA Trivial con fines de práctica para Test Spock

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
            AdministradorDeSorteo nuevoAdministrador = new AdministradorDeSorteo(sorteador)

            Sorteo sorteoCreado = nuevoAdministrador.crearSorteo(descripPremio, imgPremio, fechaVencimiento, tipo, tematicas,
            limiteParticipante, descripSorteo)
            sorteador.administradores << nuevoAdministrador

        then:
            descripPremio.equals(sorteoCreado.descripcionPremio)
            imgPremio.equals(sorteoCreado.imagenPremio)
            fechaVencimiento.equals(sorteoCreado.fechaVencimiento)
            tipo.equals(sorteoCreado.tipo)
            limiteParticipante.equals(sorteoCreado.detalle.limiteParticipante)
            descripSorteo.equals(sorteoCreado.detalle.descripSorteo)
            sorteador.cantidadAdministrdorSorteo().equals(1)
        
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


        AdministradorDeSorteo nuevoAdministrador = new AdministradorDeSorteo(sorteador)

        //Crear sorteo y agregarlo al sorteador
        Sorteo sorteoCreado = nuevoAdministrador.crearSorteo(descripPremio, imgPremio, fechaVencimiento, tipo, 
        tematicas, limiteParticipante, descripSorteo)
        sorteador.administradores << nuevoAdministrador

        //Crear participacion y agregarla al participante
        Participacion nuevaParticipacion = new Participacion()
        nuevaParticipacion.participar(participante, sorteoCreado)
        participante.participaciones << nuevaParticipacion

        CuponBeneficio cuponTest1 = new CuponBeneficio(
            codigoCupon: "4ABX23S",
            descripcionCupon: "Descripcion test del cupon",
            fechaVencimiento: new Date().toInstant().plus(1),
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
            nuevoAdministrador.finalizarSorteo()
        when:
            String mensajeCupon = nuevoAdministrador.canjearCupon("4ABX23S")
        then:
            mensajeCupon.equals("Cupon canjeado exitosamente")
            Set<CuponBeneficio> cuponesSorteo = sorteoCreado.obtenerCuponesBeneficio()
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

        
        
        CuponBeneficio cuponTest1 = new CuponBeneficio(
            codigoCupon: "4ABX23S",
            descripcionCupon: "Descripcion test del cupon",
            fechaVencimiento: new Date().toInstant().minus(1),
            estado: 3 // Vigente
        )
        
        CuponBeneficio cuponTest2 = new CuponBeneficio(
            codigoCupon: "4AK3L3O",
            descripcionCupon: "Descripcion test 2 del cupon",
            fechaVencimiento: new Date(),
            estado: 3 // Vigente
        )
        
        Set<CuponBeneficio> cupones = [cuponTest1,cuponTest2]

        AdministradorDeSorteo nuevoAdministrador = new AdministradorDeSorteo(sorteador)

        //Crear sorteo y agregarlo al sorteador
        Sorteo sorteoCreado = nuevoAdministrador.crearSorteo(descripPremio, imgPremio, fechaVencimiento, tipo, 
        tematicas, limiteParticipante, descripSorteo)
        sorteador.administradores << nuevoAdministrador

        //Crear participacion y agregarla al participante
        Participacion nuevaParticipacion = new Participacion()
        nuevaParticipacion.participar(participante, sorteoCreado)
        participante.participaciones << nuevaParticipacion

        sorteoCreado.setCuponesBeneficio(cupones) //Metodo solo para tests

        given:
            sorteoCreado.generarGanador()//Esto finaliza sorteo
        when:
            String cuponDisponible = nuevoAdministrador.canjearCupon(sorteoCreado,"4ABX23S")
        then:
            cuponDisponible.equals('Cupon vencido')
            cupones[0].obtenerEstado().equals(3)
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

        Sorteo sorteoCreado = sorteador.crearSorteo(descripPremio, imgPremio, fechaVencimiento, 
            tipo, tematicas, limiteParticipante, descripSorteo)
        
        CuponBeneficio cuponTest1 = new CuponBeneficio(
            codigoCupon: "4ABX23S",
            descripcionCupon: "Descripcion test del cupon",
            fechaVencimiento: new Date(),
            estado: 2 // Canjeado
        )

        CuponBeneficio cuponTest2 = new CuponBeneficio(
            codigoCupon: "4AK3L3O",
            descripcionCupon: "Descripcion test 2 del cupon",
            fechaVencimiento: new Date(),
            estado: 1 // Vigente
        )
        
        Set<CuponBeneficio> cupones = [cuponTest1,cuponTest2]

        AdministradorDeSorteo nuevoAdministrador = new AdministradorDeSorteo(sorteador)

        //Crear sorteo y agregarlo al sorteador
        Sorteo sorteoCreado = nuevoAdministrador.crearSorteo(descripPremio, imgPremio, fechaVencimiento, tipo, 
        tematicas, limiteParticipante, descripSorteo)
        sorteador.administradores << nuevoAdministrador

        //Crear participacion y agregarla al participante
        Participacion nuevaParticipacion = new Participacion()
        nuevaParticipacion.participar(participante, sorteoCreado)
        participante.participaciones << nuevaParticipacion

        sorteoCreado.setCuponesBeneficio(cupones) //Metodo solo para tests

        given:
            sorteoCreado.generarGanador()//Esto finaliza sorteo
        when:
            String cuponDisponible = nuevoAdministrador.canjearCupon(sorteoCreado,"4ABX23S")
        then:
            cuponDisponible.equals("Cupon ya canjeado")
            Set<CuponBeneficio> cuponesSorteo = sorteoCreado.obtenerCuponesBeneficio()
            cuponesSorteo[0].obtenerEstado().equals(2)
    }


    // Dado que el sorteador ingresa un código de cupón único que no pertenece a ningún sorteo del sorteador
    // Cuando el sorteador ingresa el código de cupón de beneficio en la aplicación para canjearlo
    // Entonces la aplicación le notificará al sorteador que este cupón no pertenece a ninguno de sus sorteos


    void "Test Sorteador - CA5 - Utilización de cupón de beneficio por participación"() {
        Sorteador sorteador = new Sorteador(logoNegocio:"", nombreRepresentante:"Nicolas", misSorteos:[:])
        
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

        Sorteo sorteoCreado = sorteador.crearSorteo(descripPremio, imgPremio, fechaVencimiento,
            tipo, tematicas, limiteParticipante, descripSorteo)
        
        CuponBeneficio cuponTest1 = new CuponBeneficio(
            codigoCupon: "4ABX23S",
            descripcionCupon: "Descripcion test del cupon",
            fechaVencimiento: new Date().toInstant().plus(1),
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
            String cuponDisponible = sorteador.canjearCupon(sorteoCreado,"8YND10Q")
        then:
            cuponDisponible.equals('Cupon no encontrado')
    }
    // Como sorteador
    // Quiero ser notificado del ganador que se sorteo automáticamente en el día y hora programado
    // Para saber quién es el ganador y poder contactarme con el mismo

    // Dado que el sorteador programó un sorteo automático cuando creó el sorteo
    // Cuando el sorteo alcanza la fecha de finalización
    // Entonces la aplicación sortea automáticamente y le notifica al sorteador cuál es
    // el ganador obtenido, y finaliza el sorteo


    void "Test Sorteador - Sorteo automático de ganador" () {
        Sorteador sorteador = new Sorteador(logoNegocio:"", nombreRepresentante:"Nicolas", misSorteos:[:])

        Tematica tematica1 = new Tematica(
            nombre: "TematicaTest1"
        )
        Set<Tematica> tematicas = [tematica1]

        Participante nuevoParticipante = new Participante()

        given:
            Sorteo sorteoCreado = sorteador.crearSorteo(
                "DescripcionPremio1", 
                "ImgPremio1",
                LocalDate.now().plusDays(10), 
                0, 
                tematicas, 
                150,
                "Sorteo interesante Test1"
            )

            sorteoCreado.agregarParticipante(nuevoParticipante)
        when:
            LocalDate fechaSimuladaVencimiento = LocalDate.now().plusDays(10)
            def vencioSorteo = sorteador.misSorteos[0].validarFecha(fechaSimuladaVencimiento)
            Participante ganador = sorteador.misSorteos[0].generarGanador()
        then:
            vencioSorteo == true //El sorteo venció (en base a fechas)
            ganador != null //Obtengo ganador valido
            sorteador.misSorteos[0].estado.equals(1)//Finalizado
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



    void "Test Sorteador - CA2 - Visualización de sorteo finalizado" () {
        Sorteador sorteador = new Sorteador(logoNegocio:"", nombreRepresentante:"Nicolas", misSorteos:[:])
        Participante p1 = new Participante()
        Participante p2 = new Participante()
        Participante p3 = new Participante()
        Participante p4 = new Participante()
        Participante p5 = new Participante()

        Tematica tematica1 = new Tematica(
            nombre: "TematicaTest1"
        )
        Set<Tematica> tematicas = [tematica1]
        
        given:
            Sorteo sorteoCreado = sorteador.crearSorteo(
                "DescripcionPremio1", 
                "ImgPremio1",
                LocalDate.now(), 
                0, 
                tematicas, 
                150,
                "Sorteo interesante Test1"
            )
            sorteoCreado.agregarParticipante(p1)
            sorteoCreado.agregarParticipante(p2)
            sorteoCreado.agregarParticipante(p3)
            sorteoCreado.agregarParticipante(p4)
            sorteoCreado.agregarParticipante(p5)
            sorteoCreado.generarGanador()

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
