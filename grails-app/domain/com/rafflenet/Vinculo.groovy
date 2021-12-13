package com.rafflenet
import java.time.*

class Vinculo {

    Usuario usuario
    Sorteo sorteo
    Cupon miCupon
    Set<Tematica> tematicasEnComun

    static constraints = {

    }

    Vinculo(Usuario usuario, Sorteo sorteo) {
        this.usuario = usuario
        this.sorteo = sorteo
    }
    
    def vincular() {
        if(this.usuario.rol == Rol.PARTICIPANTE)
            participar()
        
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

    def obtenerCodigoCupon() {
        return this.miCupon.codigo
    }


    def finalizarSorteo(Sorteo sorteo) {
        if (this.sorteo.estado == EstadoSorteo.FINALIZADO) 
            throw new Exception("El sorteo ya finalizó")
        return this.sorteo.finalizar()
        
    }

    def canjearCupon(String codigoCupon) {
        Cupon cupon = this.sorteo.obtenerCupon(codigoCupon)

        if(!cupon)  throw new Exception("Cupon no encontrado")
        if (cupon.verificarVencimiento()) throw new Exception("Cupon vencido") 
        if (cupon.estaCanjeado()) throw new Exception("Cupon ya canjeado")
        if (cupon.canjear()) return EstadoCupon.CANJEADO

        throw new Exception("Hubo error al canjear cupon")
    }
}
