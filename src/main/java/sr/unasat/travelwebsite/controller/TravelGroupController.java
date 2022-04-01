package sr.unasat.travelwebsite.controller;

import sr.unasat.travelwebsite.entity.Traveler;
import sr.unasat.travelwebsite.service.TravelGroupService;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("controller")
public class TravelGroupController {

    private TravelGroupService travelGroupService = new TravelGroupService();

    @Path("/addTravelers")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public boolean addTravelers(List<Traveler> travelerList) {
        return travelGroupService.addTravelers(travelerList);
    }

    @Path("/getTravelers")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Traveler> getTravelers() {
        return travelGroupService.getTravelers();
    }

    @Path("/putTraveler")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public boolean updateTraveler(Traveler traveler) {
        return travelGroupService.updateTraveler(traveler);
    }

    @Path("/deleteTraveler")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public boolean deleteTraveler(String passport) {
        return travelGroupService.deleteTraveler(passport);
    }
}
