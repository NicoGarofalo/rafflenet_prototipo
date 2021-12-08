package com.rafflenet
import java.time.*

class Vinculo {

    Usuario usuario
    Sorteo sorteo
    Cupon miCupon
    Set<Tematica> tematicasEnComun

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
            participarConCuponCanjeado()

        this.usuario.misVinculos << this
    }

    // Para test
    def vincularConCuponVencido(Usuario usuario, Sorteo sorteo) {
        this.usuario = usuario
        this.sorteo = sorteo

        if(this.usuario.rol == 0)
            participarConCuponVencido()
        
        this.usuario.misVinculos << this
    }

    def matchearTematicas() {
        return this.usuario.tematicas.intersect(this.sorteo.tematicas)
    }
    
    def participar() {
        this.sorteo.agregarParticipante(this.usuario)
        this.tematicasEnComun = matchearTematicas()
        this.miCupon = this.sorteo.crearCupon()
    }
    // Para test
    def participarConCuponCanjeado() {
        this.sorteo.agregarParticipante(this.usuario)
        this.tematicasEnComun = matchearTematicas()
        this.miCupon = this.sorteo.crearCuponCanjeado()
    }
    // Para test
    def participarConCuponVencido() {
        this.sorteo.agregarParticipante(this.usuario)
        this.tematicasEnComun = matchearTematicas()
        this.miCupon = this.sorteo.crearCuponVencido()
    }

    def obtenerCodigoCupon() {
        return this.miCupon.codigo
    }

    def crearSorteo( String descripPremio, String imgPremio, LocalDate fechaVencimiento, 
    int tipo, Set<Tematica> tematicas,int limiteParticipante, String descripSorteo) {

        EstadisticaSorteo nuevaEstadistica = new EstadisticaSorteo(
            limiteParticipante: limiteParticipante,
            descripcion: descripSorteo
        )

        this.sorteo = new Sorteo(
            descripcionPremio: descripPremio,
            imagenPremio: imgPremio,
            tipo: tipo,
            tematicas: tematicas,
            participantes: [],
            ganadorSorteo: "",
            fechaVencimiento: fechaVencimiento,
            estadistica: nuevaEstadistica
        )

        return this.sorteo
    }

    def finalizarSorteo(Sorteo sorteo) {
        if (this.sorteo.estado == 0)
            return this.sorteo.finalizar()
        return null
    }

    def canjearCupon(String codigoCupon) {
        Cupon cupon = this.sorteo.obtenerCupon(codigoCupon)
        if(!cupon)  return 'Cupon no encontrado'
        
        if (cupon.verificarVencimiento()) return 'Cupon vencido' 
        if (cupon.estaCanjeado()) return 'Cupon ya canjeado'
        if (cupon.canjear()) return 'Cupon canjeado exitosamente'
        return 'Hubo error al canjear cupon'
    }
}
