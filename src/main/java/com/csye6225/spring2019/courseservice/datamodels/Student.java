package com.csye6225.spring2019.courseservice.datamodels;

//import java.util.HashSet;
import java.util.Set;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="student")
public class Student {
	private String id;
	private String studentId;
	private String firstName;
    private String lastName;
    private String department;
    private String joiningDate;
    private Set<String> registeredCourses;

    public Student() {
        //this.registeredCourses = new HashSet<>();
    }

    @DynamoDBHashKey(attributeName = "id")
	@DynamoDBAutoGeneratedKey
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	@DynamoDBIndexHashKey(attributeName = "studentId", globalSecondaryIndexName = "studentId-index")
	public String getStudentId() {
		return studentId;
	}
		
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	
	@DynamoDBAttribute(attributeName = "firstName")
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@DynamoDBAttribute(attributeName = "lastName")
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@DynamoDBAttribute(attributeName = "department")
	public String getDepartment() {
		return department;
	}
	
	public void setDepartment(String department) {
		this.department = department;
	}
	
	@DynamoDBAttribute(attributeName = "joiningDate")
	public String getJoiningDate() {
		return joiningDate;
	}
	
	public void setJoiningDate(String joiningDate) {
		this.joiningDate = joiningDate;
	}
	
	@DynamoDBAttribute(attributeName = "registeredCourses")
    public Set<String> getRegisteredCourses() {
        return registeredCourses;
    }

    public void setRegisteredCourses(Set<String> registeredCourses) {
        this.registeredCourses = registeredCourses;
    }

}