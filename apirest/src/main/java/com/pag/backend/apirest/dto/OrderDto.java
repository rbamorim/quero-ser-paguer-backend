package com.pag.backend.apirest.dto;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class OrderDto {
	
	private String id;
	
	@NotBlank(message = "Id Customer required")
	private String idCustomer;

//	@NotNull(message = "Price required")
	private BigDecimal price;
	
//	@NotNull(message = "Order Item required")
	private List<OrderItemDto> orderItemDtos;
}
