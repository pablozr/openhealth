package com.openhealth.openhealth.controller;

import com.openhealth.openhealth.dto.paciente.PacienteCreateDTO;
import com.openhealth.openhealth.dto.paciente.PacienteResponseDTO;
import com.openhealth.openhealth.dto.paciente.PacienteUpdateDTO;
import com.openhealth.openhealth.service.PacienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService service;

    @PostMapping
    @Operation(summary = "Criar um paciente", description = "Cria um novo paciente")
    @ApiResponse(responseCode = "200", description = "Paciente criado com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro ao criar paciente")
    @ApiResponse(responseCode = "401", description = "Não autorizado")
    public ResponseEntity<PacienteResponseDTO> create(@RequestBody @Valid PacienteCreateDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar paciente por ID", description = "Busca um paciente pelo ID")
    @ApiResponse(responseCode = "200", description = "Paciente encontrado com sucesso")
    @ApiResponse(responseCode = "404", description = "Paciente não encontrado")
    @ApiResponse(responseCode = "401", description = "Não autorizado")
    public ResponseEntity<PacienteResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    @Operation(summary = "Buscar todos os pacientes", description = "Busca todos os pacientes cadastrados")
    @ApiResponse(responseCode = "200", description = "Pacientes encontrados com sucesso")
    @ApiResponse(responseCode = "401", description = "Não autorizado")
    public ResponseEntity<List<PacienteResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar paciente", description = "Atualiza um paciente existente")
    @ApiResponse(responseCode = "200", description = "Paciente atualizado com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro ao atualizar paciente")
    @ApiResponse(responseCode = "404", description = "Paciente não encontrado")
    @ApiResponse(responseCode = "401", description = "Não autorizado")
    public ResponseEntity<PacienteResponseDTO> update(@PathVariable Long id, @RequestBody @Valid PacienteUpdateDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar paciente", description = "Deleta um paciente existente")
    @ApiResponse(responseCode = "204", description = "Paciente deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Paciente não encontrado")
    @ApiResponse(responseCode = "401", description = "Não autorizado")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
} 