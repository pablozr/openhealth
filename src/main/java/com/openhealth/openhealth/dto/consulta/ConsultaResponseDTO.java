package com.openhealth.openhealth.dto.consulta;

import com.openhealth.openhealth.entity.Consulta;
import com.openhealth.openhealth.entity.StatusConsulta;

import java.time.LocalDateTime;

public record ConsultaResponseDTO(
    Long id,
    LocalDateTime data,
    Long medicoId,
    String pacienteCpf,
    StatusConsulta status

) {
    public static ConsultaResponseDTO toConsultaResponseDTO(Consulta consulta) {
        return new ConsultaResponseDTO(consulta.getId(), consulta.getData(), consulta.getMedico().getId(), consulta.getPaciente().getCpf(), consulta.getStatus());
    }
} 