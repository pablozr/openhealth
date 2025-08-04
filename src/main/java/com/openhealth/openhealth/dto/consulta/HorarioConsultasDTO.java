package com.openhealth.openhealth.dto.consulta;

import java.util.List;

public record HorarioConsultasDTO(
    String hora,
    List<ConsultaResponseDTO> consultas
) {

}
