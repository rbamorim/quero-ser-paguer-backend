package com.pag.backend.apirest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pag.backend.apirest.dto.CustomerDto;
import com.pag.backend.apirest.model.CustomerModel;
import com.pag.backend.apirest.repository.CustomerRepository;
import com.pag.backend.apirest.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository repository;

	@Override
	public void save(CustomerDto dto) {
		CustomerModel customerModel = new CustomerModel();
		BeanUtils.copyProperties(dto, customerModel);
		repository.save(customerModel);
		dto.setId(customerModel.getId());
	}

	@Override
	public void update(CustomerDto dto) {
		CustomerModel customerModel = new CustomerModel();
		BeanUtils.copyProperties(dto, customerModel);
		repository.update(customerModel);
	}

	@Override
	public void delete(CustomerDto dto) {
		CustomerModel customerModel = new CustomerModel();
		BeanUtils.copyProperties(dto, customerModel);
		repository.delete(customerModel);
	}

	@Override
	public CustomerDto findById(String id) {
		CustomerModel customerModel = repository.findById(id);

		if (customerModel != null) {
			CustomerDto customerDto = new CustomerDto();
			BeanUtils.copyProperties(customerModel, customerDto);
			return customerDto;
		}

		return null;
	}

	@Override
	public List<CustomerDto> findByAll() {
		List<CustomerModel> customerModels = repository.findAll();

		if (customerModels != null) {
			List<CustomerDto> customerDtos = new ArrayList<CustomerDto>();
			customerModels.stream().forEachOrdered(customerModel -> customerDtos.add(this.convert(customerModel)));
			return customerDtos;
		}

		return null;
	}
	
	private CustomerDto convert(CustomerModel customerModel) {
		CustomerDto customerDto = new CustomerDto();
		BeanUtils.copyProperties(customerModel, customerDto);
		return customerDto;
	}
}
