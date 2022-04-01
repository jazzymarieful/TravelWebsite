package sr.unasat.travelwebsite.reportresultset;

public class TravelersAgeByPeriod extends ReportResult {

    private String ageRange;
    private int year;
    private long ageAmount;

    public TravelersAgeByPeriod(String ageRange, int year, long ageAmount) {
        this.ageRange = ageRange;
        this.year = year;
        this.ageAmount = ageAmount;
    }

    public String getAgeRange() {
        return ageRange;
    }

    public void setAgeRange(String ageRange) {
        this.ageRange = ageRange;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public long getAgeAmount() {
        return ageAmount;
    }

    public void setAgeAmount(long ageAmount) {
        this.ageAmount = ageAmount;
    }

    @Override
    public String toString() {
        return "TravelersAgeByPeriod{" +
                "ageRange='" + ageRange + '\'' +
                ", year=" + year +
                ", ageAmount=" + ageAmount +
                '}';
    }
}
