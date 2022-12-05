package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dtos.TopicDTO;
import dtos.UserDTO;
import errorhandling.API_Exception;
import facades.UserFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("user")
public class UserResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final UserFacade FACADE =  UserFacade.getUserFacade(EMF);

//    private static UserResource getUserFacade(EntityManagerFactory emf) {
//        return null;
//    }

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

//    @GET
//    @Path("all")
//    @Produces({MediaType.APPLICATION_JSON})
//    public Response getAllUsers() throws EntityNotFoundException {
//        return Response.ok().entity(GSON.toJson(FACADE)).build();
//    }

    @DELETE
    @Path("/{username}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response delete(@PathParam("username") String username) throws EntityNotFoundException {
        UserDTO deleted = FACADE.deleteUser(username);
        return Response.ok().entity(GSON.toJson(deleted)).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePassword(String jsonString) throws API_Exception {
        UserDTO userDTO;
        try {
            JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();
            userDTO = new UserDTO(json.get("name").getAsString(), json.get("password").getAsString());
            FACADE.updateUserPassword(userDTO);
        } catch (Exception e) {
            throw new API_Exception("Malformed JSON Suplied",400,e);
        }
        return Response.ok().entity(GSON.toJson(userDTO)).build();
    }
}
