package sr.unasat.travelwebsite.service;

import sr.unasat.travelwebsite.dao.TravelGroupDAO;
import sr.unasat.travelwebsite.dao.TravelerDAO;
import sr.unasat.travelwebsite.entity.Traveler;
import sr.unasat.travelwebsite.travelpackagefactory.TourGroupCreator;

import java.util.List;

public class TravelGroupService {

    private TourGroupCreator tourGroupCreator = new TourGroupCreator();

    private TravelerDAO travelerDAO = new TravelerDAO();
    private TravelGroupDAO travelGroupDAO = new TravelGroupDAO();

    public List<Traveler> getTravelers() {
        return travelerDAO.retrieveAllTravelers();
    }

    public boolean addTravelers(List<Traveler> travelerList) {
        tourGroupCreator.addTravelGroupToDatabase(travelerList);
        return true;
    }

    public boolean updateTraveler(Traveler traveler) {
        return travelerDAO.updateTraveler(traveler);
    }

    public boolean deleteTraveler(String passport) {
        return travelerDAO.deleteTraveler(passport);
    }


}
