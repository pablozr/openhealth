package com.openhealth.openhealth.dto.usuario;

import jakarta.validation.constraints.NotBlank;

public record UsuarioUpdateDTO(
    @NotBlank(message = "Username é obrigatório")
    String username,
    @NotBlank(message = "Password é obrigatório")
    String password,
    @NotBlank(message = "Role é obrigatória")
    String role
) {} 