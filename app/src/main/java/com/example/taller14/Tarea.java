package com.example.taller14;

public class Tarea {
    private String id, descripcion, nombre, status, fecha, usuario;

    public Tarea() {
    }

    public Tarea (String id, String descripcion, String nombre, String status, String fecha, String usuario) {
        this.id = id;
        this.usuario = usuario;
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.status = status;
        this.fecha = fecha;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
