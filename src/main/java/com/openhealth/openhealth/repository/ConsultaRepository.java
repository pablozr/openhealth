package com.openhealth.openhealth.repository;

import com.openhealth.openhealth.entity.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    List<Consulta> findByPacienteCpf(String cpfPaciente);
    List<Consulta> findByMedicoIdAndDataBetween(Long medicoId, LocalDateTime inicio, LocalDateTime fim);
} 