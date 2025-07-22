package com.openhealth.openhealth.dto.medico;

public record MedicoResponseDTO(
    Long id,
    String nome,
    String especialidade,
    String crm
) {} 