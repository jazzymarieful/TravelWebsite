package sr.unasat.travelwebsite.travelpackagefactory;

import sr.unasat.travelwebsite.dao.AccountDAO;
import sr.unasat.travelwebsite.dao.TravelGroupDAO;
import sr.unasat.travelwebsite.dao.TravelPackageDAO;
import sr.unasat.travelwebsite.dao.TravelPlanDAO;
import sr.unasat.travelwebsite.entity.Account;
import sr.unasat.travelwebsite.entity.TravelPackage;

public class TourPackageCreator implements TravelPackageCreator {

    private TravelPackage travelPackage;

    private AccountDAO accountDAO = new AccountDAO();
    private TravelPackageDAO travelPackageDAO = new TravelPackageDAO();
    private TravelGroupDAO travelGroupDAO = new TravelGroupDAO();
    private TravelPlanDAO travelPlanDAO = new TravelPlanDAO();

    public TravelPackage addTravelPackageToDatabase(Account credentials) {
        Account account = accountDAO.retrieveAccount(credentials.getUsername(), credentials.getPassword());
        travelPackage = travelPackageDAO.insertTravelPackage(new TravelPackage(travelGroupDAO.findLastTravelGroupRecord(),
                travelPlanDAO.findLastTravelPlanRecord(), account));
        System.out.println("Travel Package has been added to database");
        System.out.println(travelPackage);
        return travelPackage;
    }


}
