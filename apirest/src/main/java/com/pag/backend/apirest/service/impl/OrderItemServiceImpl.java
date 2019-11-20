package com.pag.backend.apirest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pag.backend.apirest.dto.OrderItemDto;
import com.pag.backend.apirest.model.OrderItemModel;
import com.pag.backend.apirest.repository.OrderItemRepository;
import com.pag.backend.apirest.service.OrderItemService;

@Service
public class OrderItemServiceImpl implements OrderItemService {

	@Autowired
	private OrderItemRepository repository;

	@Override
	public void save(OrderItemDto dto) {
		OrderItemModel orderItemModel = new OrderItemModel();
		BeanUtils.copyProperties(dto, orderItemModel);
		repository.save(orderItemModel);
		dto.setId(orderItemModel.getId());
	}

	@Override
	public void update(OrderItemDto dto) {
		OrderItemModel orderItemModel = new OrderItemModel();
		BeanUtils.copyProperties(dto, orderItemModel);
		repository.update(orderItemModel);
		dto.setId(orderItemModel.getId());

	}

	@Override
	public void delete(OrderItemDto dto) {
		OrderItemModel orderItemModel = new OrderItemModel();
		BeanUtils.copyProperties(dto, orderItemModel);
		repository.delete(orderItemModel);
	}

	@Override
	public OrderItemDto findById(String id) {
		OrderItemModel orderItemModel = repository.findById(id);

		if (orderItemModel != null) {
			OrderItemDto orderItemDto = new OrderItemDto();
			BeanUtils.copyProperties(orderItemModel, orderItemDto);
			return orderItemDto;
		}

		return null;
	}

	@Override
	public List<OrderItemDto> findByAll() {
		List<OrderItemModel> orderItemModels = repository.findAll();

		if (orderItemModels != null) {
			List<OrderItemDto> orderItemDtos = new ArrayList<OrderItemDto>();
			orderItemModels.stream().forEachOrdered(orderItemModel -> orderItemDtos.add(this.convert(orderItemModel)));
			return orderItemDtos;
		}

		return null;
	}
	
	private OrderItemDto convert(OrderItemModel orderItemModel) {
		OrderItemDto orderItemDto = new OrderItemDto();
		BeanUtils.copyProperties(orderItemModel, orderItemDto);
		return orderItemDto;
	}
}
