package sr.unasat.travelwebsite.travelpackagefactory;

import sr.unasat.travelwebsite.entity.Traveler;

import java.util.List;

public interface TravelGroupCreator {

    public void addTravelGroupToDatabase(List<Traveler> travelerList);
}
