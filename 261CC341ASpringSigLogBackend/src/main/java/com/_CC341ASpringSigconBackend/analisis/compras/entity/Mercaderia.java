package com._CC341ASpringSigconBackend.analisis.compras.entity;

public class Mercaderia {
    private String codigoBarras;
    private String descripcion;
    private int stockActual;

    public Mercaderia() {
    }

    public void actualizarStock(int cantidad) {
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getStockActual() {
        return stockActual;
    }

    public void setStockActual(int stockActual) {
        this.stockActual = stockActual;
    }
}
