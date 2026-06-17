package com._CC341ASpringSigconBackend.analisis.contabilidad.entity;

import java.util.Date;

public class AsientoContable {
    private Date fechaAsiento;
    private float totalDebe;
    private float totalHaber;

    public AsientoContable() {
    }

    public Boolean validarCuadrado() {
        return false;
    }

    public Date getFechaAsiento() {
        return fechaAsiento;
    }

    public void setFechaAsiento(Date fechaAsiento) {
        this.fechaAsiento = fechaAsiento;
    }

    public float getTotalDebe() {
        return totalDebe;
    }

    public void setTotalDebe(float totalDebe) {
        this.totalDebe = totalDebe;
    }

    public float getTotalHaber() {
        return totalHaber;
    }

    public void setTotalHaber(float totalHaber) {
        this.totalHaber = totalHaber;
    }
}
