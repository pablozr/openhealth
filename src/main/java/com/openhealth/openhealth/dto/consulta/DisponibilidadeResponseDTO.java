package com.openhealth.openhealth.dto.consulta;

import java.time.LocalDateTime;
import java.util.List;

public record DisponibilidadeResponseDTO(
    List<LocalDateTime> disponiveis
) {
}
