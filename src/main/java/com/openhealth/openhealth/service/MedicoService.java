package com.openhealth.openhealth.service;

import com.openhealth.openhealth.dto.medico.MedicoCreateDTO;
import com.openhealth.openhealth.dto.medico.MedicoResponseDTO;
import com.openhealth.openhealth.dto.medico.MedicoUpdateDTO;
import com.openhealth.openhealth.entity.Medico;
import com.openhealth.openhealth.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository repository;

    public MedicoResponseDTO create(MedicoCreateDTO dto) {
        Medico medico = new Medico();
        medico.setNome(dto.nome());
        medico.setEspecialidade(dto.especialidade());
        medico.setCrm(dto.crm());
        repository.save(medico);
        return new MedicoResponseDTO(medico.getId(), medico.getNome(), medico.getEspecialidade(), medico.getCrm());
    }

    public MedicoResponseDTO findById(Long id) {
        Medico medico = repository.findById(id).orElseThrow(() -> new RuntimeException("Médico não encontrado"));
        return new MedicoResponseDTO(medico.getId(), medico.getNome(), medico.getEspecialidade(), medico.getCrm());
    }

    public List<MedicoResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(m -> new MedicoResponseDTO(m.getId(), m.getNome(), m.getEspecialidade(), m.getCrm()))
                .collect(Collectors.toList());
    }

    public MedicoResponseDTO update(Long id, MedicoUpdateDTO dto) {
        Medico medico = repository.findById(id).orElseThrow(() -> new RuntimeException("Médico não encontrado"));
        if (dto.nome() != null) medico.setNome(dto.nome());
        if (dto.especialidade() != null) medico.setEspecialidade(dto.especialidade());
        if (dto.crm() != null) medico.setCrm(dto.crm());
        repository.save(medico);
        return new MedicoResponseDTO(medico.getId(), medico.getNome(), medico.getEspecialidade(), medico.getCrm());
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
} 