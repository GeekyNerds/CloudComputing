package com.csye6225.spring2019.courseservice.datamodels;

public class Course {
	private long courseId;
	private String courseName; 
	private String programName;
    private String board;
    private long professorId;
    private long taStudentId;

    public Course() {

    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }
    
    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    
    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }
    
    public long getProfessorId() {
        return professorId;
    }

    public void setProfessorId(long professorId) {
        this.professorId = professorId;
    }

    public long getTaStudentId() {
        return taStudentId;
    }

    public void setTaStudentId(long taStudentId) {
        this.taStudentId = taStudentId;
    }
    
}