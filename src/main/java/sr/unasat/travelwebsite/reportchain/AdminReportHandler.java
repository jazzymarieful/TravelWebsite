package sr.unasat.travelwebsite.reportchain;

import sr.unasat.travelwebsite.reportresultset.ReportResult;

import java.util.List;

public class AdminReportHandler {

    Chain chain1 = new TravelerReport();
    Chain chain2 = new TravelPeriodReport();
    Chain chain3 = new DestinationReport();

    AdminReport adminReport = new AdminReport();

    public AdminReportHandler() {
        chain1.setNextChain(chain2);
        chain2.setNextChain(chain3);
    }

    public List<? extends ReportResult> startHandling(ReportRequest request) {
        return chain1.getReport(request);
    }

}
