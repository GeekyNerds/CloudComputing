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
import javax.ws.rs.core.MediaType;

import com.csye6225.spring2019.courseservice.datamodels.Announcement;
import com.csye6225.spring2019.courseservice.services.AnnouncementService;

/**
 * Announcement resource (exposed at "announcements" path)
 */
@Path("announcements")
public class AnnouncementResource {
	
	private AnnouncementService announcementService= new AnnouncementService(); 
	
	@POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Announcement addAnnouncement(Announcement announcement) {
    	return announcementService.addAnnouncement(announcement);
    }
	
	@GET
	@Path("/{boardId}_{announcementId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Announcement getAnnouncement(
			@PathParam("boardId") String boardId,
			@PathParam("announcementId") String announcementId) {
		return announcementService.getAnnouncement(boardId, announcementId);
	}
	
	@GET
	@Path("/{boardId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Announcement> getAnnouncements(
			@PathParam("boardId") String boardId) {
		return announcementService.getAnnouncements(boardId);
	}
	
	@DELETE
	@Path("/{boardId}_{announcementId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Announcement deleteAnnouncement(
			@PathParam("boardId") String boardId,
			@PathParam("announcementId") String announcementId) {
		return announcementService.deleteAnnouncement(boardId, announcementId);
	}
	
	@PUT
	@Path("/{boardId}_{announcementId}")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public Announcement updateAnnouncement(
			@PathParam("boardId") String boardId,
			@PathParam("announcementId") String announcementId,
			Announcement announcement) {
		return announcementService.updateAnnouncement(boardId, announcementId, announcement);
	}

}