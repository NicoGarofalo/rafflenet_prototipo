package com.rafflenet

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class ParticipanteSpec extends Specification implements DomainUnitTest<Participante> {

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

        Participante nuevoParticipante = new Participante(localidad:"localidad1", coidigoPostal:1234)

        DetalleSorteo nuevoDetalle1 = new DetalleSorteo(
            limiteParticipante: 100,
            localidad: "LocalidadTest1",
            descripSorteo: "Sorteo interesante Test1"
        )

        DetalleSorteo nuevoDetalle2 = new DetalleSorteo(
            limiteParticipante: 150,
            localidad: "LocalidadTest1",
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
            nuevoParticipante.participar(listaSorteos[1])
            sorteoCreado2.agregarParticipante(nuevoParticipante)
        then:
            sorteoCreado2.obtenerCantidadParticipante().equals(1)
            nuevoParticipante.obtenerCantidadSorteos().equals(1)

    }
}
