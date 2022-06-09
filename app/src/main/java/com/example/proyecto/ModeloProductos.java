package com.example.proyecto;

public class ModeloProductos {

    private int id;
    private int idAlmacen;
    private String nombre;
    private String imagen;
    private double precio;
    private int stock;

    public ModeloProductos(int id, int idAlmacen, String nombre, String imagen, double precio, int stock) {
        this.id = id;
        this.idAlmacen = idAlmacen;
        this.nombre = nombre;
        this.imagen = imagen;
        this.precio = precio;
        this.stock = stock;
    }

    public ModeloProductos() {
        this.id = -1;
        this.idAlmacen = -1;
        this.nombre = "none";
        this.imagen = "none";
        this.precio = 0.00;
        this.stock = 0;
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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
