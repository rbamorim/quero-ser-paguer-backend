package com.pag.backend.apirest.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class OrderItemDto {

	private String id;
	
//	@NotNull(message = "Id Pedido required")
	private String idOrder;
	
	@NotBlank(message = "Id Produto required")
	private String idProduct;

	@NotNull(message = "Amount required")
	private BigDecimal amount;
	
	@NotNull(message = "Price required")
	private BigDecimal price;
}
