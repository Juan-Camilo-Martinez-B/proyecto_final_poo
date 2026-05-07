package com.access.access_control.service;

import java.util.Optional;

import com.access.access_control.model.Usuario;

public interface UsuarioService {

    /**
     * Registra un nuevo usuario, genera un token y envía un correo con el QR.
     */
    Usuario registrarUsuario(Usuario usuario);

    /**
     * Envía el código QR generado al correo del usuario.
     */
    void enviarCodigoQR(Usuario usuario);

    /**
     * Busca un usuario por su token QR en la base de datos.
     */
    Optional<Usuario> buscarPorToken(String qrToken);
}
