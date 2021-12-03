package com.rafflenet

class AdministradorDeSorteo {

    Usuario usuario
    Sorteo sorteo

    static constraints = {
    }

    def crearSorteo( String descripPremio, String imgPremio, LocalDate fechaVencimiento, 
    int tipo, Set<Tematica> tematicas,int limiteParticipante, String descripSorteo) {

        DetalleSorteo nuevoDetalle = new DetalleSorteo(
            limiteParticipante: limiteParticipante,
            descripSorteo: descripSorteo
        )

        this.sorteo = new Sorteo(
            descripcionPremio: descripPremio,
            imagenPremio: imgPremio,
            tipo: tipo,
            tematicas: tematicas,
            cuponesBeneficio: "",
            participantes: [],
            ganadorSorteo: "",
            fechaVencimiento: fechaVencimiento,
            detalle: nuevoDetalle
        )

        return this.sorteo
    }

    def finalizarSorteoManual(Sorteo srteo) {}

    def obtenerGanador(Sorteo srteo) {}
    
    def canjearCupon(String codigoCupon) {
        CuponBeneficio cupon = this.sorteo.obtenerCupon(codigoCupon)
        if(!cupon)  return 'Cupon no encontrado'
        
        //aca iria un verificarVencimiento()
        if (cupon.verificarVencimiento()) return 'Cupon vencido' 
        if (cupon.canjear()) {
            return 'Cupon canjeado exitosamente'
        } else {
            return 'Cupon ya canjeado'
        }

        return cupon.canjear()
    }

    def obtenerSorteoCupon(int idSorteo) {}

}
