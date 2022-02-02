package com.fiap.cerveja.dto;

import java.time.LocalDate;

public class CervejaVencimentoDTO {

    private LocalDate vencimento;

    public LocalDate getVencimento() {
        return vencimento;
    }

    public void setVencimento(LocalDate vencimento) {
        this.vencimento = vencimento;
    }
}