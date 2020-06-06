package com.arima.inventario.model;

import java.util.List;

public class Inventario {
    private List<String> productos;

    public Inventario(List<String> productos) {
        this.productos = productos;
    }

    public Inventario() {
    }

    public List<String> getProductos() {
        return productos;
    }

    public void setProductos(List<String> productos) {
        this.productos = productos;
    }

    @Override
    public String toString() {
        return "Inventario{" +
                "productos=" + productos +
                '}';
    }
}
