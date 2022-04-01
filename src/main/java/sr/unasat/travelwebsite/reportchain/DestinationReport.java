package sr.unasat.travelwebsite.reportchain;

import sr.unasat.travelwebsite.dao.TravelPackageDAO;
import sr.unasat.travelwebsite.reportresultset.ReportResult;

import java.util.List;

public class DestinationReport implements Chain {

    private Chain nextInChain;
    private TravelPackageDAO travelPackageDAO = new TravelPackageDAO();

    @Override
    public void setNextChain(Chain nextChain) {
        this.nextInChain = nextChain;
    }

    @Override
    public List<? extends ReportResult> getReport(ReportRequest request) {
        if (request.getSelectedReport().equals("Destination Report")) {
            if (request.getSelectedPeriod() == 5) {
                return travelPackageDAO.frequencyOfDestinationVisitsByYear(request.getSelectedYear());
            } else if (request.getSelectedPeriod() == 6 || request.getSelectedPeriod() == 7) {
                return travelPackageDAO.frequencyOfDestinationVisitsBySemiYear(request.getSelectedYear(), request.getSelectedPeriod());
            } else {
                return travelPackageDAO.frequencyOfDestinationVisitsByQuarter(request.getSelectedYear(), request.getSelectedPeriod());
            }
        } else {
            System.out.println("Request not handled by any report");
        }
        return null;
    }




}
