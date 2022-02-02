package com.fiap.cerveja.dto;

import java.time.LocalDate;

import com.fiap.cerveja.domain.Tipo;


import lombok.Data;

@Data
public class CervejaDTO {
    
    private Long id;
    private String nome;
    private Tipo tipo;
    private LocalDate vencimento;
}
