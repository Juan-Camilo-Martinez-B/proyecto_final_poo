package com.access.access_control.dto;

public class UsuarioResponseDTO {

    private String id;
    private String nombre;
    private String identificacion;
    private String correo;
    private String tipo;
    private int permiso;
    private boolean correoVerificado;

    public UsuarioResponseDTO() {}

    public UsuarioResponseDTO(String id, String nombre, String identificacion, String correo, String tipo, int permiso, boolean correoVerificado) {
        this.id = id;
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.correo = correo;
        this.tipo = tipo;
        this.permiso = permiso;
        this.correoVerificado = correoVerificado;
    }

    public static UsuarioResponseDTOBuilder builder() {
        return new UsuarioResponseDTOBuilder();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getIdentificacion() { return identificacion; }
    public void setIdentificacion(String identificacion) { this.identificacion = identificacion; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public int getPermiso() { return permiso; }
    public void setPermiso(int permiso) { this.permiso = permiso; }
    public boolean isCorreoVerificado() { return correoVerificado; }
    public void setCorreoVerificado(boolean correoVerificado) { this.correoVerificado = correoVerificado; }

    public static class UsuarioResponseDTOBuilder {
        private String id;
        private String nombre;
        private String identificacion;
        private String correo;
        private String tipo;
        private int permiso;
        private boolean correoVerificado;

        public UsuarioResponseDTOBuilder id(String id) { this.id = id; return this; }
        public UsuarioResponseDTOBuilder nombre(String nombre) { this.nombre = nombre; return this; }
        public UsuarioResponseDTOBuilder identificacion(String identificacion) { this.identificacion = identificacion; return this; }
        public UsuarioResponseDTOBuilder correo(String correo) { this.correo = correo; return this; }
        public UsuarioResponseDTOBuilder tipo(String tipo) { this.tipo = tipo; return this; }
        public UsuarioResponseDTOBuilder permiso(int permiso) { this.permiso = permiso; return this; }
        public UsuarioResponseDTOBuilder correoVerificado(boolean correoVerificado) { this.correoVerificado = correoVerificado; return this; }

        public UsuarioResponseDTO build() {
            return new UsuarioResponseDTO(id, nombre, identificacion, correo, tipo, permiso, correoVerificado);
        }
    }
}
