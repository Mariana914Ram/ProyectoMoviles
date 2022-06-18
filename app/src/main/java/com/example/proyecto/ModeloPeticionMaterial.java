package com.example.proyecto;

public class ModeloPeticionMaterial {

    private int id;
    private int idUsuario;
    private int idAlmacen;
    private int idMaterial;
    private String nombreUsuario;
    private String nombreMaterial;
    private int cantidad;
    private String volver;
    private String motivo;
    private String fecha;
    private String fechaSalida;
    private String fechaDevuelto;
    private String status;
    private String descripcion;

    public ModeloPeticionMaterial(int id, int idUsuario, int idAlmacen, int idMaterial, String nombreUsuario, String nombreMaterial, int cantidad, String volver, String motivo, String fecha, String fechaSalida, String fechaDevuelto, String status, String descripcion) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idAlmacen = idAlmacen;
        this.idMaterial = idMaterial;
        this.nombreUsuario = nombreUsuario;
        this.nombreMaterial = nombreMaterial;
        this.cantidad = cantidad;
        this.volver = volver;
        this.motivo = motivo;
        this.fecha = fecha;
        this.fechaSalida = fechaSalida;
        this.fechaDevuelto = fechaDevuelto;
        this.status = status;
        this.descripcion = descripcion;
    }

    public ModeloPeticionMaterial() {
        this.id = -1;
        this.idUsuario = -1;
        this.idAlmacen = -1;
        this.idMaterial = -1;
        this.nombreUsuario = "none";
        this.nombreMaterial = "none";
        this.cantidad = -1;
        this.volver = "noVover"; //noVolver y volver
        this.motivo = "laboratorio"; //laboratorio y cliente
        this.fecha = "";
        this.fechaSalida = "";
        this.fechaDevuelto = "";
        this.status = "aceptado"; //aceptado o rechazado o pendiente o devuelto
        this.descripcion = "";
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

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombreMaterial() {
        return nombreMaterial;
    }

    public void setNombreMaterial(String nombreMaterial) {
        this.nombreMaterial = nombreMaterial;
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

    public String getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(String fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getFechaDevuelto() {
        return fechaDevuelto;
    }

    public void setFechaDevuelto(String fechaDevuelto) {
        this.fechaDevuelto = fechaDevuelto;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}