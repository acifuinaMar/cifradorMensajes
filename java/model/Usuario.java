package com.cifrador.model;

public class Usuario {

    private int idUsuario;
    private String nombre;
    private String email;
    private String password;
    // Getters y Setters
    public int getIdUsuario() { 
        return idUsuario; 
    }
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario; 
    }
    public String getNombre() {
        return nombre; 
    }
    public void setNombre(String nombre) {
        this.nombre = nombre; 
    }
    public String getEmail() {
        return email; 
    }
    public void setEmail(String email) {
        this.email = email; 
    }
    public String getPassword() { 
        return password; 
    }
    public void setPassword(String password) {
        this.password = password; 
    }
}