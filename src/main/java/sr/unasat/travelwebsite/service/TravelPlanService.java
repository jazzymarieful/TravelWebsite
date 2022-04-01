package sr.unasat.travelwebsite.service;

import sr.unasat.travelwebsite.dao.DestinationDAO;
import sr.unasat.travelwebsite.entity.Destination;
import sr.unasat.travelwebsite.reportresultset.TravelPlanData;
import sr.unasat.travelwebsite.travelpackagefactory.TourPlanCreator;

import java.util.List;

public class TravelPlanService {

    private DestinationDAO destinationDAO = new DestinationDAO();
    private TourPlanCreator tourPlanCreator = new TourPlanCreator();

    public List<Destination> getDestinations() {
        return destinationDAO.retrieveDestinationList();
    }

    public boolean addTravelPlan(TravelPlanData travelPlanData) {
        tourPlanCreator.addTravelPlanToDatabase(travelPlanData);
        return true;
    }

    public boolean updateDestination(Destination destination) {
        return destinationDAO.updateDestination(destination);
    }

    public boolean deleteDestination(Long destinationId) {
        return destinationDAO.deleteDestination(destinationId);
    }

}
