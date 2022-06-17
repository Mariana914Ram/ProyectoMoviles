package com.example.proyecto;

public class ModeloMateriales {
    private int id;
    private int idAlmacen;
    private String nombre;
    private int stock;

    public ModeloMateriales(int id, int idAlmacen, String nombre, int stock) {
        this.id = id;
        this.idAlmacen = idAlmacen;
        this.nombre = nombre;
        this.stock = stock;
    }

    public ModeloMateriales() {
        this.id = -1;
        this.idAlmacen = -1;
        this.nombre = "none";
        this.stock = -1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdAlmacen() {
        return idAlmacen;
    }

    public void setIdAlmacen(int idAlmacen) {
        this.idAlmacen = idAlmacen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}