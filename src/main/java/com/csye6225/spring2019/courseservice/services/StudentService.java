package com.csye6225.spring2019.courseservice.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.csye6225.spring2019.courseservice.datamodels.Course;
import com.csye6225.spring2019.courseservice.datamodels.DynamoDbConnector;
import com.csye6225.spring2019.courseservice.datamodels.Student;

public class StudentService {
	
	private DynamoDBMapper dynamoDBMapper;
	
	public StudentService() {
		DynamoDbConnector.init();
		dynamoDBMapper = new DynamoDBMapper(DynamoDbConnector.getClient());
	}
	
	
	public Student addStudent(Student student) {
		try {
			dynamoDBMapper.save(student);
			updateCourseRosterAdding(student);
			return student;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Student getStudent(String studentId) {
		try {
			Student student = new Student();
			student.setStudentId(studentId);
			
			DynamoDBQueryExpression<Student> expression = 
					new DynamoDBQueryExpression<Student>()
					.withIndexName("studentId-index")
			        .withConsistentRead(false)
			        .withHashKeyValues(student);
			
			return dynamoDBMapper.query(Student.class, expression).get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Student> getAllStudents() {
		try {
			return dynamoDBMapper.scan(Student.class, 
					new DynamoDBScanExpression());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Student deleteStudent(String studentId) {
		try {
			Student studentToBeDeleted = getStudent(studentId);
			updateCourseRosterOnDeletingStudent(studentToBeDeleted);
			dynamoDBMapper.delete(studentToBeDeleted);
			return studentToBeDeleted;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Student updateStudent(String studentId, Student student) {
		try {
			Student studentToBeUpdated = getStudent(studentId);
			String fname = student.getFirstName();
			String lname = student.getLastName();
			String department = student.getDepartment();
			String joiningDate = student.getJoiningDate();
			Set<String> registeredCourseIds = student.getRegisteredCourses();
			if(fname != null && !fname.isEmpty()) {
				studentToBeUpdated.setFirstName(fname);
			}
			if(lname != null && !lname.isEmpty()) {
				studentToBeUpdated.setLastName(lname);
			}
			if(department != null && !department.isEmpty()) {
				studentToBeUpdated.setDepartment(department);
			}
			if(joiningDate != null && !joiningDate.isEmpty()) {
				studentToBeUpdated.setJoiningDate(joiningDate);
			}
			if(registeredCourseIds != null && !registeredCourseIds.isEmpty()) {
				Set<String> oldRegisteredCourseIds = studentToBeUpdated.getRegisteredCourses();
				studentToBeUpdated.setRegisteredCourses(registeredCourseIds);
				updateCourseRosterAdding(studentToBeUpdated);
				updateCourseRosterRemoving(studentToBeUpdated, oldRegisteredCourseIds);
			}
			
			dynamoDBMapper.save(studentToBeUpdated);
			return studentToBeUpdated;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}	
	}
	
	public List<Student> getStudentsByOptions(
			String department, 
			String year, 
			String courseId, 
			String size) {
		try {
			List<Student> studentList = new ArrayList<Student>(getAllStudents());
			if(department != null && !department.isEmpty()) {
				studentList = getStudentsByDepartment(studentList, department);
			}
			if(year != null && !year.isEmpty()) {
				studentList = getStudentsByYear(studentList, year);
			}
			if(courseId != null && !courseId.isEmpty()) {
				studentList = getStudentsByCourseId(studentList, courseId);
			}
			if(size != null && !size.isEmpty()) {
				studentList = getStudentsBySize(studentList, Integer.parseInt(size));
			}
			
			return studentList;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private List<Student> getStudentsByDepartment(List<Student> studentList, String department) {
		for(Iterator<Student> iter = studentList.iterator(); iter.hasNext(); ) {
			Student student = iter.next();
			if(!student.getDepartment().equals(department)){
				iter.remove();
			}
		}
		
		return studentList;
	}
	
	private List<Student> getStudentsByYear(List<Student> studentList, String year) {
		for(Iterator<Student> iter = studentList.iterator(); iter.hasNext(); ) {
			Student student = iter.next();
			if(!student.getJoiningDate().substring(0, 4).equals(year)){
				iter.remove();
			}
		}
		
		return studentList;
	}
	
	private List<Student> getStudentsByCourseId(List<Student> studentList, String courseId) {
		for(Iterator<Student> iter = studentList.iterator(); iter.hasNext(); ) {
			Student student = iter.next();
			boolean isEnrolled = false;
			for(String registeredCourseId : student.getRegisteredCourses()) {
				if(registeredCourseId.equals(courseId)) {
					isEnrolled = true;
					break;
				}	
			}
			if(!isEnrolled) {
				iter.remove();
			}
		}
		
		return studentList;
	}
	
	private List<Student> getStudentsBySize(List<Student> studentList, int size) {
		int maxIndex = size < studentList.size() ? size: studentList.size();  
		return studentList.subList(0, maxIndex);
	}
	
	private void updateCourseRosterAdding(Student student) {
		try {
			CourseService courseService = new CourseService();
			for(String courseId : student.getRegisteredCourses()) {
				Course courseToBeUpdated = courseService.getCourse(courseId);
				//List<String> rosterToBeUpdated = courseToBeUpdated.getRoster();
				courseToBeUpdated.getRoster().add(student.getStudentId());
				//courseService.updateCourse(courseId, courseToBeUpdated);
				courseService.addCourse(courseToBeUpdated);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void updateCourseRosterRemoving(
			Student student, 
			Set<String> oldRegisteredCourseIds) {
		try {
			CourseService courseService = new CourseService();
			for(String courseId : oldRegisteredCourseIds) {
				if(!student.getRegisteredCourses().contains(courseId)) {
					Course courseToBeUpdated = courseService.getCourse(courseId);
					courseToBeUpdated.getRoster().remove(student.getStudentId());
					courseService.addCourse(courseToBeUpdated);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void updateCourseRosterOnDeletingStudent(Student student) {
		try {
			CourseService courseService = new CourseService();
			for(String courseId : student.getRegisteredCourses()) {
				Course courseToBeUpdated = courseService.getCourse(courseId);
				courseToBeUpdated.getRoster().remove(student.getStudentId());
				courseService.addCourse(courseToBeUpdated);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}