package com.csye6225.spring2019.courseservice.datamodels;

public class Program {
	private long programId;
	private String programName;
    private String department;

    public Program() {
   
    }

    public long getProgramId() {
        return programId;
    }

    public void setProgramId(long programId) {
        this.programId = programId;
    }
    
    public String getProgramName() {
        return programName;
    }
    
    public void setProgramName(String programName) {
        this.programName = programName;
    }
    
    public String getDepartment() {
    	return department;
    }
    
    public void setDepartment(String department) {
    	this.department = department;
    }

}