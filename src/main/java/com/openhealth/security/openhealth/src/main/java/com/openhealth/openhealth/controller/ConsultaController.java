package com.openhealth.openhealth.controller;

import com.openhealth.openhealth.dto.consulta.ConsultaCreateDTO;
import com.openhealth.openhealth.dto.consulta.ConsultaReagendarDTO;
import com.openhealth.openhealth.dto.consulta.ConsultaResponseDTO;
import com.openhealth.openhealth.dto.consulta.ConsultaUpdateDTO;
import com.openhealth.openhealth.service.ConsultaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private ConsultaService service;

    @PostMapping
    public ResponseEntity<ConsultaResponseDTO> create(@RequestBody @Valid ConsultaCreateDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultaResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<ConsultaResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsultaResponseDTO> update(@PathVariable Long id, @RequestBody @Valid ConsultaUpdateDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/reagendar")
    public ResponseEntity<Void> reagendar(@PathVariable Long id, @RequestBody @Valid ConsultaReagendarDTO dto) {
        service.reagendar(id, dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        service.cancelar(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/paciente/{cpfPaciente}")
    public ResponseEntity<List<ConsultaResponseDTO>> findByPacienteCpf(@PathVariable @Pattern(regexp = "^\\d{11}$", message = "CPF deve conter 11 dígitos numéricos") String cpfPaciente) {
        return ResponseEntity.ok(service.findByPacienteCpf(cpfPaciente));
    }
} 