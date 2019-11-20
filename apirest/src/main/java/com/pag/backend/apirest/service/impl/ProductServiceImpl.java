package com.pag.backend.apirest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pag.backend.apirest.dto.ProductDto;
import com.pag.backend.apirest.model.ProductModel;
import com.pag.backend.apirest.repository.ProductRepository;
import com.pag.backend.apirest.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductRepository repository;
	
	@Override
	public void save(ProductDto dto) {
		ProductModel productModel = new ProductModel();
		BeanUtils.copyProperties(dto, productModel);
		repository.save(productModel);
		dto.setId(productModel.getId());
	}

	@Override
	public void update(ProductDto dto) {
		ProductModel productModel = new ProductModel();
		BeanUtils.copyProperties(dto, productModel);
		repository.update(productModel);
		dto.setId(productModel.getId());
	}

	@Override
	public void delete(ProductDto dto) {
		ProductModel productModel = new ProductModel();
		BeanUtils.copyProperties(dto, productModel);
		repository.delete(productModel);
	}

	@Override
	public ProductDto findById(String id) {
		ProductModel productModel = repository.findById(id);

		if (productModel != null) {
			ProductDto productDto = new ProductDto();
			BeanUtils.copyProperties(productModel, productDto);
			return productDto;
		}

		return null;
	}

	@Override
	public List<ProductDto> findByAll() {
		List<ProductModel> productModels = repository.findAll();

		if (productModels != null) {
			List<ProductDto> productDtos = new ArrayList<ProductDto>();
			productModels.stream().forEachOrdered(productModel -> productDtos.add(this.convert(productModel)));
			return productDtos;
		}

		return null;
	}
	
	private ProductDto convert(ProductModel productModel) {
		ProductDto productDto = new ProductDto();
		BeanUtils.copyProperties(productModel, productDto);
		return productDto;
	}
}
