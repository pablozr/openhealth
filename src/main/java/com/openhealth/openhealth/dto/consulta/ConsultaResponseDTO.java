package com.openhealth.openhealth.dto.consulta;

import com.openhealth.openhealth.entity.StatusConsulta;

import java.time.LocalDateTime;

public record ConsultaResponseDTO(
    Long id,
    LocalDateTime data,
    Long medicoId,
    String pacienteCpf,
    StatusConsulta status
) {} 