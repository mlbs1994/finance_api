package com.mlbs.finance_api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mlbs.finance_api.config.exceptions.BadRequestException;
import com.mlbs.finance_api.config.exceptions.ResourceNotFoundException;
import com.mlbs.finance_api.entities.Gasto;
import com.mlbs.finance_api.entities.dto.GastoResponseDTO;
import com.mlbs.finance_api.entities.dto.GastoSaveRequestDTO;
import com.mlbs.finance_api.entities.dto.GastoUpdateRequestDTO;
import com.mlbs.finance_api.entities.mappers.GastoMapper;
import com.mlbs.finance_api.repositories.GastoRepository;
import com.mlbs.finance_api.utils.UpdateUtil;
import com.mlbs.finance_api.utils.ValidationUtil;

@Service
public class GastoService { 
	
	private final GastoMapper mapper;
    private final GastoRepository repository;

    public GastoService(GastoMapper mapper, GastoRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

	public GastoResponseDTO	get(String id) {
		Gasto gasto = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Gasto de id "+id+" inexistente"));
		
		return mapper.toDTO(gasto); 
	}
	
	public GastoResponseDTO save(GastoSaveRequestDTO dto) {
		Gasto gastoEntidade = mapper.toEntity(dto);
	    Gasto gastoSalvo = repository.save(gastoEntidade);
	    return mapper.toDTO(gastoSalvo);
	}

	public Page<GastoResponseDTO> listAll(Pageable pageable) {
		return repository.findAll(pageable).map(mapper::toDTO);
	}

	public void delete(String id) {
	    if (!repository.existsById(id)) {
	        throw new ResourceNotFoundException("Gasto de id " + id + " inexistente");
	    }
	    repository.deleteById(id);
	}

	public GastoResponseDTO update(String id, GastoUpdateRequestDTO dto) {

		if (ValidationUtil.isDtoEmpty(dto)) {
			throw new BadRequestException("Deve ser informado pelo menos um campo");
		}

		Gasto gasto = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Gasto de id " + id + " inexistente"));
		
		UpdateUtil.copyNonNullProperties(dto, gasto);

	    return mapper.toDTO(repository.save(gasto));
	}

}
