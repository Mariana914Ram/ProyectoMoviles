package com.example.proyecto;

public class ModeloPeticionMaterial {

    private int id;
    private int idUsuario;
    private int idAlmacen;
    private int idMaterial;
    private int cantidad;
    private String volver;
    private String motivo;
    private String fecha;
    private String fechaDevuelto;
    private boolean resuelto;

    public ModeloPeticionMaterial(int id, int idUsuario, int idAlmacen, int idMaterial, int cantidad, String volver, String motivo, String fecha, String fechaDevuelto, boolean resuelto) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idAlmacen = idAlmacen;
        this.idMaterial = idMaterial;
        this.cantidad = cantidad;
        this.volver = volver;
        this.motivo = motivo;
        this.fecha = fecha;
        this.fechaDevuelto = fechaDevuelto;
        this.resuelto = resuelto;
    }

    public ModeloPeticionMaterial() {
        this.id = -1;
        this.idUsuario = -1;
        this.idAlmacen = -1;
        this.idMaterial = -1;
        this.cantidad = -1;
        this.volver = "noVover"; //noVolver y volver
        this.motivo = "laboratorio"; //laboratorio y cliente
        this.fecha = "";
        this.fechaDevuelto = "";
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

    public int getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(int idMaterial) {
        this.idMaterial = idMaterial;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getVolver() {
        return volver;
    }

    public void setVolver(String volver) {
        this.volver = volver;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFechaDevuelto() {
        return fechaDevuelto;
    }

    public void setFechaDevuelto(String fechaDevuelto) {
        this.fechaDevuelto = fechaDevuelto;
    }

    public boolean isResuelto() {
        return resuelto;
    }

    public void setResuelto(boolean resuelto) {
        this.resuelto = resuelto;
    }
}