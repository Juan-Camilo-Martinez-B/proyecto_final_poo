package com.access.acces_control.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.access.acces_control.dto.ApiResponseDTO;
import com.access.acces_control.dto.UsuarioRegistroDTO;
import com.access.acces_control.dto.UsuarioResponseDTO;
import com.access.acces_control.service.AuthService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponseDTO<UsuarioResponseDTO>> registrarUsuario(@RequestBody UsuarioRegistroDTO registroDTO) {
        log.info("Iniciando registro de usuario: {}", registroDTO.getCorreo());

        UsuarioResponseDTO responseDTO = authService.registrarUsuario(registroDTO);

        return ResponseEntity.ok(ApiResponseDTO.success(
                "Registro iniciado. Revisa tu correo para validarlo.",
                responseDTO
        ));
    }

    @GetMapping("/verify-email")
    public ResponseEntity<ApiResponseDTO<String>> verificarCorreo(
            @RequestParam String correo,
            @RequestParam String codigo) {

        log.info("Verificando correo: {}", correo);

        String mensaje = authService.verificarCorreo(correo, codigo);

        return ResponseEntity.ok(ApiResponseDTO.success(
                "Correo verificado. QR enviado.",
                mensaje
        ));
    }
}
