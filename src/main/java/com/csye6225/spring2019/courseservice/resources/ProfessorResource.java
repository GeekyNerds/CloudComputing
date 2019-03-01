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

import com.csye6225.spring2019.courseservice.datamodels.Professor;
import com.csye6225.spring2019.courseservice.services.ProfessorService;

/**
 * Professor resource (exposed at "professor" path)
 */
@Path("professors")
public class ProfessorResource {
	private ProfessorService profService = new ProfessorService();
	
	@POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Professor addProfessor(Professor prof) {
    	return profService.addProfessor(prof);
    }
	
	@GET
	@Path("/{profId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Professor getProfessor(@PathParam("profId") long profId) {
		return profService.getProfessor(profId);
	}
	
	@DELETE
	@Path("/{profId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Professor deleteProfessor(@PathParam("profId") long profId) {
		return profService.deleteProfessor(profId);
	}
	
	@PUT
	@Path("/{profId}")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public Professor updateProfessor(@PathParam("profId") long profId, Professor prof) {
		return profService.updateProfessor(profId, prof);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Professor> getProfessorsByOptions(
			@QueryParam("department") String department,
			@QueryParam("year") String year,
			@QueryParam("size") String size) {
		return profService.getProfessorsByOptions(department, year, size);
	}
	
	
}
