package com.example.proyecto;

public class ModeloAviso {

    private int id;
    private int idUsuario;
    private int idAlmacen;
    private String aviso;
    private String fecha;
    private boolean resuelto;

    public ModeloAviso(int id, int idUsuario, int idAlmacen, String aviso, String fecha, boolean resuelto) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idAlmacen = idAlmacen;
        this.aviso = aviso;
        this.fecha = fecha;
        this.resuelto = resuelto;
    }

    public ModeloAviso() {
        this.id = -1;
        this.idUsuario = -1;
        this.idAlmacen = -1;
        this.aviso = "none";
        this.fecha = "00-00-0000";
        this.resuelto = false;
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

    public String getAviso() {
        return aviso;
    }

    public void setAviso(String aviso) {
        this.aviso = aviso;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public boolean isResuelto() {
        return resuelto;
    }

    public void setResuelto(boolean resuelto) {
        this.resuelto = resuelto;
    }
}
