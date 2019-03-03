package com.csye6225.spring2019.courseservice.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.csye6225.spring2019.courseservice.datamodels.Course;
import com.csye6225.spring2019.courseservice.datamodels.InMemoryDatabase;

public class CourseService {
	private static Map<Long, Course> courseMap = InMemoryDatabase.getCourseDB();

    public Course addCourse(Course course) {
        long nextAvailableId = courseMap.size() + 1;
		course.setCourseId(nextAvailableId);
		courseMap.put(nextAvailableId, course);
		return courseMap.get(nextAvailableId);
    }
    
    public Course getCourse(long courseId) {
        return courseMap.get(courseId);
    }
    
    public List<Course> getAllCourses() {
        return new ArrayList<Course>(courseMap.values());
    }
    
    public Course deleteCourse(long courseId) {
        return courseMap.remove(courseId);
    }

    public Course updateCourse(long courseId, Course course) {
    	Course courseToBeUpdated = courseMap.get(courseId);
		String courseName = course.getCourseName();
		String programName = course.getProgramName();
		String board = course.getBoard();
		long professorId = course.getProfessorId();
		long taStudentId = course.getTaStudentId();
		if(courseName != null && !courseName.isEmpty()) {
			courseToBeUpdated.setCourseName(courseName);
		}
		if(programName != null && !programName.isEmpty()) {
			courseToBeUpdated.setProgramName(programName);
		}
		if(board != null && !board.isEmpty()) {
			courseToBeUpdated.setBoard(board);
		}
		if(String.valueOf(professorId) != null && !String.valueOf(professorId).isEmpty()) {
			courseToBeUpdated.setProfessorId(professorId);
		}
		if(String.valueOf(taStudentId) != null && !String.valueOf(taStudentId).isEmpty()) {
			courseToBeUpdated.setTaStudentId(taStudentId);
		}
		
		return courseToBeUpdated;
    }
    
    public List<Course> getCoursesByOptions(String programName, String professorId, String taStudentId) {
    	List<Course> courseList = getAllCourses();
		if(programName != null && !programName.isEmpty()) {
			courseList = getCoursesByProgramName(courseList, programName);
		}
		if(professorId != null && !professorId.isEmpty()) {
			courseList = getCoursesByProfessorId(courseList, professorId);
		}
		if(taStudentId != null && !taStudentId.isEmpty()) {
			courseList = getCoursesByTaStudentId(courseList, taStudentId);
		}
		
		return courseList;  
    }
    
    public List<Course> getCoursesByProgramName(List<Course> courseList, String programName) {
        for(Course course : courseList) {
        	if(!course.getProgramName().equals(programName)) {
        		courseList.remove(course);
        	}
        }
        
        return courseList;
    }
    
    public List<Course> getCoursesByProfessorId(List<Course> courseList, String professorId) {
        for(Course course : courseList) {
        	if(course.getProfessorId() != Long.parseLong(professorId)) {
        		courseList.remove(course);
        	}
        }
        
        return courseList;
    }
    
    public List<Course> getCoursesByTaStudentId(List<Course> courseList, String taStudentId) {
    	for(Course course : courseList) {
        	if(course.getTaStudentId() != Long.parseLong(taStudentId)) {
        		courseList.remove(course);
        	}
        }
    	
        return courseList;
    }

}