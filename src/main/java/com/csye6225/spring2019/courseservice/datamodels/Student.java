package com.csye6225.spring2019.courseservice.datamodels;

import java.util.HashSet;
import java.util.Set;

public class Student {
	private long studentId;
	private String name;
    private String imageURL;
    private String programName;
    private Set<Long> courseIdsEnrolled;

    public Student() {
        this.courseIdsEnrolled = new HashSet<>();
    }
    
    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
    
    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public Set<Long> getCourseIdsEnrolled() {
        return courseIdsEnrolled;
    }

    public void setCourseIdsEnrolled(Set<Long> courseIdsEnrolled) {
        this.courseIdsEnrolled = courseIdsEnrolled;
    }

}