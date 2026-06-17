package com._CC341ASpringSigconBackend.analisis.compras.entity;

import java.util.Date;

public class NT_Negociacion {
    private String estado;
    private Date fechaInicio;

    public NT_Negociacion() {
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
}
