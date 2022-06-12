package com.example.proyecto;

public class ModeloUsuario {

    private int id;
    private String correo;
    private String contrasena;
    private String nombre;
    private String apellidos;
    private String tipo;
    private int imagen;


    public ModeloUsuario(int id, String correo, String contrasena, String nombre, String apellidos, String tipo, int imagen) {
        this.id = id;
        this.correo = correo;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.tipo = tipo;
        this.imagen = imagen;
    }

    public ModeloUsuario() {
        this.id = 0;
        this.correo = "none";
        this.contrasena = "none";
        this.nombre = "none";
        this.apellidos = "none";
        this.tipo = "normal";
        this.imagen = 1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }
}