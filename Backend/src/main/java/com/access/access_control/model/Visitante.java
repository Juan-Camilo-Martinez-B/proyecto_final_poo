package com.access.access_control.model;

import org.springframework.data.mongodb.core.mapping.Document;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class Visitante extends Usuario {

    private String motivoVisita;
    private String personaAVisitar;
    private String torreVisita;
    private String apartamentoVisita;

    public Visitante(String nombre, String identificacion, String correo, String motivoVisita, String personaAVisitar, String torreVisita, String apartamentoVisita) {
        super(nombre, identificacion, correo, "visitante", 1);
        this.motivoVisita = motivoVisita;
        this.personaAVisitar = personaAVisitar;
        this.torreVisita = torreVisita;
        this.apartamentoVisita = apartamentoVisita;
        setQrToken(generarQRContent());
    }

    public String getMotivoVisita() {
        return motivoVisita;
    }

    public void setMotivoVisita(String motivoVisita) {
        this.motivoVisita = motivoVisita;
    }

    public String getPersonaAVisitar() {
        return personaAVisitar;
    }

    public void setPersonaAVisitar(String personaAVisitar) {
        this.personaAVisitar = personaAVisitar;
    }

    public String getTorreVisita() {
        return torreVisita;
    }

    public void setTorreVisita(String torreVisita) {
        this.torreVisita = torreVisita;
    }

    public String getApartamentoVisita() {
        return apartamentoVisita;
    }

    public void setApartamentoVisita(String apartamentoVisita) {
        this.apartamentoVisita = apartamentoVisita;
    }

    @Override
    public String generarQRContent() {
        return "Tipo: Visitante | Nombre: " + getNombre()
                + " | ID: " + getIdentificacion()
                + " | Motivo: " + motivoVisita
                + " | Visitando a: " + personaAVisitar
                + " | Torre: " + torreVisita
                + " | Apartamento: " + apartamentoVisita
                + " | QR: " + getQrToken()
                + " | Permiso: " + getPermiso();
    }
}
