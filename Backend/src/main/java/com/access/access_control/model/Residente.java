package com.access.access_control.model;

import org.springframework.data.mongodb.core.mapping.Document;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class Residente extends Usuario {

    private String torre;
    private String apartamento;

    public Residente(String nombre, String identificacion, String correo, String torre, String apartamento) {
        super(nombre, identificacion, correo, "residente", 2); // Se agrega el tipo explícitamente
        this.torre = torre;
        this.apartamento = apartamento;
        setQrToken(generarQRContent()); // Se genera el QR correctamente
    }

    public String getTorre() {
        return torre;
    }

    public void setTorre(String torre) {
        this.torre = torre;
    }

    public String getApartamento() {
        return apartamento;
    }

    public void setApartamento(String apartamento) {
        this.apartamento = apartamento;
    }

    @Override
    public String generarQRContent() {
        return "Tipo: Residente | Nombre: " + getNombre()
                + " | ID: " + getIdentificacion()
                + " | Torre: " + torre
                + " | Apartamento: " + apartamento
                + " | QR: " + getQrToken()
                + " | Permiso: " + getPermiso();
    }
}
