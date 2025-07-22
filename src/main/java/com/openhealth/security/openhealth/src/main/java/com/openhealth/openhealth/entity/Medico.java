package com.openhealth.openhealth.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "medico")
public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String especialidade;
    private String crm;
} 