package com.pag.backend.apirest.repository;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.pag.backend.apirest.model.OrderItemModel;
import com.pag.backend.apirest.model.OrderModel;

@Repository
public class OrderRepository extends BaseRepository<OrderModel> {

	@Autowired
	private OrderItemRepository orderItemRepository;
	
	public void save(OrderModel orderModel, List<OrderItemModel> orderItemModels)
	{
        this.save(orderModel);
        
        orderItemModels.forEach(orderItemModel -> orderItemModel.setIdOrder(orderModel.getId()));
        orderItemModels.forEach(orderItemModel -> orderItemRepository.save(orderItemModel));
	}
	
	@Override
	protected Class<OrderModel> keyObject() {
		return OrderModel.class;
	}
	
	@Override
	protected DynamoDBSaveExpression buildDynamoDBSaveExpression(OrderModel model) {
		DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression();
		HashMap<String, ExpectedAttributeValue> expected = new HashMap<String, ExpectedAttributeValue>();
		
		expected.put("id", new ExpectedAttributeValue(new AttributeValue(model.getId()))
				.withComparisonOperator(ComparisonOperator.EQ));
		saveExpression.setExpected(expected);
		
		return saveExpression;
	}
}
