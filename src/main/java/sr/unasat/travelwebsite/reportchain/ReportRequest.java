package sr.unasat.travelwebsite.reportchain;

public class ReportRequest {

    private String selectedReport;
    private int selectedYear;
    private int selectedPeriod;

    public ReportRequest() {
    }

    public ReportRequest(String selectedReport, int selectedYear, int selectedPeriod) {
        this.selectedReport = selectedReport;
        this.selectedYear = selectedYear;
        this.selectedPeriod = selectedPeriod;
    }

    public String getSelectedReport() {
        return selectedReport;
    }

    public void setSelectedReport(String selectedReport) {
        this.selectedReport = selectedReport;
    }

    public int getSelectedYear() {
        return selectedYear;
    }

    public void setSelectedYear(int selectedYear) {
        this.selectedYear = selectedYear;
    }

    public int getSelectedPeriod() {
        return selectedPeriod;
    }

    public void setSelectedPeriod(int selectedPeriod) {
        this.selectedPeriod = selectedPeriod;
    }

    @Override
    public String toString() {
        return "ReportRequest{" +
                "selectedReport= " + selectedReport +
                ", selectedYear= " + selectedYear +
                ", selectedPeriod= " + selectedPeriod +
                '}';
    }
}
