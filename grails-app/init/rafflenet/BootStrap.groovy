package com.rafflenet
import java.time.*

class BootStrap {

    def init = { servletContext ->

        Usuario usuario = new Usuario(
            nombre: 'Fulanito',
            nombreRepresentante: 'Fulano',
            logoNegocio: 'notnull',
            constrasenia: 'test1234',
            email: 'fulano@mailfiuba.com',
            telefono: '1234567890',
            rol: 0
        ).save(failOnError: true)

        EstadisticaSorteo nuevaEstadistica = new EstadisticaSorteo(
            limiteParticipante: 20,
            descripcion: "Descripcion del sorteo test"
        ).save(failOnError: true)

        Sorteo sorteo = new Sorteo(
            descripcionPremio: "Apple Watch series 5",
            imagenPremio: "",
            tipo: 0,
            participantes: [],
            ganadorSorteo: "",
            fechaVencimiento: LocalDate.now().plusDays(10),
            estadistica: nuevaEstadistica
        ).save(failOnError: true)



        EstadisticaSorteo nuevaEstadistica1 = new EstadisticaSorteo(
            limiteParticipante: 20,
            descripcion: "Esta es la descripcion del mejor sorteo del mundo"
        ).save(failOnError: true)

        Sorteo sorteo2 = new Sorteo(
            descripcionPremio: "Microsoft Surface Pro",
            imagenPremio: "",
            tipo: 0,
            participantes: [],
            ganadorSorteo: "",
            fechaVencimiento: LocalDate.now().plusDays(10),
            estadistica: nuevaEstadistica1
        ).save(failOnError: true)

        println "Inicializando..."
    }

    def destroy = {
    }
}
