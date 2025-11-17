package com.mlbs.finance_api.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mlbs.finance_api.entities.enums.Moeda;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "gastos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Gasto {
	
	@EqualsAndHashCode.Include
	@Id
	private String id;
	
	private LocalDate data;
	private String descricao;
	private Moeda moeda;
	private BigDecimal valor;
}
