package com.pag.backend.apirest.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.pag.backend.apirest.dto.ProductDto;
import com.pag.backend.apirest.model.ProductModel;
import com.pag.backend.apirest.repository.ProductRepository;
import com.pag.backend.apirest.service.impl.ProductServiceImpl;

@RunWith(SpringRunner.class)
public class ProductServiceTest {

	@Autowired
	private ProductService service;

	@MockBean
	private ProductRepository repository;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@TestConfiguration
	static class ProductServiceTestContextConfiguration {

		@Bean
		public ProductService customerService() {
			return new ProductServiceImpl();
		}
	}
	
	@Test
	public void saveSuccessTest() {
		ProductDto productDto = oneProduct();
		productDto.setId(null);
		
		ProductModel productModel = new ProductModel();
		BeanUtils.copyProperties(productDto, productModel);
		
		org.mockito.Mockito.doNothing().when(repository).save(productModel);
		
		service.save(productDto);
		
		verify(repository).save(productModel);
	}
	
	@Test
	public void updateSuccessTest() {
		ProductDto productDto = oneProduct();
		
		ProductModel productModel = new ProductModel();
		BeanUtils.copyProperties(productDto, productModel);
		
		org.mockito.Mockito.doNothing().when(repository).update(productModel);
		
		service.update(productDto);
		
		verify(repository).update(productModel);
	}
	
	@Test
	public void deleteSuccessTest() {
		ProductDto productDto = oneProduct();
		
		ProductModel productModel = new ProductModel();
		BeanUtils.copyProperties(productDto, productModel);
		
		org.mockito.Mockito.doNothing().when(repository).delete(productModel);
		
		service.delete(productDto);
		
		verify(repository).delete(productModel);
	}
	
	@Test
	public void findByIdSuccessTest() {
		ProductDto productDto = oneProduct();
		ProductModel productModel = new ProductModel();
		BeanUtils.copyProperties(productDto, productModel);
		
		when(repository.findById(productDto.getId())).thenReturn(productModel);
		
		ProductDto test = service.findById(productDto.getId());
		
		verify(repository).findById(productDto.getId());
		assertEquals(productDto, test);
	}
	
	@Test
	public void findByIdNotFoundTest() {
		String id = "10000";
		
		ProductDto productDto = service.findById(id);
		
		verify(repository).findById(id);
		assertNull(productDto);
	}
	
	private ProductDto oneProduct() {
		ProductDto productDto = new ProductDto();
		productDto.setId("1");
		productDto.setName("Teste 1");
		productDto.setPrice(new BigDecimal(100));
		return productDto;
	}
}
