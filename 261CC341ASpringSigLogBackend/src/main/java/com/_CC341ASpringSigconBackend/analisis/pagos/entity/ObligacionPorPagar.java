package com._CC341ASpringSigconBackend.analisis.pagos.entity;

public class ObligacionPorPagar {
    private float montoTotal;
    private float saldoPendiente;
    private String estado;

    public ObligacionPorPagar() {
    }

    public void actualizarEstado(String nuevoEstado) {
    }

    public float getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(float montoTotal) {
        this.montoTotal = montoTotal;
    }

    public float getSaldoPendiente() {
        return saldoPendiente;
    }

    public void setSaldoPendiente(float saldoPendiente) {
        this.saldoPendiente = saldoPendiente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
