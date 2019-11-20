package com.pag.backend.apirest.repository;

import java.util.HashMap;

import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.pag.backend.apirest.model.ProductModel;

@Repository
public class ProductRepository extends BaseRepository<ProductModel> {
	
	@Override
	protected Class<ProductModel> keyObject() {
		return ProductModel.class;
	}
	
	@Override
	protected DynamoDBSaveExpression buildDynamoDBSaveExpression(ProductModel model)
	{
		DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression();
		HashMap<String, ExpectedAttributeValue> expected = new HashMap<String, ExpectedAttributeValue>();
		
		expected.put("id", new ExpectedAttributeValue(new AttributeValue(model.getId()))
				.withComparisonOperator(ComparisonOperator.EQ));
		saveExpression.setExpected(expected);
		
		return saveExpression;
	}
}
