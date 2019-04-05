package com.csye6225.spring2019.courseservice.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.csye6225.spring2019.courseservice.datamodels.Student;
import com.csye6225.spring2019.courseservice.services.StudentService;

/**
 * Student resource (exposed at "students" path)
 */
@Path("students")
public class StudentResource {
	private StudentService studentService = new StudentService();
	
	@POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Student addStudent(Student student) {
    	return studentService.addStudent(student);
    }
	
	@GET
	@Path("/{studentId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Student getStudent(@PathParam("studentId") String studentId) {
		return studentService.getStudent(studentId);
	}
	
	@DELETE
	@Path("/{studentId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Student deleteStudent(@PathParam("studentId") String studentId) {
		return studentService.deleteStudent(studentId);
	}
	
	@PUT
	@Path("/{studentId}")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public Student updateStudent(@PathParam("studentId") String studentId, Student student) {
		return studentService.updateStudent(studentId, student);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Student> getStudentsByOptions(
			@QueryParam("department") String department,
			@QueryParam("year") String year,
			@QueryParam("courseId") String courseId,
			@QueryParam("size") String size) {
		return studentService.getStudentsByOptions(department, year, courseId, size);
	}
	
}