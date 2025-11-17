package com.mlbs.finance_api.entities.dto;

import com.mlbs.finance_api.config.exceptions.ErrorType;

/** 
 * DTO para API respostas padronizadas de erros seguindo o padrão RFC 7807 (Problem Details for HTTP APIs). 
 */
public record ApiErrorDTO(
		ErrorType type,       
        String title,      
        int status,        
        String detail,     
        String instance    
		) {
}
