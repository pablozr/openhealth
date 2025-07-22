package com.openhealth.openhealth.dto.paciente;

public record PacienteResponseDTO(
    Long id,
    String cpf,
    String nome,
    String telefone,
    String email
) {} 