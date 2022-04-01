package sr.unasat.travelwebsite.service;

import sr.unasat.travelwebsite.reportchain.AdminReport;
import sr.unasat.travelwebsite.reportchain.AdminReportHandler;
import sr.unasat.travelwebsite.reportchain.ReportRequest;
import sr.unasat.travelwebsite.reportresultset.ReportResult;

import java.util.List;

public class TravelReportService {

    private AdminReportHandler adminReportHandler = new AdminReportHandler();

    public List<? extends ReportResult> getReport(ReportRequest request) {
        return adminReportHandler.startHandling(request);
    }


}
