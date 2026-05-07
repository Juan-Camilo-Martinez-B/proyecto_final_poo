package com.access.acces_control.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.access.acces_control.dto.UsuarioRegistroDTO;
import com.access.acces_control.dto.UsuarioResponseDTO;
import com.access.acces_control.exception.BadRequestException;
import com.access.acces_control.exception.EmailSendingException;
import com.access.acces_control.exception.ResourceNotFoundException;
import com.access.acces_control.mapper.UsuarioMapper;
import com.access.acces_control.model.Usuario;
import com.access.acces_control.repository.UsuarioRepository;
import com.access.acces_control.service.AuthService;
import com.access.acces_control.service.EmailService;
import com.access.acces_control.service.QrService;
import com.access.acces_control.service.UsuarioService;
import com.access.acces_control.util.ValidationUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private QrService qrService;

    @Autowired
    private EmailService emailService;

    @Value("${app.base.url}")
    private String appBaseUrl;

    private final Map<String, Usuario> usuariosPendientes = new HashMap<>();
    private final Map<String, String> codigosValidacion = new HashMap<>();

    @Override
    public UsuarioResponseDTO registrarUsuario(UsuarioRegistroDTO registroDTO) {
        log.info("Iniciando registro de usuario: {}", registroDTO.getCorreo());

        ValidationUtil.validateRegistroDTO(registroDTO);

        if (usuarioRepository.existsByCorreo(registroDTO.getCorreo())) {
            throw new BadRequestException("Este correo ya está registrado");
        }

        Usuario usuario = UsuarioMapper.toEntity(registroDTO);
        usuario.setCorreoVerificado(false);

        usuariosPendientes.put(usuario.getCorreo(), usuario);

        String codigoValidacion = UUID.randomUUID().toString();
        codigosValidacion.put(usuario.getCorreo(), codigoValidacion);

        enviarCorreoValidacion(usuario, codigoValidacion);

        log.info("Usuario registrado exitosamente (pendiente de verificación): {}", usuario.getCorreo());

        return UsuarioMapper.toResponseDTO(usuario);
    }

    @Override
    public String verificarCorreo(String correo, String codigo) {
        log.info("Verificando correo: {}", correo);

        Usuario usuario = usuariosPendientes.get(correo);

        if (usuario == null) {
            throw new ResourceNotFoundException("Usuario no encontrado o ya verificado");
        }

        String codigoEsperado = codigosValidacion.get(correo);
        if (codigoEsperado == null || !codigoEsperado.equals(codigo)) {
            throw new BadRequestException("Código de validación incorrecto o expirado");
        }

        // Marcar correo como verificado y guardar en la base de datos
        usuario.setCorreoVerificado(true);
        usuarioRepository.save(usuario);

        // Limpiar datos temporales
        usuariosPendientes.remove(correo);
        codigosValidacion.remove(correo);

        // Generar y enviar QR
        enviarQRPorCorreo(usuario);

        log.info("Correo verificado exitosamente: {}", correo);

        return "Tu código QR ha sido enviado a tu correo electrónico";
    }

    @Override
    public String validarQRArchivo(MultipartFile archivo) {
        if (archivo == null || archivo.isEmpty()) {
            throw new BadRequestException("Archivo no proporcionado");
        }

        String qrToken = qrService.procesarQRDesdeArchivo(archivo);

        if (qrToken == null || qrToken.isEmpty()) {
            throw new BadRequestException("QR inválido o no legible");
        }

        return verificarPermiso(qrToken);
    }

    @Override
    public String verificarPermiso(String qrToken) {
        Usuario usuario = usuarioService.buscarPorToken(qrToken)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado o QR inválido"));

        if (!usuario.isCorreoVerificado()) {
            return "❌ Acceso denegado: el usuario no ha verificado su correo electrónico";
        }

        int permiso = usuario.getPermiso();
        String nombreUsuario = usuario.getNombre();

        log.info("Acceso validado para usuario: {} con permiso nivel {}", nombreUsuario, permiso);

        return obtenerMensajeAcceso(permiso, nombreUsuario);
    }

    private String obtenerMensajeAcceso(int permiso, String nombreUsuario) {
        return switch (permiso) {
            case 3 -> String.format("✅ Bienvenid@ %s - Acceso completo (todas las puertas)", nombreUsuario);
            case 2 -> String.format("✅ Bienvenid@ %s - Acceso a puerta 1 y 2", nombreUsuario);
            case 1 -> String.format("✅ Bienvenid@ %s - Acceso solo a puerta 1", nombreUsuario);
            default -> String.format("❌ Permiso denegado para %s - Nivel de acceso inválido", nombreUsuario);
        };
    }

    private void enviarCorreoValidacion(Usuario usuario, String codigoValidacion) {
        try {
            String enlaceVerificacion = String.format(
                    "%s/api/auth/verify-email?correo=%s&codigo=%s",
                    appBaseUrl,
                    usuario.getCorreo(),
                    codigoValidacion
            );

            String contenido = String.format(
                    "Hola %s,\n\n"
                    + "Por favor, haz clic en el siguiente enlace para validar tu correo:\n\n"
                    + "%s\n\n"
                    + "Este enlace expirará en 10 minutos.\n\n"
                    + "Saludos,\nEquipo ControlPlus",
                    usuario.getNombre(),
                    enlaceVerificacion
            );

            emailService.enviarCorreo(
                    usuario.getCorreo(),
                    "Validación de correo - ControlPlus",
                    contenido
            );
        } catch (Exception e) {
            log.error("Error al enviar correo de validación: {}", e.getMessage());
            throw new EmailSendingException("Error enviando correo de validación", e);
        }
    }

    private void enviarQRPorCorreo(Usuario usuario) {
        try {
            log.info("Intentando enviar QR a: {}", usuario.getCorreo());
            log.info("QR Token del usuario: {}", usuario.getQrToken());

            if (usuario.getQrToken() == null || usuario.getQrToken().isEmpty()) {
                log.error("El usuario no tiene QR Token asignado");
                throw new IllegalStateException("El usuario no tiene QR Token asignado");
            }

            String qrBase64 = qrService.generarQrBase64(usuario.getQrToken());
            log.info("QR generado correctamente, tamaño Base64: {}", qrBase64.length());

            String contenido = String.format(
                    "Hola %s,\n\n"
                    + "Tu registro ha sido completado exitosamente.\n\n"
                    + "Adjunto encontrarás tu código QR de acceso.\n"
                    + "Nivel de permiso: %d\n\n"
                    + "Saludos,\nEquipo ControlPlus",
                    usuario.getNombre(),
                    usuario.getPermiso()
            );

            boolean enviado = emailService.enviarCorreoConQR(
                    usuario.getCorreo(),
                    "Código QR de Acceso - ControlPlus",
                    contenido,
                    qrBase64
            );

            if (enviado) {
                log.info("Correo con QR enviado exitosamente a: {}", usuario.getCorreo());
            } else {
                log.error("El correo con QR no pudo ser enviado");
            }
        } catch (Exception e) {
            log.error("Error al enviar QR por correo a {}: {}", usuario.getCorreo(), e.getMessage(), e);
            throw new EmailSendingException("Error enviando QR por correo", e);
        }
    }
}
