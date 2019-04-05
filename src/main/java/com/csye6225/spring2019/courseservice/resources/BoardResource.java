package com.csye6225.spring2019.courseservice.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.csye6225.spring2019.courseservice.datamodels.Board;
import com.csye6225.spring2019.courseservice.services.BoardService;

/**
 * Board resource (exposed at "boards" path)
 */
@Path("boards")
public class BoardResource {
	
	private BoardService boardService = new BoardService();
	
	@POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Board addBoard(Board board) {
    	return boardService.addBoard(board);
    }
	
	@GET
	@Path("/{boardId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Board getBoard(@PathParam("boardId") String boardId) {
		return boardService.getBoard(boardId);
	}
	
	@DELETE
	@Path("/{boardId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Board deleteBoard(@PathParam("boardId") String boardId) {
		return boardService.deleteBoard(boardId);
	}
	
}