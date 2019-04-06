package com.csye6225.spring2019.courseservice.services;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.csye6225.spring2019.courseservice.datamodels.Board;
import com.csye6225.spring2019.courseservice.datamodels.DynamoDbConnector;

public class BoardService {
	
	private DynamoDBMapper dynamoDBMapper;
	
	public BoardService() {
		DynamoDbConnector.init();
		dynamoDBMapper = new DynamoDBMapper(DynamoDbConnector.getClient());
	}
	
	public Board addBoard(Board board) {
		try {
			dynamoDBMapper.save(board);
			return board;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Board getBoard(String boardId) {
		try {
			Board board = new Board();
			board.setBoardId(boardId);
			
			DynamoDBQueryExpression<Board> expression = 
					new DynamoDBQueryExpression<Board>()
					.withIndexName("boardId-index")
			        .withConsistentRead(false)
			        .withHashKeyValues(board);
			
			return dynamoDBMapper.query(Board.class, expression).get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Board deleteBoard(String boardId) {
		try {
			Board boardToBeDeleted = getBoard(boardId);
			deleteAnnouncementsAssociated(boardId);
			dynamoDBMapper.delete(boardToBeDeleted);
			return boardToBeDeleted;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private void deleteAnnouncementsAssociated(String boardId) {
		try {
			AnnouncementService announcementService = new AnnouncementService();
			announcementService.deleteAnnouncements(boardId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}