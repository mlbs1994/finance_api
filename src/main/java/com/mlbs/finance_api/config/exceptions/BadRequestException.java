package com.mlbs.finance_api.config.exceptions;

public class BadRequestException extends RuntimeException{
	
	private static final long serialVersionUID = 2156398150735157369L;

	public BadRequestException(String mensagem) {
		super(mensagem);
	}
	
}
