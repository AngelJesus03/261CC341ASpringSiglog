package com._CC341ASpringSigconBackend.analisis.compras.control;

import com._CC341ASpringSigconBackend.analisis.compras.entity.NT_Negociacion;

public class CC_Negociacion {
    private NT_Negociacion negociacion;

    public CC_Negociacion() {
    }

    public void registrarNegociacion(String datos) {
    }

    public Boolean validarTerminos() {
        return false;
    }

    public NT_Negociacion getNegociacion() {
        return negociacion;
    }

    public void setNegociacion(NT_Negociacion negociacion) {
        this.negociacion = negociacion;
    }
}
