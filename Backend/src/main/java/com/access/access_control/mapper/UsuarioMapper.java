package com.access.access_control.mapper;

import com.access.access_control.dto.UsuarioRegistroDTO;
import com.access.access_control.dto.UsuarioResponseDTO;
import com.access.access_control.exception.BadRequestException;
import com.access.access_control.model.Administrador;
import com.access.access_control.model.Empleado;
import com.access.access_control.model.Residente;
import com.access.access_control.model.Usuario;
import com.access.access_control.model.Visitante;

public class UsuarioMapper {

    public static Usuario toEntity(UsuarioRegistroDTO dto) {
        String tipo = dto.getTipo().toLowerCase();

        return switch (tipo) {
            case "residente" ->
                new Residente(
                dto.getNombre(),
                dto.getIdentificacion(),
                dto.getCorreo(),
                dto.getTorre(),
                dto.getApartamento()
                );
            case "visitante" ->
                new Visitante(
                dto.getNombre(),
                dto.getIdentificacion(),
                dto.getCorreo(),
                dto.getMotivoVisita(),
                dto.getPersonaAVisitar(),
                dto.getTorreVisita(),
                dto.getApartamentoVisita()
                );
            case "empleado" ->
                new Empleado(
                dto.getNombre(),
                dto.getIdentificacion(),
                dto.getCorreo()
                );
            case "administrador" ->
                new Administrador(
                dto.getNombre(),
                dto.getIdentificacion(),
                dto.getCorreo()
                );
            default ->
                throw new BadRequestException("Tipo de usuario inválido: " + tipo);
        };
    }

    public static UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        return UsuarioResponseDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .identificacion(usuario.getIdentificacion())
                .correo(usuario.getCorreo())
                .tipo(usuario.getTipo())
                .permiso(usuario.getPermiso())
                .correoVerificado(usuario.isCorreoVerificado())
                .build();
    }
}
