package com.mlbs.finance_api.entities.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.mlbs.finance_api.entities.enums.Moeda;

public record GastoResponseDTO(String id, LocalDate data, String descricao, Moeda moeda, BigDecimal valor) {
}
