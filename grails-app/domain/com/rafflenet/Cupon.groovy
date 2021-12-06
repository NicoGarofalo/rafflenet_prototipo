package com.rafflenet
import java.lang.Math
import java.time.*

class Cupon {

    String codigo = ""
    String descripcion
    LocalDate fechaVencimiento
    int estado = 1 //Ver esto con el profe si se mantiene No Vigente cambiar a 0

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
        if((this.fechaVencimiento < fechaHoy) && (this.estado == 1)) {
            caducar()
            return true
        }
        if(this.estado == 3) return true
        return false
    }

    def estaCanjeado () {
        return this.estado == 2
    }

    def caducar() {
        this.estado = 3
    }

    def canjear() {
        if(this.estado != 1) return false
        this.estado = 2
        return true
    }
}
