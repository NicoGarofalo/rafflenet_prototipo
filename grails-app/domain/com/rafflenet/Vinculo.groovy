package com.rafflenet

class Vinculo {

    Usuario usuario
    Sorteo sorteo
    Cupon miCupon
    Set<Tematica> tematicasEnComun
    Set<Viculo> misVinculos = []

    static constraints = {

    }
    
    def vincular(Usuario usuario, Sorteo sorteo) {

        this.usuario = usuario
        this.sorteo = sorteo
        this.miCupon = sorteo.crearCupon()

        //this.tematicasEncomun = matchearTematicas(usuario.tematicas, sorteo.teamticas)
        if(this.usuario == 0)
            participar()
    }

    def matchearTematicas(Tematicas tematicasUsuario, Tematicas tematicasSorteo) {
        //hacer
    }
    
    def participar() {
        this.sorteo.agregarParticipante(usuario)
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

    def finalizarSorteo(Sorteo sorteo) {}

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
}
