package com.csye6225.spring2019.courseservice.services;

import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.csye6225.spring2019.courseservice.datamodels.Announcement;
import com.csye6225.spring2019.courseservice.datamodels.DynamoDbConnector;

public class AnnouncementService {
	
	private DynamoDBMapper dynamoDBMapper;
	
	public AnnouncementService() {
		DynamoDbConnector.init();
		dynamoDBMapper = new DynamoDBMapper(DynamoDbConnector.getClient());
	}
	
	public Announcement addAnnouncement(Announcement announcement) {
		try {
			dynamoDBMapper.save(announcement);
			return announcement;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Announcement getAnnouncement(String boardId, String announcementId) {
		try {
			Announcement announcement = new Announcement();
			announcement.setBoardId(boardId);
			announcement.setAnnouncementId(announcementId);
			Condition rangeKeyCondition = new Condition()
					.withComparisonOperator(ComparisonOperator.EQ.toString())
					.withAttributeValueList(new AttributeValue().withS(announcementId));
			DynamoDBQueryExpression<Announcement> expression = 
					new DynamoDBQueryExpression<Announcement>()
					.withIndexName("boardId-announcementId-index")
			        .withConsistentRead(false)
			        .withHashKeyValues(announcement)
			        .withRangeKeyCondition("announcementId", rangeKeyCondition);
			
			return dynamoDBMapper.query(Announcement.class, expression).get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Announcement> getAnnouncements(String boardId) {
		try {
			Announcement announcement = new Announcement();
			announcement.setBoardId(boardId);
			DynamoDBQueryExpression<Announcement> expression = 
					new DynamoDBQueryExpression<Announcement>()
					.withIndexName("boardId-announcementId-index")
			        .withConsistentRead(false)
			        .withHashKeyValues(announcement);
			
			return dynamoDBMapper.query(Announcement.class, expression);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Announcement deleteAnnouncement(String boardId, String announcementId) {
		try {
			Announcement announcementToBeDeleted = getAnnouncement(boardId, announcementId);
			dynamoDBMapper.delete(announcementToBeDeleted);
			return announcementToBeDeleted;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Announcement> deleteAnnouncements(String boardId) {
		try {
			List<Announcement> announcementsToBeDeleted = getAnnouncements(boardId);
			dynamoDBMapper.batchDelete(announcementsToBeDeleted);
			return announcementsToBeDeleted;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Announcement updateAnnouncement(
			String boardId, 
			String announcementId, 
			Announcement announcement) {
		
		try {
			Announcement announcementToBeUpdated = getAnnouncement(boardId, announcementId);
			String announcementText = announcement.getAnnouncementText();
			if(announcementText != null && !announcementText.isEmpty()) {
				announcementToBeUpdated.setAnnouncementText(announcementText);
			}
			dynamoDBMapper.save(announcementToBeUpdated);
			return announcementToBeUpdated;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}	
	}

}