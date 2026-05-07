package com.access.access_control.dto;

public class UsuarioRegistroDTO {

    private String tipo;
    private String nombre;
    private String identificacion;
    private String correo;

    // Campos específicos para Residente
    private String torre;
    private String apartamento;

    // Campos específicos para Visitante
    private String motivoVisita;
    private String personaAVisitar;
    private String torreVisita;
    private String apartamentoVisita;
    public UsuarioRegistroDTO() {}

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getIdentificacion() { return identificacion; }
    public void setIdentificacion(String identificacion) { this.identificacion = identificacion; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getTorre() { return torre; }
    public void setTorre(String torre) { this.torre = torre; }
    public String getApartamento() { return apartamento; }
    public void setApartamento(String apartamento) { this.apartamento = apartamento; }
    public String getMotivoVisita() { return motivoVisita; }
    public void setMotivoVisita(String motivoVisita) { this.motivoVisita = motivoVisita; }
    public String getPersonaAVisitar() { return personaAVisitar; }
    public void setPersonaAVisitar(String personaAVisitar) { this.personaAVisitar = personaAVisitar; }
    public String getTorreVisita() { return torreVisita; }
    public void setTorreVisita(String torreVisita) { this.torreVisita = torreVisita; }
    public String getApartamentoVisita() { return apartamentoVisita; }
    public void setApartamentoVisita(String apartamentoVisita) { this.apartamentoVisita = apartamentoVisita; }
}
