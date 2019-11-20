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

import com.pag.backend.apirest.dto.OrderItemDto;
import com.pag.backend.apirest.model.OrderItemModel;
import com.pag.backend.apirest.service.OrderItemService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "orderitems")
@Api(value = "Api Itens de Pedidos", tags = "Itens de Pedidos")
@CrossOrigin(origins = "*")
public class OrderItemController {

	@Autowired
	private OrderItemService service;

	@PostMapping
	@ApiOperation(value = "Criar item de pedido")
	public ResponseEntity<OrderItemDto> save(@RequestBody @Valid OrderItemDto orderItemDto) {
		if (orderItemDto.getId() != null)
			throw new RuntimeException("You must not pass an id");

		service.save(orderItemDto);

		return ResponseEntity.status(HttpStatus.CREATED).body(orderItemDto);
	}
	
	@PutMapping("/{id}")
	@ApiOperation(value = "Atualizar item de pedido")
	public ResponseEntity<OrderItemDto> update(@PathVariable(name = "id") String id, @RequestBody @Valid OrderItemDto orderItemDto){
		orderItemDto.setId(id);
		
		service.update(orderItemDto);
		
		return ResponseEntity.ok(orderItemDto);
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Deletar item de pedido")
	public ResponseEntity<OrderItemModel> delete(@PathVariable String id) {
		OrderItemDto orderItemDto = new OrderItemDto();
		orderItemDto.setId(id);
		
		service.delete(orderItemDto);
		
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Buscar item de pedido pela chave")
	public ResponseEntity<OrderItemDto> findById(@PathVariable("id") String id) {
		OrderItemDto orderItemDto = service.findById(id);
		return ResponseEntity.ok(orderItemDto);
	}
	
	@GetMapping()
	@ApiOperation(value = "Buscar todos itens de pedidos")
	public ResponseEntity<List<OrderItemDto>> findAll() {
		
		List<OrderItemDto> orderItemDtos = service.findByAll();
		
		return ResponseEntity.ok(orderItemDtos);
	}
}