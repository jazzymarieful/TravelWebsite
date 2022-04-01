package sr.unasat.travelwebsite.controller;

import sr.unasat.travelwebsite.entity.Account;
import sr.unasat.travelwebsite.entity.TravelPackage;
import sr.unasat.travelwebsite.service.TravelPackageService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("travelPackageController")
public class TravelPackageController {

    private TravelPackageService travelPackageService = new TravelPackageService();

    @Path("/verifyAccount")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public boolean verifyAccount(Account account) {
        return travelPackageService.verifyAccount(account);
    }

    @Path("/addTravelPackage")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public TravelPackage addTravelPackage(Account credentials) {
        return travelPackageService.addTravelPackage(credentials);
    }

    @Path("/getTravelPackages")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<TravelPackage> getTravelPackages(Account credentials) {
        return travelPackageService.getTravelPackages(credentials);
    }

    @Path("/deleteTravelPackage")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public boolean deleteTravelPackage(Long travelPackageId) {
        return travelPackageService.deleteTravelPackage(travelPackageId);
    }



}
