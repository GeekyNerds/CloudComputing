package com.csye6225.spring2019.courseservice.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.csye6225.spring2019.courseservice.datamodels.InMemoryDatabase;
import com.csye6225.spring2019.courseservice.datamodels.Student;

public class StudentService {
	private HashMap<Long, Student> studentMap = InMemoryDatabase.getStudentDB();
	
	public Student addStudent(Student student) {
		long nextAvailableId = studentMap.size() + 1;
		student.setStudentId(nextAvailableId);
		studentMap.put(nextAvailableId, student);
		return studentMap.get(nextAvailableId);
	}
	
	public Student getStudent(long studentId) {
		return studentMap.get(studentId);
	}
	
	public List<Student> getAllStudents() {
		return new ArrayList<Student>(studentMap.values());
	}
	
	public Student deleteStudent(long studentId) {
		return studentMap.remove(studentId);
	}
	
	public Student updateStudent(long studentId, Student student) {
		Student studentToBeUpdated = studentMap.get(studentId);
		String name = student.getName();
		String imageURL = student.getImageURL();
		String programName = student.getProgramName();
		Set<Long> courseIdsEnrolled = student.getCourseIdsEnrolled();
		if(name != null && !name.isEmpty()) {
			studentToBeUpdated.setName(name);
		}
		if(imageURL != null && !imageURL.isEmpty()) {
			studentToBeUpdated.setImageURL(imageURL);
		}
		if(programName != null && !programName.isEmpty()) {
			studentToBeUpdated.setProgramName(programName);
		}
		if(courseIdsEnrolled != null && !courseIdsEnrolled.isEmpty()) {
			studentToBeUpdated.setCourseIdsEnrolled(courseIdsEnrolled);
		}
		
		return studentToBeUpdated;
	}
	
	public List<Student> getStudentsByOptions(String programName, String courseId, String size) {
		List<Student> studentList = getAllStudents();
		if(programName != null && !programName.isEmpty()) {
			studentList = getStudentsByProgramName(studentList, programName);
		}
		if(courseId != null && !courseId.isEmpty()) {
			studentList = getStudentsByCourseId(studentList, courseId);
		}
		if(size != null && !size.isEmpty()) {
			studentList = getStudentsBySize(studentList, Integer.parseInt(size));
		}
		
		return studentList;
	}
	
	public List<Student> getStudentsByProgramName(List<Student> studentList, String programName) {
		for(Student student : studentList) {
			if(!student.getProgramName().equals(programName)){
				studentList.remove(student);
			}
		}
		
		return studentList;
	}
	
	public List<Student> getStudentsByCourseId(List<Student> studentList, String courseId) {
		for(Student student : studentList) {
			boolean isEnrolled = false;
			for(Long courseIdEnrolled : student.getCourseIdsEnrolled()) {
				if(courseIdEnrolled.toString().equals(courseId)) {
					isEnrolled = true;
					break;
				}	
			}
			if(!isEnrolled) {
				studentList.remove(student);
			}
		}
		
		return studentList;
	}
	
	public List<Student> getStudentsBySize(List<Student> studentList, int size) {
		int maxIndex = size < studentList.size() ? size: studentList.size();  
		return studentList.subList(0, maxIndex);
	}

}