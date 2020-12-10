package com.example.findafriend;

public class Usuario {
    private String latitud;
    private String longitud;

    public Usuario(String latitud,String longitud) {
        this.latitud = latitud;
        this.longitud=longitud;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }
}

