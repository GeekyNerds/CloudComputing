package com.csye6225.spring2019.courseservice.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.csye6225.spring2019.courseservice.datamodels.Course;
import com.csye6225.spring2019.courseservice.datamodels.DynamoDbConnector;
import com.csye6225.spring2019.courseservice.datamodels.Student;

public class CourseService {
	
	private DynamoDBMapper dynamoDBMapper;
	
	public CourseService() {
		DynamoDbConnector.init();
		dynamoDBMapper = new DynamoDBMapper(DynamoDbConnector.getClient());
	}

    public Course addCourse(Course course) {
    	try {
    		dynamoDBMapper.save(course);
        	return course;
        	
    	} catch (Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    	
    }
    
    public Course getCourse(String courseId) {
    	try {
    		Course course = new Course();
        	course.setCourseId(courseId);
        	DynamoDBQueryExpression<Course> expression = 
    				new DynamoDBQueryExpression<Course>()
    				.withIndexName("courseId-index")
    		        .withConsistentRead(false)
    		        .withHashKeyValues(course);
    		
    		return dynamoDBMapper.query(Course.class, expression).get(0);
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    }
    
    public List<Course> getAllCourses() {
    	try {
    		return dynamoDBMapper.scan(Course.class, 
    				new DynamoDBScanExpression());
    	} catch (Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    	
    }
    
    public Course deleteCourse(String courseId) {
    	try {
    		Course courseToBeDeleted = getCourse(courseId);
        	updateStudentRegisteredCourseIdsOnDeletingCourse(courseToBeDeleted);
        	dynamoDBMapper.delete(courseToBeDeleted);
        	return courseToBeDeleted;
        	
    	} catch (Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    }

    public Course updateCourse(String courseId, Course course) {
    	try {
    		Course courseToBeUpdated = getCourse(courseId);
    		String professorId = course.getProfessorId();
    		String taId = course.getTaId();
    		String department = course.getDepartment();
    		String boardId = course.getBoardId();
    		
    		if(professorId != null && !professorId.isEmpty()) {
    			courseToBeUpdated.setProfessorId(professorId);
    		}
    		if(taId != null && !taId.isEmpty()) {
    			courseToBeUpdated.setTaId(taId);
    		}
    		if(department != null && !department.isEmpty()) {
    			courseToBeUpdated.setDepartment(department);
    		}
    		if(boardId != null && !boardId.isEmpty()) {
    			courseToBeUpdated.setBoardId(boardId);
    		}
    		
    		dynamoDBMapper.save(courseToBeUpdated);
    		return courseToBeUpdated;
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    }
    
    public List<Course> getCoursesByOptions(
    		String professoId, 
    		String taId, 
    		String department,
    		String boardId) {
    	try {
    		List<Course> courseList = new ArrayList<Course>(getAllCourses());
    		if(professoId != null && !professoId.isEmpty()) {
    			courseList = getCoursesByProfessoId(courseList, professoId);
    		}
    		if(taId != null && !taId.isEmpty()) {
    			courseList = getCoursesByTaId(courseList, taId);
    		}
    		if(department != null && !department.isEmpty()) {
    			courseList = getCoursesByDepartment(courseList, department);
    		}
    		if(boardId != null && !boardId.isEmpty()) {
    			courseList = getCoursesByBoardId(courseList, boardId);
    		}
    		
    		return courseList; 
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    	 
    }
    
    private List<Course> getCoursesByProfessoId(List<Course> courseList, String professoId) {
        for(Iterator<Course> iter = courseList.iterator(); iter.hasNext(); ) {
        	Course course = iter.next();
        	if(!course.getProfessorId().equals(professoId)) {
        		iter.remove();
        	}
        }
        
        return courseList;
    }
    
    private List<Course> getCoursesByTaId(List<Course> courseList, String taId) {
    	for(Iterator<Course> iter = courseList.iterator(); iter.hasNext(); ) {
        	Course course = iter.next();
        	if(!course.getTaId().equals(taId)) {
        		iter.remove();
        	}
        }
        
        return courseList;
    }
    
    private List<Course> getCoursesByDepartment(List<Course> courseList, String department) {
    	for(Iterator<Course> iter = courseList.iterator(); iter.hasNext(); ) {
        	Course course = iter.next();
        	if(!course.getDepartment().equals(department)) {
        		iter.remove();
        	}
        }
    	
        return courseList;
    }
    
    private List<Course> getCoursesByBoardId(List<Course> courseList, String boardId) {
    	for(Iterator<Course> iter = courseList.iterator(); iter.hasNext(); ) {
        	Course course = iter.next();
        	if(!course.getBoardId().equals(boardId)) {
        		iter.remove();
        	}
        }
    	
        return courseList;
    }
   
    private void updateStudentRegisteredCourseIdsOnDeletingCourse(Course courseToBeDeleted) {
    	try {
    		for(String studentId : courseToBeDeleted.getRoster()) {
        		StudentService studentService = new StudentService();
        		Student studentToBeUpdated = studentService.getStudent(studentId);
        		studentToBeUpdated.getRegisteredCourses().remove(courseToBeDeleted.getCourseId());
        		studentService.addStudent(studentToBeUpdated);
        	}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}	
    }

}