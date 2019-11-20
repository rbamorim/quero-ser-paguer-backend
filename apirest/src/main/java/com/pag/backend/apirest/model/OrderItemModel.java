package com.pag.backend.apirest.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName = "item_pedido")
public class OrderItemModel implements Serializable {

	private static final long serialVersionUID = -4403840633935190723L;

	@DynamoDBHashKey(attributeName = "id")
	@DynamoDBAutoGeneratedKey
	private String id;
	
	@DynamoDBAttribute(attributeName = "id_pedido")
	private String idOrder;
	
	@DynamoDBAttribute(attributeName = "id_produto")
	private String idProduct;

	@DynamoDBAttribute(attributeName = "quantidade")
	private BigDecimal amount;
	
	@DynamoDBAttribute(attributeName = "valor")
	private BigDecimal price;
}
