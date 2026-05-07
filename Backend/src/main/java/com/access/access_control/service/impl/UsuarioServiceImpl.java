package com.access.access_control.service.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.access.access_control.model.Usuario;
import com.access.access_control.repository.UsuarioRepository;
import com.access.access_control.service.EmailService;
import com.access.access_control.service.QrService;
import com.access.access_control.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private QrService qrService;

    @Override
    public Usuario registrarUsuario(Usuario usuario) {
        usuario.setQrToken(generateToken()); // Genera un token único
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        enviarCodigoQR(usuarioGuardado);
        return usuarioGuardado;
    }

    @Override
    public void enviarCodigoQR(Usuario usuario) {
        String qrCodeBase64 = qrService.generarQrBase64(usuario.getQrToken());

        String asunto = "Tu código QR de acceso - ControlPlus";
        String contenido = "Hola " + usuario.getNombre() + ",\n\n"
                + "Gracias por registrarte en ControlPlus.\n\n"
                + "A continuación encontrarás tu código QR de acceso.\n\n"
                + "Guarda este correo para acceder al sistema.";

        emailService.enviarCorreoConQR(usuario.getCorreo(), asunto, contenido, qrCodeBase64);
    }

    private String generateToken() {
        return UUID.randomUUID().toString(); // Genera un UUID único
    }

    @Override
    public Optional<Usuario> buscarPorToken(String qrToken) {
        return usuarioRepository.findByQrToken(qrToken);
    }
}
