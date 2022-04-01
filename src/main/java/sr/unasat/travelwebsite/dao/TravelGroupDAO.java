package sr.unasat.travelwebsite.dao;

import sr.unasat.travelwebsite.entity.TravelGroup;
import sr.unasat.travelwebsite.jpaconfig.JPAConfiguration;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class TravelGroupDAO {

    private EntityManager entityManager = JPAConfiguration.getEntityManager();

    public TravelGroup findTravelGroupByTraveler(String passport) {
        String jpql = "select t from TravelGroup t join t.travelers tr where tr.passport = :passport";
        TypedQuery<TravelGroup> query = entityManager.createQuery(jpql, TravelGroup.class);
        List<TravelGroup> travelGroupList = query.setParameter("passport", passport).getResultList();
        TravelGroup travelGroup = null;
        if (!travelGroupList.isEmpty()) {
            travelGroup = travelGroupList.get(0);
        } else {
            System.out.println("No travel group found");
        }
        return travelGroup;
    }

    public TravelGroup findLastTravelGroupRecord() {
        entityManager.getTransaction().begin();
        String jpql = "select t from TravelGroup t order by t.travelGroupId desc";
        TypedQuery<TravelGroup> query = entityManager.createQuery(jpql, TravelGroup.class);
        List<TravelGroup> travelGroupList = query.setMaxResults(1).getResultList();
        TravelGroup lastTravelGroup = travelGroupList.get(0);
        entityManager.getTransaction().commit();
        return lastTravelGroup;
    }

    public TravelGroup insertTravelGroup(TravelGroup travelGroup) {
        entityManager.getTransaction().begin();
        entityManager.persist(travelGroup);
        entityManager.getTransaction().commit();
        System.out.println("Travel group inserted");
        return travelGroup;
    }

    public List<TravelGroup> findAllTravelGroups() {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("select t from TravelGroup t");
        List<TravelGroup> travelGroupList = query.getResultList();
        entityManager.getTransaction().commit();
        return travelGroupList;
    }

    public int updateTravelGroupDecreaseTravelerCount(TravelGroup travelGroup) {
        String jpql = "update TravelGroup t set t.travelerCount = :decreasedTravelerCount where t.travelGroupId = :travelGroupId";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("decreasedTravelerCount", travelGroup.getTravelerCount() - 1);
        query.setParameter("travelGroupId", travelGroup.getTravelGroupId());
        int rowsUpdated = query.executeUpdate();
        System.out.println("Travel group updated: " + rowsUpdated);
        return rowsUpdated;
    }




}
