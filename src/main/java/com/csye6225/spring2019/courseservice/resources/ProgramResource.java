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

import com.csye6225.spring2019.courseservice.datamodels.Program;
import com.csye6225.spring2019.courseservice.services.ProgramService;

/**
 * Program resource (exposed at "programs" path)
 */
@Path("programs")
public class ProgramResource {
	private ProgramService programService = new ProgramService();
	
	@POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Program addProgram(Program program) {
    	return programService.addProgram(program);
    }
	
	@GET
	@Path("/{programId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Program getProgram(@PathParam("programId") long programId) {
		return programService.getProgram(programId);
	}
	
	@DELETE
	@Path("/{programId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Program deleteProgram(@PathParam("programId") long programId) {
		return programService.deleteProgram(programId);
	}
	
	@PUT
	@Path("/{programId}")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public Program updateProgram(@PathParam("programId") long programId, Program program) {
		return programService.updateProgram(programId, program);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Program> getProgramsByOptions(
			@QueryParam("department") String department,
			@QueryParam("size") String size) {
		return programService.getProgramsByOptions(department, size);
	}
	
}