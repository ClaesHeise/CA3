package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.TeacherDTO;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("teacher")
public class TeacherResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final TeacherResource FACADE =  TeacherResource.getTeacherFacade(EMF);

    private static TeacherResource getTeacherFacade(EntityManagerFactory emf) {
        return null;
    }

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Path("all")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllTeacher() throws EntityNotFoundException {
        return Response.ok().entity(GSON.toJson(FACADE.getAllTeacher())).build();
    }

    @DELETE
    @Path("/{username}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response delete(@PathParam("username") String username) throws EntityNotFoundException {
        Response deleted = FACADE.delete(username);
        return Response.ok().entity(GSON.toJson(deleted)).build();
    }
    @PUT
    @Path("/{update_password}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response update_password(@PathParam("update_password") String password) throws EntityNotFoundException {
        TeacherDTO t = GSON.fromJson(password, TeacherDTO.class);
        t.setUsername(password); //Should be implemented
        Response updated = FACADE.update_password(String.valueOf(t));
        return Response.ok().entity(GSON.toJson(updated)).build();
    }
}
