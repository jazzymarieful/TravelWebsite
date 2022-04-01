package sr.unasat.travelwebsite;

import sr.unasat.travelwebsite.dao.TravelPackageDAO;
import sr.unasat.travelwebsite.dao.TravelPlanDAO;
import sr.unasat.travelwebsite.dao.TravelSegmentDAO;
import sr.unasat.travelwebsite.reportchain.AdminReportHandler;
import sr.unasat.travelwebsite.reportchain.ReportRequest;

public class Main {

    public static void main(String[] args) {
        TravelPackageDAO travelPackageDAO = new TravelPackageDAO();
        TravelSegmentDAO travelSegmentDAO = new TravelSegmentDAO();
        TravelPlanDAO travelPlanDAO = new TravelPlanDAO();
        AdminReportHandler adminReportHandler = new AdminReportHandler();

//        travelPackageDAO.frequencyOfDestinationVisitsByYear(2022);
//        travelPackageDAO.frequencyOfTravelersByPeriod(2);
        System.out.println(adminReportHandler.startHandling(new ReportRequest("Traveler Report", 2022, 5)));

    }
}
