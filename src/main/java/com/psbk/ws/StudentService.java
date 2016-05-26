package com.psbk.ws;

import java.sql.Connection;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.psbk.ws.common.JdbcTemplate;
import com.psbk.ws.common.MasterConnection;
import com.psbk.ws.common.MyMap;

@Path("/student")
public class StudentService extends MasterConnection {

	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public Student getStudent() {
		Student s = new Student();

		s.setId("143040001");
		s.setName("John Doe");
		s.setAddress("Gotham City");

		return s;
	}

	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createStudent(Student student) {
		super.createConnection();
		String respon = student.getAddress();

		Student s = new Student();
		s.setId(student.getId());
		s.setName(student.getName());
		s.setAddress(student.getAddress());

		JdbcTemplate template = new JdbcTemplate(super.conn, super.ds);
		MyMap map = new MyMap();
		map.put("student", s);
		template.insert("student", map);
		super.closeConnection();
		return Response.status(201).entity(respon).build();
	}

}
