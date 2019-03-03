package com.csye6225.spring2019.courseservice.datamodels;

import java.util.HashMap;

public class InMemoryDatabase {
	private static HashMap<Long, Professor> profDB = new HashMap<>();
	private static HashMap<Long, Student> studentDB = new HashMap<>();
	private static HashMap<Long, Program> programDB = new HashMap<>();
	private static HashMap<Long, Course> courseDB = new HashMap<>();
	private static HashMap<Long, Lecture> lectureDB = new HashMap<>();
	
	public static HashMap<Long, Professor> getProfessorDB() {
		return profDB;
	}
	
	public static HashMap<Long, Student> getStudentDB() {
		return studentDB;
	}
	
	public static HashMap<Long, Program> getProgramDB() {
		return programDB;
	}
	
	public static HashMap<Long, Course> getCourseDB() {
		return courseDB;
	}
	
	public static HashMap<Long, Lecture> getLectureDB() {
		return lectureDB;
	}
}