package com.csye6225.spring2019.courseservice.datamodels;

import java.util.HashMap;

public class InMemoryDatabase {
	private static HashMap<Long, Professor> profDB = new HashMap<>();
	
	public static HashMap<Long, Professor> getProfessorDB() {
		return profDB;
	}
}
