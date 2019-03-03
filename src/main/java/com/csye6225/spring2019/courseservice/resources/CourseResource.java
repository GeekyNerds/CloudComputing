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

import com.csye6225.spring2019.courseservice.datamodels.Course;
import com.csye6225.spring2019.courseservice.services.CourseService;

/**
 * Course resource (exposed at "courses" path)
 */
@Path("courses")
public class CourseResource {
	private CourseService courseService = new CourseService();
	
	@POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Course addCourse(Course course) {
    	return courseService.addCourse(course);
    }
	
	@GET
	@Path("/{courseId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Course getCourse(@PathParam("courseId") long courseId) {
		return courseService.getCourse(courseId);
	}
	
	@DELETE
	@Path("/{courseId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Course deleteCourse(@PathParam("courseId") long courseId) {
		return courseService.deleteCourse(courseId);
	}
	
	@PUT
	@Path("/{courseId}")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public Course updateCourse(@PathParam("courseId") long courseId, Course course) {
		return courseService.updateCourse(courseId, course);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Course> getCoursesByOptions(
			@QueryParam("programName") String programName,
			@QueryParam("professorId") String professorId,
			@QueryParam("taStudentId") String taStudentId) {
		return courseService.getCoursesByOptions(programName, professorId, taStudentId);
	}
	
}