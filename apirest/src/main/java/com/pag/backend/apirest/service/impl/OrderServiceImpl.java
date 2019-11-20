package com.pag.backend.apirest.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pag.backend.apirest.dto.OrderDto;
import com.pag.backend.apirest.dto.OrderItemDto;
import com.pag.backend.apirest.model.OrderItemModel;
import com.pag.backend.apirest.model.OrderModel;
import com.pag.backend.apirest.repository.OrderItemRepository;
import com.pag.backend.apirest.repository.OrderRepository;
import com.pag.backend.apirest.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderItemRepository orderItemrepository;

	@Override
	public void save(OrderDto dto) {
		OrderModel orderModel = new OrderModel();
		List<OrderItemModel> orderItemModels = new ArrayList<OrderItemModel>();

		BeanUtils.copyProperties(dto, orderModel);

		BigDecimal sum = dto.getOrderItemDtos().stream().map(OrderItemDto::getPrice).reduce(BigDecimal.ZERO,
				BigDecimal::add);

		orderModel.setPrice(sum);
		dto.getOrderItemDtos().parallelStream()
				.forEachOrdered(orderItemDto -> orderItemModels.add(this.convert(orderItemDto)));

		orderRepository.save(orderModel, orderItemModels);

		dto.setId(orderModel.getId());
		dto.getOrderItemDtos().forEach(orderItemDto -> orderItemDto.setIdOrder(orderModel.getId()));
		orderItemModels.parallelStream().forEachOrdered(orderItemModel -> dto.getOrderItemDtos().get(orderItemModels.indexOf(orderItemModel)).setId(orderItemModel.getId()));
	}

	@Override
	public void update(OrderDto dto) {
		OrderModel orderModel = new OrderModel();
		BeanUtils.copyProperties(dto, orderModel);
		orderRepository.update(orderModel);
		dto.setId(orderModel.getId());

	}

	@Override
	public void delete(OrderDto dto) {
		OrderModel orderModel = new OrderModel();
		BeanUtils.copyProperties(dto, orderModel);
		orderRepository.delete(orderModel);
	}

	@Override
	public OrderDto findById(String id) {
		OrderModel orderModel = orderRepository.findById(id);
		

		if (orderModel != null) {
			OrderDto orderDto = new OrderDto();
			BeanUtils.copyProperties(orderModel, orderDto);
			
			List<OrderItemModel> OrderItemModels = this.orderItemrepository.findAll();
			if (OrderItemModels != null) {
				orderDto.setOrderItemDtos(new ArrayList<OrderItemDto>());
				OrderItemModels.stream().filter(orderItemModel -> orderItemModel.getIdOrder().equals(orderModel.getId())).forEach(orderItemModel -> orderDto.getOrderItemDtos().add(this.convert(orderItemModel)));
			}
			
			return orderDto;
		}

		return null;
	}

	@Override
	public List<OrderDto> findByAll() {
		List<OrderModel> orderModels = orderRepository.findAll();
		
		if (orderModels != null) {
			List<OrderDto> orderDtos = new ArrayList<OrderDto>();
			orderModels.stream().forEachOrdered(orderModel -> orderDtos.add(this.convert(orderModel)));
			
			for (OrderDto orderDto : orderDtos) {
				List<OrderItemModel> OrderItemModels = this.orderItemrepository.findAll();
				if (OrderItemModels != null) {
					orderDto.setOrderItemDtos(new ArrayList<OrderItemDto>());
					OrderItemModels.stream().filter(orderItemModel -> orderItemModel.getIdOrder().equals(orderDto.getId())).forEach(orderItemModel -> orderDto.getOrderItemDtos().add(this.convert(orderItemModel)));
				}
			}

			return orderDtos;
		}

		return null;
	}

	private OrderDto convert(OrderModel orderModel) {
		OrderDto orderDto = new OrderDto();
		BeanUtils.copyProperties(orderModel, orderDto);
		return orderDto;
	}
	
	private OrderItemDto convert(OrderItemModel orderItemModel) {
		OrderItemDto orderItemDto = new OrderItemDto();
		BeanUtils.copyProperties(orderItemModel, orderItemDto);
		return orderItemDto;
	}

	private OrderItemModel convert(OrderItemDto orderItemDto) {
		OrderItemModel orderItemModel = new OrderItemModel();
		BeanUtils.copyProperties(orderItemDto, orderItemModel);
		return orderItemModel;
	}
}