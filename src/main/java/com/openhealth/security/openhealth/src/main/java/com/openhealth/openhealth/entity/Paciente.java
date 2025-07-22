package com.openhealth.openhealth.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "paciente")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String cpf;

    private String nome;
    private String telefone;
    private String email;
} 