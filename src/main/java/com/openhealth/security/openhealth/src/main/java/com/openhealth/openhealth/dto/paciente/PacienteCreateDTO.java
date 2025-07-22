package com.openhealth.openhealth.dto.paciente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record PacienteCreateDTO(
    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "^\\d{11}$", message = "CPF deve conter 11 dígitos numéricos")
    String cpf,
    @NotBlank(message = "Nome é obrigatório")
    String nome,
    @NotBlank(message = "Telefone é obrigatório")
    String telefone,
    @Email(message = "Email inválido")
    @NotBlank(message = "Email é obrigatório")
    String email
) {} 