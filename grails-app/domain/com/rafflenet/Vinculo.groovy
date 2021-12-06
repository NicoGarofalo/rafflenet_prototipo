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

        if(this.usuario.rol == 0)
            participar()
        
        this.usuario.misVinculos << this
    }

    // Para test
    def vincularConCuponCanjeado(Usuario usuario, Sorteo sorteo) {
        this.usuario = usuario
        this.sorteo = sorteo

        if(this.usuario.rol == 0)
            participar()

        this.usuario.misVinculos << this
    }

    // Para test
    def vincularConCuponVencido(Usuario usuario, Sorteo sorteo) {
        this.usuario = usuario
        this.sorteo = sorteo

        if(this.usuario.rol == 0)
            participar()
        
        this.usuario.misVinculos << this
    }

    def matchearTematicas() {
        return this.usuario.tematicasUsuario.intersect(this.sorteo.tematicasSorteo)
    }
    
    def participar() {
        this.sorteo.agregarParticipante(usuario)
        this.tematicasEncomun = matchearTematicas()
        this.miCupon = this.sorteo.crearCupon()
    }
    // Para test
    def participarConCuponCanjeado() {
        this.sorteo.agregarParticipante(usuario)
        this.tematicasEncomun = matchearTematicas()
        this.miCupon = this.sorteo.crearCuponCanjeado()
    }
    // Para test
    def participarConCuponVencido() {
        this.sorteo.agregarParticipante(usuario)
        this.tematicasEncomun = matchearTematicas()
        this.miCupon = this.sorteo.crearCuponVencido()
    }

    def obtenerCantidadVinculos() {
        return this.misVinculos.size()
    }

    def obtenerCodigoCupon() {
        return this.miCupon.codigo
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

    def finalizarSorteo(Sorteo sorteo) {
        if (sorteo.estado == 0)
            return sorteo.finalizar()
        return null
    }

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
