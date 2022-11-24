package rest;

//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import dtos.TopicDTO;
//import facades.TopicFacade;
//import utils.EMF_Creator;
//
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.EntityNotFoundException;
//import javax.print.attribute.standard.MediaTray;
//import javax.ws.rs.GET;
//import javax.ws.rs.Path;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//import java.awt.*;
//import java.util.ArrayList;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facades.TopicFacade;
import utils.EMF_Creator;

@Path("topic")
public class TopicResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final TopicFacade FACADE =  TopicFacade.getTopicFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Path("all")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllTopics() throws EntityNotFoundException {
        return Response.ok().entity(GSON.toJson(FACADE.getAllTopics())).build();
    }

    @GET
    @Path("/{name}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getByName(@PathParam("name") String name) throws EntityNotFoundException {
        return Response.ok().entity(GSON.toJson(FACADE.getTopicByName(name))).build();
    }
}
