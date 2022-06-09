package com.example.proyecto;

public class ModeloAlmacen {

    private int id;
    private String nombre;

    public ModeloAlmacen(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public ModeloAlmacen() {
        this.id = -1;
        this.nombre = "none";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
