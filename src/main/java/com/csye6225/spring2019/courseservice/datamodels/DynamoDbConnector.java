package com.csye6225.spring2019.courseservice.datamodels;

import com.amazonaws.auth.InstanceProfileCredentialsProvider;
//import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

public class DynamoDbConnector {

	 static AmazonDynamoDB dynamoDb ;
 
	 public static void init() {
		if (dynamoDb == null) {
			InstanceProfileCredentialsProvider credentialsProvider = new InstanceProfileCredentialsProvider(false);
			credentialsProvider.getCredentials();
			dynamoDb = AmazonDynamoDBClientBuilder
					.standard()
					.withCredentials(credentialsProvider)
					.withRegion("us-west-2")
					.build();		
			System.out.println("I created the client");
		} 

	}
	 
	 public static AmazonDynamoDB getClient() {
		 return dynamoDb;
	 }
}

