package com.locateyourfriend.restServer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/message")
public class Test {
	
	@GET
	public String getMsg()
	{
		return "Hello World !! - Jersey 2. ";
	}
}
