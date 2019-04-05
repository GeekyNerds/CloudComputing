package com.csye6225.spring2019.courseservice.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.csye6225.spring2019.courseservice.datamodels.Professor;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.csye6225.spring2019.courseservice.datamodels.DynamoDbConnector;


public class ProfessorService {
	
	private DynamoDBMapper dynamoDBMapper;
	
	public ProfessorService() {
		DynamoDbConnector.init();
		dynamoDBMapper = new DynamoDBMapper(DynamoDbConnector.getClient());
	}
	
	public Professor addProfessor(Professor prof) {
		try {
			dynamoDBMapper.save(prof);
			return prof;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Professor getProfessor(String profId) {
		try {
			Professor professor = new Professor();
			professor.setProfessorId(profId);
			
			DynamoDBQueryExpression<Professor> expression = 
					new DynamoDBQueryExpression<Professor>()
					.withIndexName("professorId-index")
			        .withConsistentRead(false)
			        .withHashKeyValues(professor);
			
			return dynamoDBMapper.query(Professor.class, expression).get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Professor> getAllProfessors() {
		try {
			return dynamoDBMapper.scan(Professor.class, 
					new DynamoDBScanExpression());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Professor deleteProfessor(String profId) {
		try {
			Professor profToBeDeleted = getProfessor(profId);
			dynamoDBMapper.delete(profToBeDeleted);
			return profToBeDeleted;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Professor updateProfessor(String profId, Professor prof) {
		try {
			Professor profToBeUpdated = getProfessor(profId);
			String fname = prof.getFirstName();
			String lname = prof.getLastName();
			String department = prof.getDepartment();
			String joiningDate = prof.getJoiningDate();
			if(fname != null && !fname.isEmpty()) {
				profToBeUpdated.setFirstName(fname);
			}
			if(lname != null && !lname.isEmpty()) {
				profToBeUpdated.setLastName(lname);
			}
			if(department != null && !department.isEmpty()) {
				profToBeUpdated.setDepartment(department);
			}
			if(joiningDate != null && !joiningDate.isEmpty()) {
				profToBeUpdated.setJoiningDate(joiningDate);
			}
			
			dynamoDBMapper.save(profToBeUpdated);
			return profToBeUpdated;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Professor> getProfessorsByOptions(String department, String year, String size) {
		try {
			List<Professor> profList = new ArrayList<Professor>(getAllProfessors());
			if(department != null && !department.isEmpty()) {
				profList = getProfessorsByDepartment(profList, department);
			}
			if(year != null && !year.isEmpty()) {
				profList = getProfessorsByYear(profList, year);
			}
			if(size != null && !size.isEmpty()) {
				profList = getProfessorsBySize(profList, Integer.parseInt(size));
			}
			
			return profList;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private List<Professor> getProfessorsByDepartment(List<Professor> profList, String department) {
		for(Iterator<Professor> iter = profList.iterator(); iter.hasNext(); ) {
			Professor prof = iter.next();
			if(!prof.getDepartment().equals(department)){
				iter.remove();
			}
		}
		
		return profList;
	}
	
	private List<Professor> getProfessorsByYear(List<Professor> profList, String year) {
		for(Iterator<Professor> iter = profList.iterator(); iter.hasNext(); ) {
			Professor prof = iter.next();
			if(!prof.getJoiningDate().substring(0, 4).equals(year)){
				iter.remove();
			}
		}
		
		return profList;
	}
	
	private List<Professor> getProfessorsBySize(List<Professor> profList, int size) {
		int maxIndex = size < profList.size() ? size: profList.size();  
		return profList.subList(0, maxIndex);
	}

}