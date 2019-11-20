package com.pag.backend.apirest.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ProductDto {
	
	private String id;
	
	@NotBlank(message = "Name required")
	@Size(min = 1, max = 100, message = "The name must contain a maximum of 100 characters.")
	private String name;
	
	@NotNull(message = "Price required")
	private BigDecimal price;
}
