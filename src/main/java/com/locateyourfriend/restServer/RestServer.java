package com.locateyourfriend.restServer;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RestServer{
	
	@Inject
	Logger logger;
	
	@GET
	@Path("/bienvenue")
	public String bienvenue()
	{
		String message = "Connection au serveur établie !";
		logger.log(Level.INFO, message);
		return message;
	}
	
	@GET
	@Path("/bienvenueJSON")
	@Produces(MediaType.APPLICATION_JSON)
	public String bienvenueJSON()
	{
		String message = "Connection au serveur établie !";
		logger.log(Level.INFO, message);
		return message;
	}

}
