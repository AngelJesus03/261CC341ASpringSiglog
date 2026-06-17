package com._CC341ASpringSigconBackend.analisis.compras.control;

import com._CC341ASpringSigconBackend.analisis.compras.entity.Recepcion;
import com._CC341ASpringSigconBackend.analisis.compras.entity.Mercaderia;
import java.util.List;

public class CC_Recepcion {
    private Recepcion recepcion;
    private List<Mercaderia> mercaderias;

    public CC_Recepcion() {
    }

    public Boolean verificarGuiaRemision(String nroGuia) {
        return false;
    }

    public void procesarIngreso() {
    }

    public void compararConOrden() {
    }

    public Recepcion getRecepcion() {
        return recepcion;
    }

    public void setRecepcion(Recepcion recepcion) {
        this.recepcion = recepcion;
    }

    public List<Mercaderia> getMercaderias() {
        return mercaderias;
    }

    public void setMercaderias(List<Mercaderia> mercaderias) {
        this.mercaderias = mercaderias;
    }
}
