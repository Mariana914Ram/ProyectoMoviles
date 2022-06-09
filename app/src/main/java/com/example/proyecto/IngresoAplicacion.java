package com.example.proyecto;

public class IngresoAplicacion {
    private String id;
    private String correo;
    private String contrasena;
    private String nombre;
    private String apellidos;
    private String tipo;
    private boolean registrado;

    public IngresoAplicacion(String id, String correo, String contrasena, String nombre, String apellidos, String tipo, boolean registrado) {
        this.id = id;
        this.correo = correo;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.tipo = tipo;
        this.registrado = registrado;
    }

    public IngresoAplicacion() {
        this.id = "-1";
        this.correo = "none";
        this.contrasena = "none";
        this.nombre = "none";
        this.apellidos = "none";
        this.tipo = "normal";
        this.registrado = false;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public boolean isRegistrado() {
        return registrado;
    }

    public void setRegistrado(boolean registrado) {
        this.registrado = registrado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}