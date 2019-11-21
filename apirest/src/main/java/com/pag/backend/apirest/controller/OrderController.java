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

import com.pag.backend.apirest.dto.OrderDto;
import com.pag.backend.apirest.service.OrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "orders")
@Api(value = "Api Pedidos", tags = "Pedidos")
@CrossOrigin(origins = "*")
public class OrderController {

	@Autowired
	private OrderService service;

	@PostMapping
	@ApiOperation(value = "Criar pedido")
	public ResponseEntity<OrderDto> save(@RequestBody @Valid OrderDto dto) {
		if (dto.getId() != null)
			throw new RuntimeException("You must not pass an id");

		service.save(dto);

		return ResponseEntity.status(HttpStatus.CREATED).body(dto);
	}
	
	@PutMapping("/{id}")
	@ApiOperation(value = "Atualizar pedido")
	public ResponseEntity<OrderDto> update(@PathVariable(name = "id") String id, @RequestBody @Valid OrderDto dto){
		dto.setId(id);
		
		service.update(dto);
		
		return ResponseEntity.ok(dto);
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Deletar pedido")
	public ResponseEntity<OrderDto> delete(@PathVariable String id) {
		OrderDto orderDto = new OrderDto();
		orderDto.setId(id);
		
		service.delete(orderDto);
		
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Buscar pedido pela chave")
	public ResponseEntity<OrderDto> findById(@PathVariable("id") String id) {
		OrderDto orderDto = service.findById(id);
		return ResponseEntity.ok(orderDto);
	}
	
	@GetMapping()
	@ApiOperation(value = "Buscar todos pedidos")
	public ResponseEntity<List<OrderDto>> findAll() {
		
		List<OrderDto> orderDtos = service.findByAll();
		
		return ResponseEntity.ok(orderDtos);
	}
}