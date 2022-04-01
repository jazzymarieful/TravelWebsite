package sr.unasat.travelwebsite.reportresultset;

public class PeriodByVisitFrequency extends ReportResult {

    private int period;
    private long frequency;

    public PeriodByVisitFrequency(int period, long frequency) {
        this.period = period;
        this.frequency = frequency;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public long getFrequency() {
        return frequency;
    }

    public void setFrequency(long frequency) {
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        return "PeriodByVisitFrequency{" +
                "period=" + period +
                ", frequency=" + frequency +
                '}' + "\n";
    }
}
