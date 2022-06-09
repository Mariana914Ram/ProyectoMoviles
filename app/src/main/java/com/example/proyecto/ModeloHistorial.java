package com.example.proyecto;

public class ModeloHistorial {

    private int id;
    private int idAlmacen;
    private String suceso;

    public ModeloHistorial(int id, int idAlmacen, String suceso) {
        this.id = id;
        this.idAlmacen = idAlmacen;
        this.suceso = suceso;
    }

    public ModeloHistorial() {
        this.id = -1;
        this.idAlmacen = -1;
        this.suceso = "none";
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

    public String getSuceso() {
        return suceso;
    }

    public void setSuceso(String suceso) {
        this.suceso = suceso;
    }
}