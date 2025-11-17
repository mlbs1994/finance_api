package com.mlbs.finance_api.entities.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.mlbs.finance_api.entities.enums.Moeda;

public record GastoUpdateRequestDTO(
		BigDecimal valor,
		String descricao,
		LocalDate data,
		Moeda moeda) 
{ }
