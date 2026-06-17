package com._CC341ASpringSigconBackend.analisis.compras.entity;

import java.util.Date;

public class OrdenInternamiento {
    private String zonaAlmacen;
    private Date fechaAsentado;

    public OrdenInternamiento() {
    }

    public String getZonaAlmacen() {
        return zonaAlmacen;
    }

    public void setZonaAlmacen(String zonaAlmacen) {
        this.zonaAlmacen = zonaAlmacen;
    }

    public Date getFechaAsentado() {
        return fechaAsentado;
    }

    public void setFechaAsentado(Date fechaAsentado) {
        this.fechaAsentado = fechaAsentado;
    }
}
