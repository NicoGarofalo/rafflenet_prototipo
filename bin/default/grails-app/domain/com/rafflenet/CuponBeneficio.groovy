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

    def canjear() {
        if(estado != 1) return false
        estado = 2
        return true
    }
}
