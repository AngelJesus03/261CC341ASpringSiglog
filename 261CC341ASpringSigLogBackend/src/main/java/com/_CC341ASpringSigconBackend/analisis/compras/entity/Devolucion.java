package com._CC341ASpringSigconBackend.analisis.compras.entity;

public class Devolucion {
    private String motivo;
    private int cantidadRechazada;

    public Devolucion() {
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public int getCantidadRechazada() {
        return cantidadRechazada;
    }

    public void setCantidadRechazada(int cantidadRechazada) {
        this.cantidadRechazada = cantidadRechazada;
    }
}
