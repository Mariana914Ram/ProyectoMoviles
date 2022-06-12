package com.example.proyecto;

public class ModeloAlmacen {

    private int id;
    private String nombre;
    private int imagen;

    public ModeloAlmacen(int id, String nombre, int imagen) {
        this.id = id;
        this.nombre = nombre;
        this.imagen = imagen;
    }

    public ModeloAlmacen() {
        this.id = -1;
        this.nombre = "none";
        this.imagen = 0;
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

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }
}

