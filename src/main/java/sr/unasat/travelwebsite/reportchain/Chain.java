package sr.unasat.travelwebsite.reportchain;

import sr.unasat.travelwebsite.reportresultset.ReportResult;

import java.util.List;

public interface Chain {

    void setNextChain(Chain nextChain);

    List<? extends ReportResult> getReport(ReportRequest request);
}
