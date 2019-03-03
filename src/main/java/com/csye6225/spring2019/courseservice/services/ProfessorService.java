package com.csye6225.spring2019.courseservice.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.csye6225.spring2019.courseservice.datamodels.InMemoryDatabase;
import com.csye6225.spring2019.courseservice.datamodels.Professor;

public class ProfessorService {
	
	private HashMap<Long, Professor> profMap = InMemoryDatabase.getProfessorDB();
	
	public Professor addProfessor(Professor prof) {
		long nextAvailableId = profMap.size() + 1;
		prof.setProfessorId(nextAvailableId);
		prof.setJoiningDate(new Date());
		profMap.put(nextAvailableId, prof);
		return profMap.get(nextAvailableId);
	}
	
	public Professor getProfessor(long profId) {
		return profMap.get(profId);
	}
	
	public List<Professor> getAllProfessors() {
		return new ArrayList<Professor>(profMap.values());
	}
	
	public Professor deleteProfessor(long profId) {
		return profMap.remove(profId);
	}
	
	public Professor updateProfessor(long profId, Professor prof) {
		Professor profToBeUpdated = profMap.get(profId);
		String fname = prof.getFirstName();
		String lname = prof.getLastName();
		String department = prof.getDepartment();
		Date joiningDate = prof.getJoiningDate();
		if(fname != null && !fname.isEmpty()) {
			profToBeUpdated.setFirstName(fname);
		}
		if(lname != null && !lname.isEmpty()) {
			profToBeUpdated.setLastName(lname);
		}
		if(department != null && !department.isEmpty()) {
			profToBeUpdated.setDepartment(department);
		}
		if(joiningDate != null && !joiningDate.toString().isEmpty()) {
			profToBeUpdated.setJoiningDate(joiningDate);
		}
		
		return profToBeUpdated;
	}
	
	public List<Professor> getProfessorsByOptions(String department, String year, String size) {
		List<Professor> profList = getAllProfessors();
		if(department != null && !department.isEmpty()) {
			profList = getProfessorsByDepartment(profList, department);
		}
		if(year != null && !year.isEmpty()) {
			profList = getProfessorsByYear(profList, Integer.parseInt(year));
		}
		if(size != null && !size.isEmpty()) {
			profList = getProfessorsBySize(profList, Integer.parseInt(size));
		}
		
		return profList;
	}
	
	public List<Professor> getProfessorsByDepartment(List<Professor> profList, String department) {
		for(Professor prof : profMap.values()) {
			if(!prof.getDepartment().equals(department)){
				profList.remove(prof);
			}
		}
		
		return profList;
	}
	
	public List<Professor> getProfessorsByYear(List<Professor> profList, int year) {
		for(Professor prof : profMap.values()) {
			if(prof.getJoiningDate().getYear() != (year - 1900)){
				profList.remove(prof);
			}
		}
		
		return profList;
	}
	
	public List<Professor> getProfessorsBySize(List<Professor> profList, int size) {
		int maxIndex = size < profList.size() ? size: profList.size();  
		return profList.subList(0, maxIndex);
	}

}