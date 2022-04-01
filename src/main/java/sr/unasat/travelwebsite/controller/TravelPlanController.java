package sr.unasat.travelwebsite.controller;

import sr.unasat.travelwebsite.entity.Destination;
import sr.unasat.travelwebsite.reportresultset.TravelPlanData;
import sr.unasat.travelwebsite.service.TravelPlanService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/travelPlanController")
public class TravelPlanController {

    private TravelPlanService travelPlanService = new TravelPlanService();

    @Path("/getDestinations")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Destination> getDestinations() {
        return travelPlanService.getDestinations();
    }

    @Path("/addDestinations")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public boolean addTravelPlan(TravelPlanData travelPlanData) {
        return travelPlanService.addTravelPlan(travelPlanData);
    }

    @Path("/putDestination")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public boolean updateTraveler(Destination destination) {
        return travelPlanService.updateDestination(destination);
    }

    @Path("/deleteDestination")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public boolean deleteDestination(Long destinationId) {
        return travelPlanService.deleteDestination(destinationId);
    }

}
