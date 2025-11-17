package com.mlbs.finance_api.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mlbs.finance_api.entities.dto.GastoResponseDTO;
import com.mlbs.finance_api.entities.dto.GastoSaveRequestDTO;
import com.mlbs.finance_api.entities.dto.GastoUpdateRequestDTO;
import com.mlbs.finance_api.services.GastoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/gastos")
public class GastoController {

	@Autowired
	private GastoService service;
	
	@PostMapping
	public ResponseEntity<GastoResponseDTO> save(@RequestBody @Valid GastoSaveRequestDTO request) {
		GastoResponseDTO responseDto = service.save(request);
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(responseDto.id())
				.toUri();
		
		return ResponseEntity.created(location).body(responseDto);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<GastoResponseDTO> get(@PathVariable String id) {
		return ResponseEntity.ok(service.get(id));
	}
	
	@GetMapping 
	public ResponseEntity<Page<GastoResponseDTO>> listAll(
	        @PageableDefault(size = 10, sort = "data", direction = Direction.DESC) Pageable pageable) {
	    return ResponseEntity.ok(service.listAll(pageable));
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<GastoResponseDTO> update(@PathVariable String id, @RequestBody @Valid GastoUpdateRequestDTO request) {
		return ResponseEntity.ok(service.update(id, request));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
	    service.delete(id); 
	    return ResponseEntity.noContent().build();
	}

}
