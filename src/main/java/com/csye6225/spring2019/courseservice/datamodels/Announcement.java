package com.csye6225.spring2019.courseservice.datamodels;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="announcement")
public class Announcement {
	private String id;
	private String announcementId;
	private String announcementText;
	private String boardId;
	
	public Announcement() {
		
	}
	
	@DynamoDBHashKey(attributeName = "id")
	@DynamoDBAutoGeneratedKey
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	@DynamoDBIndexRangeKey(attributeName = "announcementId", globalSecondaryIndexName = "boardId-announcementId-index")
	public String getAnnouncementId() {
		return announcementId;
	}
	
	public void setAnnouncementId(String announcementId) {
		this.announcementId = announcementId;
	}
	
	@DynamoDBAttribute(attributeName = "announcementText")
	public String getAnnouncementText() {
		return announcementText;
	}
	
	public void setAnnouncementText(String announcementText) {
		this.announcementText = announcementText;
	}
	
	@DynamoDBIndexHashKey(attributeName = "boardId", globalSecondaryIndexName = "boardId-announcementId-index")
	public String getBoardId() {
		return boardId;
	}
	
	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}
}