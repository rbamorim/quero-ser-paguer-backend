package com.pag.backend.apirest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

/**
 * Configurações do banco de dados DynamoDB
 * 
 * @author RafaelBA
 *
 */
@Configuration
public class DynamoDbConfig {
	@Value("local")
	private String regiao;

	@Value("http://localhost:8000")
	private String endpoint;

	@Value("local")
	private String accessKey;

	@Value("local")
	private String secretKey;

	@Bean
	public DynamoDBMapper mapper() {
		return new DynamoDBMapper(this.config());
	}

	private AmazonDynamoDB config() {
		try {
			AmazonDynamoDB db = AmazonDynamoDBClientBuilder.standard()
					.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(this.endpoint, this.regiao))
					.withCredentials(
							new AWSStaticCredentialsProvider(new BasicAWSCredentials(this.accessKey, this.secretKey)))
					.build();
			return db;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}
}
