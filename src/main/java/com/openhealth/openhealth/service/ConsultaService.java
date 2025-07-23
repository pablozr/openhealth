package com.openhealth.openhealth.service;

import com.openhealth.openhealth.dto.consulta.ConsultaCreateDTO;
import com.openhealth.openhealth.dto.consulta.ConsultaReagendarDTO;
import com.openhealth.openhealth.dto.consulta.ConsultaResponseDTO;
import com.openhealth.openhealth.dto.consulta.ConsultaUpdateDTO;
import com.openhealth.openhealth.dto.consulta.DisponibilidadeResponseDTO;
import com.openhealth.openhealth.entity.Consulta;
import com.openhealth.openhealth.entity.StatusConsulta;
import com.openhealth.openhealth.repository.ConsultaRepository;
import com.openhealth.openhealth.repository.MedicoRepository;
import com.openhealth.openhealth.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository repository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    public ConsultaResponseDTO create(ConsultaCreateDTO dto) {
        var paciente = pacienteRepository.findByCpf(dto.cpfPaciente()).orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
        var medico = medicoRepository.findById(dto.idMedico()).orElseThrow(() -> new RuntimeException("Médico não encontrado"));
        Consulta consulta = new Consulta();
        consulta.setData(dto.data());
        consulta.setMedico(medico);
        consulta.setPaciente(paciente);
        consulta.setStatus(StatusConsulta.AGENDADA);
        repository.save(consulta);
        return new ConsultaResponseDTO(consulta.getId(), consulta.getData(), consulta.getMedico().getId(), consulta.getPaciente().getCpf(), consulta.getStatus());
    }

    public ConsultaResponseDTO findById(Long id) {
        Consulta consulta = repository.findById(id).orElseThrow(() -> new RuntimeException("Consulta não encontrada"));
        return new ConsultaResponseDTO(consulta.getId(), consulta.getData(), consulta.getMedico().getId(), consulta.getPaciente().getCpf(), consulta.getStatus());
    }

    public ConsultaResponseDTO update(Long id, ConsultaUpdateDTO dto) {
        Consulta consulta = repository.findById(id).orElseThrow(() -> new RuntimeException("Consulta não encontrada"));
        if (dto.cpfPaciente() != null) {
            var paciente = pacienteRepository.findByCpf(dto.cpfPaciente()).orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
            consulta.setPaciente(paciente);
        }
        if (dto.idMedico() != null) {
            var medico = medicoRepository.findById(dto.idMedico()).orElseThrow(() -> new RuntimeException("Médico não encontrado"));
            consulta.setMedico(medico);
        }
        if (dto.data() != null) consulta.setData(dto.data());
        if (dto.status() != null) consulta.setStatus(dto.status());
        repository.save(consulta);
        return new ConsultaResponseDTO(consulta.getId(), consulta.getData(), consulta.getMedico().getId(), consulta.getPaciente().getCpf(), consulta.getStatus());
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public void reagendar(Long id, ConsultaReagendarDTO dto) {
        Consulta consulta = repository.findById(id).orElseThrow(() -> new RuntimeException("Consulta não encontrada"));
        consulta.setData(dto.data());
        repository.save(consulta);
    }

    public void cancelar(Long id) {
        Consulta consulta = repository.findById(id).orElseThrow(() -> new RuntimeException("Consulta não encontrada"));
        consulta.setStatus(StatusConsulta.CANCELADA);
        repository.save(consulta);
    }

    public List<ConsultaResponseDTO> findByPacienteCpf(String cpfPaciente) {
        pacienteRepository.findByCpf(cpfPaciente).orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
        List<Consulta> consultas = repository.findByPacienteCpf(cpfPaciente);
        return consultas.stream() 
                .map(c -> new ConsultaResponseDTO(c.getId(), c.getData(), c.getMedico().getId(), c.getPaciente().getCpf(), c.getStatus()))
                .collect(Collectors.toList());
    }

    public DisponibilidadeResponseDTO getDisponibilidade(Long medicoId, LocalDateTime inicio, LocalDateTime fim) {
        medicoRepository.findById(medicoId).orElseThrow(() -> new RuntimeException("Médico não encontrado"));
        if (inicio == null) inicio = LocalDateTime.now();
        if (fim == null) fim = inicio.plusDays(30);

        LocalDateTime startData = inicio.withHour(9).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endData = fim.withHour(18).withMinute(0).withSecond(0).withNano(0);

        List<Consulta> consultas = repository.findByMedicoIdAndDataBetween(medicoId, startData, endData);

        Set<LocalDateTime> ocupados = consultas.stream()
                .map(Consulta::getData)
                .collect(Collectors.toSet());

        List<LocalDateTime> disponiveis = new ArrayList<>();
        LocalDateTime current = startData;
        while (!current.isAfter(endData)) {
            DayOfWeek dia = current.getDayOfWeek();
            if (dia != DayOfWeek.SATURDAY && dia != DayOfWeek.SUNDAY) {
                for (int i = 9; i < 18; i++) {
                    LocalDateTime slot = current.withHour(i).withMinute(0).withSecond(0).withNano(0);
                    if (!ocupados.contains(slot)) {
                        disponiveis.add(slot);
                    }
                }
            }
            current = current.plusDays(1);
        }
        return new DisponibilidadeResponseDTO(disponiveis);
    }
} 