package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.CalculatorDTO;
import errorhandling.API_Exception;
import facades.CalculatorFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("calculator")
public class CalculatorResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final CalculatorFacade FACADE = CalculatorFacade.getCalculatorFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCalculatorNames() throws API_Exception {
        List<String> calculatorNames;
        try {
            calculatorNames = FACADE.getAllCalculatorNames();
        } catch (Exception exception) {
            throw new API_Exception("Something went wrong", 500, exception);
        }
        return Response.ok().entity(GSON.toJson(calculatorNames)).build();
    }

    @GET
    @Path("{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCalculatorByName(@PathParam("name") String name) throws API_Exception {
        CalculatorDTO calculatorDTO;
        try {
            calculatorDTO = FACADE.getCalculator(name);
        } catch (Exception exception) {
            throw new API_Exception("Something went wrong", 500, exception);
        }
        return Response.ok().entity(GSON.toJson(calculatorDTO)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCalculator(String jsonString) throws API_Exception {
        CalculatorDTO calculatorDTO;
        try {
            calculatorDTO = GSON.fromJson(jsonString, CalculatorDTO.class);
            calculatorDTO = FACADE.createCalculator(calculatorDTO);
        } catch (Exception exception) {
            throw new API_Exception("Malformed JSON Suplied", 400, exception);
        }
        return Response.ok().entity(GSON.toJson(calculatorDTO)).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCalculator(String jsonString) throws API_Exception {
        CalculatorDTO calculatorDTO;
        try {
            calculatorDTO = GSON.fromJson(jsonString, CalculatorDTO.class);
            calculatorDTO = FACADE.updateCalculator(calculatorDTO);
        } catch (Exception exception) {
            throw new API_Exception("Malformed JSON Suplied", 400, exception);
        }
        return Response.ok().entity(GSON.toJson(calculatorDTO)).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{name}")
    public Response deleteCalculator(@PathParam("name") String name) throws API_Exception {
        CalculatorDTO calculatorDTO;
        try {
            calculatorDTO = FACADE.deleteCalculator(name);
        } catch (Exception exception) {
            throw new API_Exception("Calculator name does not exist", 400, exception);
        }
        return Response.ok().entity(GSON.toJson(calculatorDTO)).build();
    }
}
