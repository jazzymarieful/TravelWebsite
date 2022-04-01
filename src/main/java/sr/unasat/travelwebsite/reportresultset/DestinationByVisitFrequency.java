package sr.unasat.travelwebsite.reportresultset;

import sr.unasat.travelwebsite.entity.Destination;

public class DestinationByVisitFrequency extends ReportResult {

    private Destination destination;
    private int year;
    private long frequency;

    public DestinationByVisitFrequency(Destination destination, int year, long frequency) {
        this.destination = destination;
        this.year = year;
        this.frequency = frequency;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public long getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        return "DestinationByVisitFrequency{" +
                "destination=" + destination +
                ", year=" + year +
                ", frequency=" + frequency +
                '}' + "\n";
    }
}
