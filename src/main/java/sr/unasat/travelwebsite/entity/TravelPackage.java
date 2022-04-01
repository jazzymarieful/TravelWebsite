package sr.unasat.travelwebsite.entity;

import javax.persistence.*;

@Entity
@Table(name = "travel_package")
public class TravelPackage {

    @Id
    @Column(name = "travel_package_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long travelPackageId;

    @OneToOne (cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "travel_group_id", nullable = false, unique = true)
    private TravelGroup travelGroup;

    @OneToOne (cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "travel_plan_id", nullable = false, unique = true)
    private TravelPlan travelPlan;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = true)
    private Account account;

    public TravelPackage() {
    }

    public TravelPackage(TravelGroup travelGroup, TravelPlan travelPlan, Account account) {
        this.travelGroup = travelGroup;
        this.travelPlan = travelPlan;
        this.account = account;
    }

    public Long getTravelPackageId() {
        return travelPackageId;
    }

    public void setTravelPackageId(Long travelPackageId) {
        this.travelPackageId = travelPackageId;
    }

    public TravelGroup getTravelGroup() {
        return travelGroup;
    }

    public void setTravelGroup(TravelGroup travelGroup) {
        this.travelGroup = travelGroup;
    }

    public TravelPlan getTravelPlan() {
        return travelPlan;
    }

    public void setTravelPlan(TravelPlan travelPlan) {
        this.travelPlan = travelPlan;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "TravelPackage{" +
                "travelPackageId=" + travelPackageId +
                ", travelGroup=" + travelGroup +
                ", travelPlan=" + travelPlan +
//                ", account=" + account +
                '}' + '\n';
    }
}
