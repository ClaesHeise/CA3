package rest;

import com.google.gson.*;
import dtos.CalculatorDTO;
import dtos.TopicDTO;
import dtos.UserDTO;
import entities.Calculator;
import entities.CalculatorField;
import entities.User;
import errorhandling.API_Exception;
import facades.UserFacade;
import utils.EMF_Creator;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.HashSet;
import java.util.Set;

@Path("user")
public class UserResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final UserFacade FACADE =  UserFacade.getUserFacade(EMF);

//    private static UserResource getUserFacade(EntityManagerFactory emf) {
//        return null;
//    }

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Context
    SecurityContext securityContext;

//    @GET
//    @Path("all")
//    @Produces({MediaType.APPLICATION_JSON})
//    public Response getAllUsers() throws EntityNotFoundException {
//        return Response.ok().entity(GSON.toJson(FACADE)).build();
//    }

//    @DELETE
//    @Path("/{username}")
//    @Produces({MediaType.APPLICATION_JSON})
//    public Response delete(@PathParam("username") String username) throws EntityNotFoundException {
//        UserDTO deleted = FACADE.deleteUser(username);
//        return Response.ok().entity(GSON.toJson(deleted)).build();
//    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("teacher")
    public Response updatePassword(String jsonString) throws API_Exception {
        UserDTO userDTO;
        try {
            JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();
            String name = securityContext.getUserPrincipal().getName();
            String password = json.get("password").getAsString();
            userDTO = FACADE.updateUserPassword(name, password);
        } catch (Exception e) {
            throw new API_Exception("Malformed JSON Suplied",400,e);
        }
        return Response.ok().entity(GSON.toJson(userDTO)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("teacher")
    public Response addUser(String jsonString) throws API_Exception {
        UserDTO userDTO;
        try {
            JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();
            String name = json.get("name").getAsString();
            String password = json.get("password").getAsString();
            userDTO = new UserDTO(name, password);
            FACADE.createUser(userDTO);
        } catch (Exception e) {
            throw new API_Exception("Malformed JSON Suplied",400,e);
        }
        return Response.ok().entity(GSON.toJson(userDTO)).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("verify")
    @RolesAllowed("teacher")
    public Response getFromTeacher() {
        String username = securityContext.getUserPrincipal().getName();
        UserDTO userDTO = FACADE.findUserFromUsername(username);
        return Response.ok().entity(GSON.toJson(userDTO)).build();
    }
}

