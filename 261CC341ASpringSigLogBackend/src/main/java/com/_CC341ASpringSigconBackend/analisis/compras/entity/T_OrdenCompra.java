package com._CC341ASpringSigconBackend.analisis.compras.entity;

import java.util.Date;

public class T_OrdenCompra {
    private Date fechaEmision;
    private float montoTotal;
    private String estado;

    public T_OrdenCompra() {
    }

    public void cambiarEstado(String nuevoEstado) {
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public float getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(float montoTotal) {
        this.montoTotal = montoTotal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
