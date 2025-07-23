package com.openhealth.openhealth.controller;

import com.openhealth.openhealth.dto.medico.MedicoCreateDTO;
import com.openhealth.openhealth.dto.medico.MedicoResponseDTO;
import com.openhealth.openhealth.dto.medico.MedicoUpdateDTO;
import com.openhealth.openhealth.service.MedicoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoService service;

    @PostMapping
    @Operation(summary = "Criar um médico", description = "Cria um novo médico")
    @ApiResponse(responseCode = "200", description = "Médico criado com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro ao criar médico")
    @ApiResponse(responseCode = "401", description = "Não autorizado")
    public ResponseEntity<MedicoResponseDTO> create(@RequestBody @Valid MedicoCreateDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar médico por ID", description = "Busca um médico pelo ID")
    @ApiResponse(responseCode = "200", description = "Médico encontrado com sucesso")
    @ApiResponse(responseCode = "404", description = "Médico não encontrado")
    @ApiResponse(responseCode = "401", description = "Não autorizado")
    public ResponseEntity<MedicoResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    @Operation(summary = "Buscar todos os médicos", description = "Busca todos os médicos cadastrados")
    @ApiResponse(responseCode = "200", description = "Médicos encontrados com sucesso")
    @ApiResponse(responseCode = "401", description = "Não autorizado")
    public ResponseEntity<List<MedicoResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar médico", description = "Atualiza um médico existente")
    @ApiResponse(responseCode = "200", description = "Médico atualizado com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro ao atualizar médico")
    @ApiResponse(responseCode = "404", description = "Médico não encontrado")
    @ApiResponse(responseCode = "401", description = "Não autorizado")
    public ResponseEntity<MedicoResponseDTO> update(@PathVariable Long id, @RequestBody @Valid MedicoUpdateDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar médico", description = "Deleta um médico existente")
    @ApiResponse(responseCode = "204", description = "Médico deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Médico não encontrado")
    @ApiResponse(responseCode = "401", description = "Não autorizado")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
} 