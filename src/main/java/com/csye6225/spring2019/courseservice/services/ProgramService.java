package com.csye6225.spring2019.courseservice.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.csye6225.spring2019.courseservice.datamodels.InMemoryDatabase;
import com.csye6225.spring2019.courseservice.datamodels.Program;

public class ProgramService {
	private HashMap<Long, Program> programMap = InMemoryDatabase.getProgramDB();
	
	public Program addProgram(Program program) {
		long nextAvailableId = programMap.size() + 1;
		program.setProgramId(nextAvailableId);
		programMap.put(nextAvailableId, program);
		return programMap.get(nextAvailableId);
	}
	
	public Program getProgram(long programId) {
		return programMap.get(programId);
	}
	
	public List<Program> getAllPrograms() {
		return new ArrayList<Program>(programMap.values());
	}
	
	public Program deleteProgram(long programId) {
		return programMap.remove(programId);
	}
	
	public Program updateProgram(long programId, Program program) {
		Program programToBeUpdated = programMap.get(programId);
		String programName = program.getProgramName();
		String department = program.getDepartment();
		
		if(programName != null && !programName.isEmpty()) {
			programToBeUpdated.setProgramName(programName);
		}
		if(department != null && !department.isEmpty()) {
			programToBeUpdated.setDepartment(department);
		}
		
		return programToBeUpdated;
	}
	
	public List<Program> getProgramsByOptions(String department, String size) {
		List<Program> programList = getAllPrograms();
		if(department != null && !department.isEmpty()) {
			programList = getProgramsByDepartment(programList, department);
		}
		if(size != null && !size.isEmpty()) {
			programList = getProgramsBySize(programList, Integer.parseInt(size));
		}
		
		return programList;
	}
	
	public List<Program> getProgramsByDepartment(List<Program> programList, String department) {
		for(Program program : programList) {
			if(!program.getDepartment().equals(department)){
				programList.remove(program);
			}
		}
		
		return programList;
	}
	
	public List<Program> getProgramsBySize(List<Program> programList, int size) {
		int maxIndex = size < programList.size() ? size: programList.size();  
		return programList.subList(0, maxIndex);
	}
}
