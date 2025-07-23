package com.openhealth.openhealth.dto.consulta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public record ConsultaReagendarDTO(
    @NotNull(message = "Data e hora da consulta são obrigatórias")
    @Future(message = "A data e hora da consulta devem ser no futuro")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime data
) {} 