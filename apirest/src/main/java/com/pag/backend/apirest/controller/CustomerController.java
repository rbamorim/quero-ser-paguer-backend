package com.pag.backend.apirest.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pag.backend.apirest.dto.CustomerDto;
import com.pag.backend.apirest.service.CustomerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "customers")
@Api(value = "Api Clientes", tags = "Clientes")
@CrossOrigin(origins = "*")
public class CustomerController {

	@Autowired
	private CustomerService service;

	@PostMapping
	@ApiOperation(value = "Criar cliente")
	public ResponseEntity<CustomerDto> save(@RequestBody @Valid CustomerDto customerDto) {
		if (customerDto.getId() != null)
			throw new RuntimeException("You must not pass an id");

		service.save(customerDto);

		return ResponseEntity.status(HttpStatus.CREATED).body(customerDto);
	}
	
	@PutMapping("/{id}")
	@ApiOperation(value = "Atualizar cliente")
	public ResponseEntity<CustomerDto> update(@PathVariable(name = "id") String id, @RequestBody @Valid CustomerDto customerDto){
		customerDto.setId(id);
		
		service.update(customerDto);
		
		return ResponseEntity.ok(customerDto);
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Deletar cliente")
	public ResponseEntity<CustomerDto> delete(@PathVariable String id) {
		CustomerDto customerDto = new CustomerDto();
		customerDto.setId(id);
		
		service.delete(customerDto);
		
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Buscar cliente pela chave")
	public ResponseEntity<CustomerDto> findById(@PathVariable("id") String id) {
		CustomerDto customerDto = service.findById(id);
		return ResponseEntity.ok(customerDto);
	}
	
	@GetMapping()
	@ApiOperation(value = "Buscar todos clientes")
	public ResponseEntity<List<CustomerDto>> findAll() {
		
		List<CustomerDto> customerDtos = service.findByAll();
		
		return ResponseEntity.ok(customerDtos);
	}
}