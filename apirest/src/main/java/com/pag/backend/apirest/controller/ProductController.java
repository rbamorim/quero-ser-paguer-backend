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

import com.pag.backend.apirest.dto.ProductDto;
import com.pag.backend.apirest.service.ProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "products")
@Api(value = "Api Produtos", tags = "Produtos")
@CrossOrigin(origins = "*")
public class ProductController {

	@Autowired
	ProductService service;

	@PostMapping
	@ApiOperation(value = "Criar produto")
	public ResponseEntity<ProductDto> save(@RequestBody @Valid ProductDto productDto) {
		if (productDto.getId() != null)
			throw new RuntimeException("You must not pass an id");

		service.save(productDto);

		return ResponseEntity.status(HttpStatus.CREATED).body(productDto);
	}
	
	@PutMapping("/{id}")
	@ApiOperation(value = "Alterar produto")
	public ResponseEntity<ProductDto> update(@PathVariable(name = "id") String id, @RequestBody @Valid ProductDto productDto){
		productDto.setId(id);
		
		service.update(productDto);
		
		return ResponseEntity.ok(productDto);
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Deletar produto")
	public ResponseEntity<ProductDto> delete(@PathVariable String id) {
		ProductDto productDto = new ProductDto();
		productDto.setId(id);
		
		service.delete(productDto);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Buscar produto pela chave")
	public ResponseEntity<ProductDto> findById(@PathVariable("id") String id) {
		ProductDto productDto = service.findById(id);
		return ResponseEntity.ok(productDto);
	}
	
	@GetMapping()
	@ApiOperation(value = "Buscar todos produtos")
	public ResponseEntity<List<ProductDto>> findAll() {
		
		List<ProductDto> productDtos = service.findByAll();
		
		return ResponseEntity.ok(productDtos);
	}
}
