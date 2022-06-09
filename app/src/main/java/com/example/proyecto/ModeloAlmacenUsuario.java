package com.example.proyecto;

public class ModeloAlmacenUsuario {

    private int id;
    private int idUsuario;
    private int idAlmacen;

    public ModeloAlmacenUsuario(int id, int idUsuario, int idAlmacen) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idAlmacen = idAlmacen;
    }

    public ModeloAlmacenUsuario() {
        this.id = -1;
        this.idUsuario = -1;
        this.idAlmacen = -1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdAlmacen() {
        return idAlmacen;
    }

    public void setIdAlmacen(int idAlmacen) {
        this.idAlmacen = idAlmacen;
    }
}
