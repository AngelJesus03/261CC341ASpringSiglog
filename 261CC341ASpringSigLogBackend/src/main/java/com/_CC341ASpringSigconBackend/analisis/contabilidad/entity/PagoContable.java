package com._CC341ASpringSigconBackend.analisis.contabilidad.entity;

import java.util.Date;

public class PagoContable {
    private String nroOperacion;
    private Date fechaRegistro;

    public PagoContable() {
    }

    public String getNroOperacion() {
        return nroOperacion;
    }

    public void setNroOperacion(String nroOperacion) {
        this.nroOperacion = nroOperacion;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
