package com.rafflenet

class CuponBeneficio {


    private String codigoCupon //Aca agregar un = metodoQueGenereCodigoCupon
    private String descripcionCupon
    private Date fechaVencimiento
    private int estado = 1 //Ver esto con el profe si se mantiene No Vigente cambiar a 0

    static constraints = {
    }

    def obtenerEstado() {
        return this.estado
    }

    def verificarVencimiento() {
        Date fechaHoy = new Date()
        if(this.fechaVencimiento < fechaHoy) {
            caducar()
            return true
        }
        return false
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