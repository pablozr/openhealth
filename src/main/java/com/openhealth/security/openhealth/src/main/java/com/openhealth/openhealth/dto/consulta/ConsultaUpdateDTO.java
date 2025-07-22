package com.openhealth.openhealth.dto.consulta;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.openhealth.openhealth.entity.StatusConsulta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

public record ConsultaUpdateDTO(
    @NotBlank(message = "CPF do paciente é obrigatório")
    @Pattern(regexp = "^\\d{11}$", message = "CPF deve conter 11 dígitos numéricos")
    String cpfPaciente,
    @NotNull(message = "ID do médico é obrigatório")
    Long idMedico,
    @NotNull(message = "Data e hora da consulta são obrigatórias")
    @Future(message = "A data e hora da consulta devem ser no futuro")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime data,
    @NotNull(message = "Status da consulta é obrigatório")
    StatusConsulta status
) {} 