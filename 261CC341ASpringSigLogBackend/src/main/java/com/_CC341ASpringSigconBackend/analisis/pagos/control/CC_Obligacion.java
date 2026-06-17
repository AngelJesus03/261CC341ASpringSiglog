package com._CC341ASpringSigconBackend.analisis.pagos.control;

import com._CC341ASpringSigconBackend.analisis.pagos.entity.ObligacionPorPagar;

public class CC_Obligacion {
    private ObligacionPorPagar obligacion;

    public CC_Obligacion() {
    }

    public float calcularSaldosPendientes() {
        return 0.0f;
    }

    public void actualizarEstadoObligacion(String estado) {
    }

    public ObligacionPorPagar getObligacion() {
        return obligacion;
    }

    public void setObligacion(ObligacionPorPagar obligacion) {
        this.obligacion = obligacion;
    }
}
