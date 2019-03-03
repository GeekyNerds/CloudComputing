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

import com.csye6225.spring2019.courseservice.datamodels.Lecture;
import com.csye6225.spring2019.courseservice.services.LectureService;

/**
 * Lecture resource (exposed at "lectures" path)
 */
@Path("lectures")
public class LectureResource {
	private LectureService lectureService = new LectureService();
	
	@POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Lecture addLecture(Lecture Lecture) {
    	return lectureService.addLecture(Lecture);
    }
	
	@GET
	@Path("/{lectureId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Lecture getLecture(@PathParam("lectureId") long lectureId) {
		return lectureService.getLecture(lectureId);
	}
	
	@DELETE
	@Path("/{lectureId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Lecture deleteLecture(@PathParam("lectureId") long lectureId) {
		return lectureService.deleteLecture(lectureId);
	}
	
	@PUT
	@Path("/{lectureId}")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public Lecture updateLecture(@PathParam("lectureId") long lectureId, Lecture Lecture) {
		return lectureService.updateLecture(lectureId, Lecture);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Lecture> getLecturesByOptions(@QueryParam("courseId") String courseId) {
		return lectureService.getLecturesByOptions(courseId);
	}
	
}