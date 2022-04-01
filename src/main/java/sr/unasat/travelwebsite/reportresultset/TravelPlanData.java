package sr.unasat.travelwebsite.reportresultset;

import java.util.ArrayList;
import java.util.List;

public class TravelPlanData {

    private List<String> selectedDestinationList = new ArrayList<>();
    private String selectedRating;
    private String selectedStartDate;
    private String selectedEndDate;
    private int duration;

    public TravelPlanData() {}

    public TravelPlanData(List<String> selectedDestinationList, String selectedRating, String selectedStartDate, String selectedEndDate, int duration) {
        this.selectedDestinationList = selectedDestinationList;
        this.selectedRating = selectedRating;
        this.selectedStartDate = selectedStartDate;
        this.selectedEndDate = selectedEndDate;
        this.duration = duration;
    }

    public List<String> getSelectedDestinationList() {
        return selectedDestinationList;
    }

    public void setSelectedDestinationList(List<String> selectedDestinationList) {
        this.selectedDestinationList = selectedDestinationList;
    }

    public String getSelectedRating() {
        return selectedRating;
    }

    public void setSelectedRating(String selectedRating) {
        this.selectedRating = selectedRating;
    }

    public String getSelectedStartDate() {
        return selectedStartDate;
    }

    public void setSelectedStartDate(String selectedStartDate) {
        this.selectedStartDate = selectedStartDate;
    }

    public String getSelectedEndDate() {
        return selectedEndDate;
    }

    public void setSelectedEndDate(String selectedEndDate) {
        this.selectedEndDate = selectedEndDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "TravelPlanData{" +
                "selectedDestinationList=" + selectedDestinationList +
                ", selectedRating='" + selectedRating + '\'' +
                ", selectedStartDate='" + selectedStartDate + '\'' +
                ", selectedEndDate='" + selectedEndDate + '\'' +
                ", duration=" + duration +
                '}';
    }
}
