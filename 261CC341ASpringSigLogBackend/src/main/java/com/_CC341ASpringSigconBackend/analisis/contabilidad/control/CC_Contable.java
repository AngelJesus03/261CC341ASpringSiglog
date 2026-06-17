package com._CC341ASpringSigconBackend.analisis.contabilidad.control;

import com._CC341ASpringSigconBackend.analisis.contabilidad.entity.PagoContable;
import com._CC341ASpringSigconBackend.analisis.contabilidad.entity.AsientoContable;
import com._CC341ASpringSigconBackend.analisis.pagos.entity.Pago;

public class CC_Contable {
    private PagoContable pagoContable;
    private AsientoContable asientoContable;

    public CC_Contable() {
    }

    public void generarPagoContable(String datos) {
    }

    public void crearAsientoContable(Pago pago) {
    }

    public Boolean validarPartidaDoble() {
        return false;
    }

    public PagoContable getPagoContable() {
        return pagoContable;
    }

    public void setPagoContable(PagoContable pagoContable) {
        this.pagoContable = pagoContable;
    }

    public AsientoContable getAsientoContable() {
        return asientoContable;
    }

    public void setAsientoContable(AsientoContable asientoContable) {
        this.asientoContable = asientoContable;
    }
}
