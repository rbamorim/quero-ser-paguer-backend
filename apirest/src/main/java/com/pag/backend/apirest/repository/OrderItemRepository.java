package com.pag.backend.apirest.repository;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.amazonaws.util.StringUtils;
import com.pag.backend.apirest.model.OrderItemModel;

@Repository
public class OrderItemRepository extends BaseRepository<OrderItemModel> {

	@Override
	protected Class<OrderItemModel> keyObject() {
		return OrderItemModel.class;
	}

	@Override
	protected DynamoDBSaveExpression buildDynamoDBSaveExpression(OrderItemModel model) {
		DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression();
		HashMap<String, ExpectedAttributeValue> expected = new HashMap<String, ExpectedAttributeValue>();

		expected.put("id", new ExpectedAttributeValue(new AttributeValue(model.getId()))
				.withComparisonOperator(ComparisonOperator.EQ));
		saveExpression.setExpected(expected);

		return saveExpression;
	}

	public List<OrderItemModel> findByIdOrder(String idOrder) {
		OrderItemModel orderItemModem = new OrderItemModel();
		orderItemModem.setIdOrder(idOrder);

		PaginatedScanList<OrderItemModel> scanResult = mapper.scan(keyObject(), buildDynamoDBScanExpression(orderItemModem));
		return scanResult;
	}

	protected DynamoDBScanExpression buildDynamoDBScanExpression(OrderItemModel model) {
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
		
		if (StringUtils.isNullOrEmpty(model.getId())) {
			Condition conditionIdOrder = new Condition()
					.withComparisonOperator(ComparisonOperator.EQ)
					.withAttributeValueList(new AttributeValue().withS(model.getIdOrder()));
					
			scanExpression.addFilterCondition("id_pedido", conditionIdOrder);
		}
				
		return scanExpression;
	}
}
