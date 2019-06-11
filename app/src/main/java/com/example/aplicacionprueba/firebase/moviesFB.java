package com.example.aplicacionprueba.firebase;

public class moviesFB {
    protected String pelicula_id;

    public moviesFB() {
        this.pelicula_id = "";
    }

    public moviesFB(String pelicula_id) {
        this.pelicula_id = pelicula_id;
    }

    public void setPelicula_id(String pelicula_id) {
        this.pelicula_id = pelicula_id;
    }

    public String getPelicula_id() {
        return this.pelicula_id;
    }
}