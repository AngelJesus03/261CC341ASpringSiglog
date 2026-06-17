package com._CC341ASpringSigconBackend.analisis.compras.entity;

import java.util.Date;

public class Recepcion {
    private Date fechaLlegada;
    private String estadoValidacion;

    public Recepcion() {
    }

    public Date getFechaLlegada() {
        return fechaLlegada;
    }

    public void setFechaLlegada(Date fechaLlegada) {
        this.fechaLlegada = fechaLlegada;
    }

    public String getEstadoValidacion() {
        return estadoValidacion;
    }

    public void setEstadoValidacion(String estadoValidacion) {
        this.estadoValidacion = estadoValidacion;
    }
}
