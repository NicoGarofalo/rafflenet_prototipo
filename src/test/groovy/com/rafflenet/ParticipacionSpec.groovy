package com.rafflenet

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class ParticipacionSpec extends Specification implements DomainUnitTest<Participacion> {

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
        Participacion nuevaParticipacion = new Participacion()

        DetalleSorteo nuevoDetalle1 = new DetalleSorteo(
            limiteParticipante: 100,
            descripSorteo: "Sorteo interesante Test1"
        )

        DetalleSorteo nuevoDetalle2 = new DetalleSorteo(
            limiteParticipante: 150,
            descripSorteo: "Sorteo interesante Test1"
        )

        Sorteo sorteoCreado1 =  new Sorteo(
            descripcionPremio: "PremioTest1",
            imagenPremio: "ImgPremioTest1",
            duracionDias: 10,
            tipo: 0,
            tematicas: "TematicaTest1",
            participantes: [],
            ganadorSorteo: "",
            detalle: nuevoDetalle1
        )

        Sorteo sorteoCreado2 =  new Sorteo(
            descripcionPremio: "PremioTest1",
            imagenPremio: "ImgPremioTest1",
            duracionDias: 8,
            tipo: 0,
            tematicas: "TematicaTest1",
            participantes: [],
            ganadorSorteo: "",
            detalle: nuevoDetalle2
        )

        given:
            Set<Sorteo> listaSorteos = [sorteoCreado1, sorteoCreado2]
        when:
            nuevaParticipacion.participar(usuario, listaSorteos[1])
            usuario.participaciones << nuevaParticipacion
        then:
            sorteoCreado2.obtenerCantidadParticipante().equals(1)
            usuario.obtenerCantidadParticipaciones().equals(1)

    }
}
