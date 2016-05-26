package com.rsbt.modules.authenticate;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/auth")
public class AuthenticateService {
	
	@POST
	@Path("/login")
	public Response login(){
		return null;
	}
	
	@POST
	@Path("/logout")
	public Response logout(){
		return null;	
	}
	
	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response register(){
		return null;
	}
	
}
