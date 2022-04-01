package sr.unasat.travelwebsite.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "travel_group")
public class TravelGroup {

    @Id
    @Column(name = "travel_group_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long travelGroupId;
    @Column(name = "traveler_count", nullable = false)
    private int travelerCount;

    @OneToMany(mappedBy = "travelGroup", fetch = FetchType.EAGER) //cascade remove checken en orphan removal bij traveler entity
    private Set<Traveler> travelers;

    public TravelGroup() {
    }

    public TravelGroup(int travelerCount) {
        this.travelerCount = travelerCount;
    }

    public Long getTravelGroupId() {
        return travelGroupId;
    }

    public void setTravelGroupId(Long travelGroupId) {
        this.travelGroupId = travelGroupId;
    }

    public int getTravelerCount() {
        return travelerCount;
    }

    public void setTravelerCount(int travelerCount) {
        this.travelerCount = travelerCount;
    }

    public Set<Traveler> getTravelers() {
        return travelers;
    }

    public void addTravelers(Traveler traveler) {
        travelers.add(traveler);
    }

    @Override
    public String toString() {
        return "TravelGroup{" +
                "travelGroupId=" + travelGroupId +
                ", travelerCount=" + travelerCount +
                ", travelers=" + travelers +
                "}";
    }
}
