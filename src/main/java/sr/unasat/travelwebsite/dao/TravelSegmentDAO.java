package sr.unasat.travelwebsite.dao;

import sr.unasat.travelwebsite.entity.TravelSegment;
import sr.unasat.travelwebsite.jpaconfig.JPAConfiguration;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class TravelSegmentDAO {

    private EntityManager entityManager = JPAConfiguration.getEntityManager();

//    public TravelSegmentDAO(EntityManager entityManager) {
//        this.entityManager = entityManager;
//    }

    public List<TravelSegment> retrieveTravelSegmentByDestinationFrequency() {
        List<TravelSegment> travelSegmentList;
        entityManager.getTransaction().begin();
        String jpql = "select t from TravelSegment t join t.destination d group by d.destinationId order by count(d), d.destinationId";
        TypedQuery<TravelSegment> query = entityManager.createQuery(jpql, TravelSegment.class);
        travelSegmentList = query.getResultList();
        entityManager.getTransaction().commit();
        return travelSegmentList;
    }

    public List<TravelSegment> findAllTravelSegments() {
        entityManager.getTransaction().begin();
        String jpql = "select t from TravelSegment t";
        Query query = entityManager.createQuery(jpql);
        List<TravelSegment> travelSegmentList = query.getResultList();
        entityManager.getTransaction().commit();
        return travelSegmentList;
    }

    public TravelSegment insertTravelSegment(TravelSegment travelSegment) {
        entityManager.getTransaction().begin();
        entityManager.persist(travelSegment);
        entityManager.getTransaction().commit();
//        System.out.println("Travel segment has been inserted");
        return travelSegment;
    }

}
