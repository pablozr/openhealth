package com.openhealth.openhealth.controller;

import com.openhealth.openhealth.dto.consulta.ConsultaCreateDTO;
import com.openhealth.openhealth.dto.consulta.ConsultaReagendarDTO;
import com.openhealth.openhealth.dto.consulta.ConsultaResponseDTO;
import com.openhealth.openhealth.dto.consulta.ConsultaUpdateDTO;
import com.openhealth.openhealth.dto.consulta.DisponibilidadeResponseDTO;
import com.openhealth.openhealth.dto.consulta.HorarioConsultasDTO;
import com.openhealth.openhealth.service.ConsultaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearerAuth")
public class ConsultaController {

    @Autowired
    private ConsultaService service;

    @Operation(summary = "Criar uma consulta", description = "Cria uma nova consulta para um paciente")
    @ApiResponse(responseCode = "200", description = "Consulta criada com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro ao criar consulta")
    @ApiResponse(responseCode = "401", description = "Não autorizado")
    @PostMapping
    public ResponseEntity<ConsultaResponseDTO> create(@RequestBody @Valid ConsultaCreateDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @Operation(summary = "Buscar consulta por ID", description = "Busca uma consulta pelo ID")
    @ApiResponse(responseCode = "200", description = "Consulta encontrada com sucesso")
    @ApiResponse(responseCode = "404", description = "Consulta não encontrada")
    @ApiResponse(responseCode = "401", description = "Não autorizado")
    @GetMapping("/{id}")
    public ResponseEntity<ConsultaResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "Atualizar consulta", description = "Atualiza uma consulta existente")
    @ApiResponse(responseCode = "200", description = "Consulta atualizada com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro ao atualizar consulta")
    @ApiResponse(responseCode = "404", description = "Consulta não encontrada")
    @PutMapping("/{id}")
    public ResponseEntity<ConsultaResponseDTO> update(@PathVariable Long id, @RequestBody @Valid ConsultaUpdateDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Deletar consulta", description = "Deleta uma consulta existente")
    @ApiResponse(responseCode = "204", description = "Consulta deletada com sucesso")
    @ApiResponse(responseCode = "404", description = "Consulta não encontrada")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Reagendar consulta", description = "Reagenda uma consulta existente")
    @ApiResponse(responseCode = "200", description = "Consulta reagendada com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro ao reagendar consulta")
    @ApiResponse(responseCode = "404", description = "Consulta não encontrada")
    @PutMapping("/{id}/reagendar")
    public ResponseEntity<Void> reagendar(@PathVariable Long id, @RequestBody @Valid ConsultaReagendarDTO dto) {
        service.reagendar(id, dto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Cancelar consulta", description = "Cancela uma consulta existente")
    @ApiResponse(responseCode = "200", description = "Consulta cancelada com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro ao cancelar consulta")
    @ApiResponse(responseCode = "404", description = "Consulta não encontrada")
    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        service.cancelar(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Buscar consultas por CPF do paciente", description = "Busca todas as consultas de um paciente pelo CPF")
    @ApiResponse(responseCode = "200", description = "Consultas encontradas com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro ao buscar consultas")
    @ApiResponse(responseCode = "404", description = "Paciente não encontrado")
    @GetMapping("/paciente/{cpfPaciente}")
    public ResponseEntity<List<ConsultaResponseDTO>> findByPacienteCpf(@PathVariable @Pattern(regexp = "^\\d{11}$", message = "CPF deve conter 11 dígitos numéricos") String cpfPaciente) {
        return ResponseEntity.ok(service.findByPacienteCpf(cpfPaciente));
    }

    @Operation(summary = "Buscar disponibilidade de um médico", description = "Busca a disponibilidade de um médico para uma consulta")
    @ApiResponse(responseCode = "200", description = "Disponibilidade encontrada com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro ao buscar disponibilidade")
    @ApiResponse(responseCode = "404", description = "Médico não encontrado")
    @ApiResponse(responseCode = "401", description = "Não autorizado")
    @GetMapping("/disponibilidade/{medicoId}")
    public ResponseEntity<DisponibilidadeResponseDTO> getDisponibilidade(@PathVariable Long medicoId, @RequestParam(required = false) LocalDateTime inicio, @RequestParam(required = false) LocalDateTime fim) {
        return ResponseEntity.ok(service.getDisponibilidade(medicoId, inicio, fim));
    }

    @GetMapping("/hoje")
    @Operation(summary = "Buscar consultas do dia atual", description = "Busca todas as consultas do dia atual")
    @ApiResponse(responseCode = "200", description = "Consultas encontradas com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro ao buscar consultas")
    @ApiResponse(responseCode = "401", description = "Não autorizado")
    public ResponseEntity<List<HorarioConsultasDTO>> getAgendaHoje() {
        return ResponseEntity.ok(service.getAgendaHoje());
    }
} 