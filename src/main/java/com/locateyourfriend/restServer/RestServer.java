package com.locateyourfriend.restServer;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/bienvenue")
public class RestServer{
	
	Logger logger;
	
	
	
	@GET
	public String bienvenue()
	{
		String message = "Connection au serveur �tablie !";
		logger.log(Level.INFO, message);
		return message;
	}
	
	@POST
	@Path("/bienvenueJSON")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON) 
	public String getMessage(@QueryParam("email") String email, @QueryParam("password") String password ){
		String message = "{Connection au serveur �tablie !}";
		logger.log(Level.INFO, message);
		return message;
	}

}
