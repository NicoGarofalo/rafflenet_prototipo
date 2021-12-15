package com.rafflenet
import java.lang.Math
import java.time.*

class Cupon {

    String codigo = ""
    String descripcion
    LocalDate fechaVencimiento
    EstadoCupon estado = EstadoCupon.VIGENTE //Ver esto con el profe si se mantiene No Vigente cambiar a 0

    static constraints = {
    }

    def obtenerEstado () {
        return this.estado
    }

    def generarCodigo () {
        char nuevoChar
        String nuevoCodigo = ""


        for(int i = 0; i < 7; i++){
            if (i == 2 || i == 3 || i == 5){
                //numero
                nuevoChar = (char) ((int) (Math.random() * (57-48)) + 48) 
            } else { 
                //caracter
                nuevoChar = (char) ((int) (Math.random() * (90-65)) + 65)
            }
            nuevoCodigo += nuevoChar
        }
        this.codigo = nuevoCodigo
    }

    def verificarVencimiento () {
        LocalDate fechaHoy = LocalDate.now()
        if((this.fechaVencimiento < fechaHoy) && (this.estado == EstadoCupon.VIGENTE)) {
            caducar()
            return true
        }
        if(this.estado == EstadoCupon.VENCIDO) return true
        return false
    }

    def estaCanjeado () {
        return this.estado == EstadoCupon.CANJEADO
    }

    def estaVigente () {
        return this.estado == EstadoCupon.VIGENTE
    }

    def caducar() {
        this.estado = EstadoCupon.VENCIDO
    }

    def canjear() {
        if(this.estado == EstadoCupon.VIGENTE){
            this.estado = EstadoCupon.CANJEADO
            return true
        }
        return false
    }
}
