package com.mlbs.finance_api.entities.mappers;

import org.mapstruct.Mapper;

import com.mlbs.finance_api.entities.Gasto;
import com.mlbs.finance_api.entities.dto.GastoSaveRequestDTO;
import com.mlbs.finance_api.entities.dto.GastoResponseDTO;

@Mapper(componentModel = "spring")
public interface GastoMapper {
	
    Gasto toEntity(GastoSaveRequestDTO dto);
    GastoResponseDTO toDTO(Gasto gasto);
    
}
