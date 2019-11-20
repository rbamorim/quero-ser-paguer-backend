package com.pag.backend.apirest.controller;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "lambda")
@Api(value = "Api Lambda", tags = "Desafio Lambda")
@CrossOrigin(origins = "*")
public class LambdaContoller {

	@GetMapping()
	@ApiOperation(value = "Desafio lambda - Retorna Quantidade, Soma dos números pares, média, maior valor, exemplo de envio: \"values\": [1, 2, 3, 4, 5, 6, 7, 8, 9]")
	public ResponseEntity<String> lambda(@RequestBody Map<String, Object> valuesMap) {

		if (valuesMap == null || valuesMap.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		@SuppressWarnings("unchecked")
		List<Integer> values = (List<Integer>) valuesMap.get("values");

		IntSummaryStatistics intSummaryStatistics = values.stream().mapToInt(value -> (value % 2 == 0) ? value : 0)
				.summaryStatistics();

		return ResponseEntity.status(HttpStatus.CREATED).body(intSummaryStatistics.toString());
	}
}
