package com.mlbs.finance_api.entities.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.mlbs.finance_api.entities.enums.Moeda;

import jakarta.validation.constraints.NotNull;

public record GastoSaveRequestDTO(
		
		@NotNull
		BigDecimal valor,
		
		@NotNull
		String descricao,
		
		@NotNull
		LocalDate data,
		
		@NotNull
		Moeda moeda
) {}
