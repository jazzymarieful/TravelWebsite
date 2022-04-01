package sr.unasat.travelwebsite.controller;

import sr.unasat.travelwebsite.reportchain.ReportRequest;
import sr.unasat.travelwebsite.reportresultset.ReportResult;
import sr.unasat.travelwebsite.service.TravelReportService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/travelReportController")
public class TravelReportController {

    private TravelReportService travelReportService = new TravelReportService();

    @Path("/getReport")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<? extends ReportResult> getReport(ReportRequest request) {
        System.out.println(travelReportService.getReport(request));
        return travelReportService.getReport(request);
    }

}

