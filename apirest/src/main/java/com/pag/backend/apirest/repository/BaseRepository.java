package com.pag.backend.apirest.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMappingException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.datamodeling.TransactionWriteRequest;
import com.amazonaws.services.dynamodbv2.model.InternalServerErrorException;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.amazonaws.services.dynamodbv2.model.TransactionCanceledException;

public abstract class BaseRepository<Model> {

	@Autowired
	protected DynamoDBMapper mapper = null;
	
	public void save(Model model)
	{
		mapper.save(model);
	}
	
	public void update(Model model)
	{
		mapper.save(model, this.buildDynamoDBSaveExpression(model));
	}

	public void delete(Model model)
	{
		mapper.delete(model);
	}
	
	public Model findById(String id)
	{
		return mapper.load(keyObject(), id);
	}
	
	public List<Model> findAll()
	{
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
		PaginatedScanList<Model> scanResult = mapper.scan(keyObject(), scanExpression);
		return scanResult;
	}
	
	protected abstract Class<Model> keyObject();
	
	protected abstract DynamoDBSaveExpression buildDynamoDBSaveExpression(Model model);
	
	protected void executeTransactionWrite(TransactionWriteRequest transactionWriteRequest) {
        try {
            mapper.transactionWrite(transactionWriteRequest);
        } catch (DynamoDBMappingException ddbme) {
            System.err.println("Client side error in Mapper, fix before retrying. Error: " + ddbme.getMessage());
        } catch (ResourceNotFoundException rnfe) {
            System.err.println("One of the tables was not found, verify table exists before retrying. Error: " + rnfe.getMessage());
        } catch (InternalServerErrorException ise) {
            System.err.println("Internal Server Error, generally safe to retry with back-off. Error: " + ise.getMessage());
        } catch (TransactionCanceledException tce) {
            System.err.println("Transaction Canceled, implies a client issue, fix before retrying. Error: " + tce.getMessage());
        } catch (Exception ex) {
            System.err.println("An exception occurred, investigate and configure retry strategy. Error: " + ex.getMessage());
        }
    }
}
