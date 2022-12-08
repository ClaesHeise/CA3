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

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import com.google.gson.*;
import dtos.CalculatorDTO;
import dtos.TopicDTO;
import entities.Calculator;
import entities.CalculatorField;
import errorhandling.API_Exception;
import facades.TopicFacade;
import utils.EMF_Creator;

import java.util.HashSet;
import java.util.Set;

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
    @Path("allCalc")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllCalcs() throws EntityNotFoundException {
        return Response.ok().entity(GSON.toJson(FACADE.getAllCalcs())).build();
    }

    @GET
    @Path("/{name}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getByName(@PathParam("name") String name) throws EntityNotFoundException {
        return Response.ok().entity(GSON.toJson(FACADE.getTopicByName(name))).build();
    }

    @GET
    @Path("allSubjects")
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

    // Get all calculator
    // Add topic
    // Change topic
    // Delete topic

//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response addTopic(String jsonString) throws API_Exception {
//        TopicDTO topicDTO;
//        try {
////            TopicDTO getCalc = FACADE.getTopicByName("Addition"); // ToDo remove this
//            JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();
//            String calcName = json.get("calcName").getAsString();
//            String calculatorURL = json.get("calculatorURL").getAsString();
//            Set<String> tags = new HashSet<>();
//            for(JsonElement s : json.get("tags").getAsJsonArray()){
//                tags.add(s.getAsString());
//            }
//            CalculatorField calcField = new CalculatorField(json.get("keyword").getAsString(),json.get("calcFormula").getAsString(),tags,json.get("isSingleInput").getAsBoolean());
//            Set<CalculatorField> calcFields = new HashSet<>();
//            Calculator calculator = new Calculator(calcName, calculatorURL);
//            calcField.assingCalculator(calculator);
//            calcFields.add(calcField);
//            CalculatorDTO calcDTO = new CalculatorDTO(calculator);
//            topicDTO = new TopicDTO(json.get("name").getAsString(), json.get("description").getAsString(),
//                    json.get("example").getAsString(), json.get("formula").getAsString(), calcDTO);
//            FACADE.createTopic(topicDTO);
//        } catch (Exception e) {
//            throw new API_Exception("Malformed JSON Suplied",400,e);
//        }
//        return Response.ok().entity(GSON.toJson(topicDTO)).build();
//    }

    @POST
    @Path("{subjectName}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("teacher")
    public Response addTopic(@PathParam("subjectName") String subjectName, String jsonString) throws API_Exception {
        TopicDTO topicDTO;
        try {
            topicDTO = GSON.fromJson(jsonString, TopicDTO.class);
            topicDTO = FACADE.createTopic(topicDTO, subjectName);
        } catch (EntityExistsException exception){
            throw new API_Exception("Another topic with same name already exists", 409, exception);
        } catch (Exception e) {
            throw new API_Exception("Malformed JSON Suplied",400,e);
        }
        return Response.ok().entity(GSON.toJson(topicDTO)).build();
    }

    @PUT
    @Path("{subjectName}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("teacher")
    public Response updateTopic(@PathParam("subjectName") String subjectName, String jsonString) throws API_Exception {
        TopicDTO topicDTO;
        try {
            topicDTO = GSON.fromJson(jsonString, TopicDTO.class);
            topicDTO = FACADE.updateTopic(topicDTO, subjectName);
        } catch (Exception e) {
            throw new API_Exception("Malformed JSON Suplied",400,e);
        }
        return Response.ok().entity(GSON.toJson(topicDTO)).build();
    }

    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed("teacher")
    public Response delete(String jsonString) throws API_Exception {
        String name;
        try {
            JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();
            name = json.get("name").getAsString();
            System.out.println(name);
            FACADE.deleteTopic(name);
        } catch (Exception e) {
            throw new API_Exception("Malformed JSON Suplied",400,e);
        }
        return Response.ok().entity(GSON.toJson(name)).build();
    }
}
