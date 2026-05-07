package com.access.access_control.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.access.access_control.dto.ApiResponseDTO;
import com.access.access_control.dto.LoginQRRequestDTO;
import com.access.access_control.exception.BadRequestException;
import com.access.access_control.service.AuthService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class LoginQRController {

    @Autowired
    private AuthService authService;

    @PostMapping(value = "/loginQR-json", consumes = "application/json")
    public ResponseEntity<ApiResponseDTO<String>> validarQRJson(@RequestBody LoginQRRequestDTO request) {
        log.info("Validando QR mediante JSON");

        if (request.getQrToken() == null || request.getQrToken().trim().isEmpty()) {
            throw new BadRequestException("QR inválido o faltante");
        }

        String mensaje = authService.verificarPermiso(request.getQrToken());
        return ResponseEntity.ok(ApiResponseDTO.success(mensaje));
    }

    @PostMapping(value = "/loginQR-file", consumes = "multipart/form-data")
    public ResponseEntity<ApiResponseDTO<String>> validarQRArchivo(@RequestParam("archivo") MultipartFile archivo) {
        log.info("Validando QR mediante archivo de imagen");

        String mensaje = authService.validarQRArchivo(archivo);
        return ResponseEntity.ok(ApiResponseDTO.success(mensaje));
    }
}
