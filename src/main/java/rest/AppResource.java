package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facades.AppFacade;
import facades.TopicFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("app")
public class AppResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final AppFacade FACADE =  AppFacade.getAppFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Path("/topic/all")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllTopics() throws EntityNotFoundException {
        return Response.ok().entity(GSON.toJson(FACADE.getAllTopics())).build();
    }

    @GET
    @Path("/topic/{name}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getByName(@PathParam("name") String name) throws EntityNotFoundException {
        return Response.ok().entity(GSON.toJson(FACADE.getTopicByName(name))).build();
    }

    @GET
    @Path("/subject/all")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllSubject() throws EntityNotFoundException {
        return Response.ok().entity(GSON.toJson(FACADE.getSubjects())).build();
    }

    @GET
    @Path("/subject/{name}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getSubjectByName(@PathParam("name") String name) throws EntityNotFoundException {
        return Response.ok().entity(GSON.toJson(FACADE.getSubjects(name))).build();
    }
}
