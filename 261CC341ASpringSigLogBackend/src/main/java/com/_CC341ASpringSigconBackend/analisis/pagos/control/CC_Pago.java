package com._CC341ASpringSigconBackend.analisis.pagos.control;

import com._CC341ASpringSigconBackend.analisis.pagos.entity.Pago;

public class CC_Pago {
    private Pago pago;

    public CC_Pago() {
    }

    public Boolean validarFondos() {
        return false;
    }

    public void registrarMovimientoCaja() {
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }
}
