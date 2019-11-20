package com.pag.backend.apirest.dto;

import java.util.Date;

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
public class CustomerDto {

	private String id;
	
	@NotBlank(message = "Name required")
	@Size(max = 100, message = "The name must contain a maximum of 100 characters.")
	private String name;

	@NotBlank(message = "CPF required")
	@Size(min = 11, max = 11, message = "The CPF must contain a maximum of 11 characters.")
	private String cpf;
	
	@NotNull(message = "Birth date required")
	private Date birthDate;
}
