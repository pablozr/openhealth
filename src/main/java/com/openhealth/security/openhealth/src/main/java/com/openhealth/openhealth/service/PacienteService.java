package com.openhealth.openhealth.service;

import com.openhealth.openhealth.dto.paciente.PacienteCreateDTO;
import com.openhealth.openhealth.dto.paciente.PacienteResponseDTO;
import com.openhealth.openhealth.dto.paciente.PacienteUpdateDTO;
import com.openhealth.openhealth.entity.Paciente;
import com.openhealth.openhealth.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository repository;

    public PacienteResponseDTO create(PacienteCreateDTO dto) {
        Paciente paciente = new Paciente();
        paciente.setCpf(dto.cpf());
        paciente.setNome(dto.nome());
        paciente.setTelefone(dto.telefone());
        paciente.setEmail(dto.email());
        repository.save(paciente);
        return new PacienteResponseDTO(paciente.getId(), paciente.getCpf(), paciente.getNome(), paciente.getTelefone(), paciente.getEmail());
    }

    public PacienteResponseDTO findById(Long id) {
        Paciente paciente = repository.findById(id).orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
        return new PacienteResponseDTO(paciente.getId(), paciente.getCpf(), paciente.getNome(), paciente.getTelefone(), paciente.getEmail());
    }

    public List<PacienteResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(p -> new PacienteResponseDTO(p.getId(), p.getCpf(), p.getNome(), p.getTelefone(), p.getEmail()))
                .collect(Collectors.toList());
    }

    public PacienteResponseDTO update(Long id, PacienteUpdateDTO dto) {
        Paciente paciente = repository.findById(id).orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
        if (dto.cpf() != null) paciente.setCpf(dto.cpf());
        if (dto.nome() != null) paciente.setNome(dto.nome());
        if (dto.telefone() != null) paciente.setTelefone(dto.telefone());
        if (dto.email() != null) paciente.setEmail(dto.email());
        repository.save(paciente);
        return new PacienteResponseDTO(paciente.getId(), paciente.getCpf(), paciente.getNome(), paciente.getTelefone(), paciente.getEmail());
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
} 