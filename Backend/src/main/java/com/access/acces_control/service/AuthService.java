package com.access.acces_control.service;

import org.springframework.web.multipart.MultipartFile;

import com.access.acces_control.dto.UsuarioRegistroDTO;
import com.access.acces_control.dto.UsuarioResponseDTO;

public interface AuthService {

    /**
     * Inicia el registro de un usuario, generando un token de validación y enviando un correo.
     */
    UsuarioResponseDTO registrarUsuario(UsuarioRegistroDTO registroDTO);

    /**
     * Verifica el correo del usuario usando el código de validación, y si es exitoso le envía su QR.
     */
    String verificarCorreo(String correo, String codigo);

    /**
     * Verifica los permisos de acceso usando el token del QR (texto).
     */
    String verificarPermiso(String qrToken);

    /**
     * Procesa un archivo de imagen que contiene un QR y luego verifica los permisos.
     */
    String validarQRArchivo(MultipartFile archivo);
}
