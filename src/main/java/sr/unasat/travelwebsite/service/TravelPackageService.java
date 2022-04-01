package sr.unasat.travelwebsite.service;

import sr.unasat.travelwebsite.dao.AccountDAO;
import sr.unasat.travelwebsite.dao.TravelPackageDAO;
import sr.unasat.travelwebsite.entity.Account;
import sr.unasat.travelwebsite.entity.TravelPackage;
import sr.unasat.travelwebsite.travelpackagefactory.TourPackageCreator;

import java.util.List;

public class TravelPackageService {

    private AccountDAO accountDAO = new AccountDAO();
    private TourPackageCreator tourPackageCreator = new TourPackageCreator();
    private TravelPackageDAO travelPackageDAO = new TravelPackageDAO();

    public boolean verifyAccount(Account account) {
        return accountDAO.verifyAccount(account.getUsername(), account.getPassword());
    }

    public TravelPackage addTravelPackage(Account credentials) {
        return tourPackageCreator.addTravelPackageToDatabase(credentials);
    }

    public List<TravelPackage> getTravelPackages(Account credentials) {
        return travelPackageDAO.retrieveAllTravelPackagesByAccount(credentials);
    }

    public boolean deleteTravelPackage(Long travelPackageId) {
        return travelPackageDAO.deleteTravelPackage(travelPackageId);

    }

}
