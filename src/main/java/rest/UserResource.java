package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.UserDTO;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("user")
public class UserResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final UserResource FACADE =  UserResource.getUserFacade(EMF);

    private static UserResource getUserFacade(EntityManagerFactory emf) {
        return null;
    }

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Path("all")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllUsers() throws EntityNotFoundException {
        return Response.ok().entity(GSON.toJson(FACADE.getAllUsers())).build();
    }

    @DELETE
    @Path("/{username}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response delete(@PathParam("username") String username) throws EntityNotFoundException {
        Response deleted = FACADE.delete(username);
        return Response.ok().entity(GSON.toJson(deleted)).build();
    }
    // int id måske skal den være bav ved (String password)
    @PUT
    @Path("/{update_password}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response update_password(@PathParam("update_password") String password) throws EntityNotFoundException {
        UserDTO t = GSON.fromJson(password, UserDTO.class);
        t.setUsername(password); //Should be implemented
        Response updated = FACADE.update_password(String.valueOf(t));
        return Response.ok().entity(GSON.toJson(updated)).build();
    }
}
