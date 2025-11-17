package com.mlbs.finance_api.entities.enums;

public enum Moeda {

	REAL("R$"),
	DOLAR("$"),
	EURO("€");

	private final String sigla;

	Moeda(String sigla) {
		this.sigla = sigla;
	}

	public String getSigla() {
		return sigla;
	}

}
