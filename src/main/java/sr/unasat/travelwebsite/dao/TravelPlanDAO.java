package sr.unasat.travelwebsite.dao;

import sr.unasat.travelwebsite.entity.TravelPlan;
import sr.unasat.travelwebsite.jpaconfig.JPAConfiguration;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class TravelPlanDAO {

    private EntityManager entityManager = JPAConfiguration.getEntityManager();

//    public TravelPlanDAO(EntityManager entityManager) {
//        this.entityManager = entityManager;
//    }

    public TravelPlan findLastTravelPlanRecord() {
        entityManager.getTransaction().begin();
        String jpql = "select t from TravelPlan t order by t.travelPlanId desc";
        TypedQuery<TravelPlan> query = entityManager.createQuery(jpql, TravelPlan.class);
        List<TravelPlan> travelPlanList = query.setMaxResults(1).getResultList();
        TravelPlan lastTravelPlan = travelPlanList.get(0);
        entityManager.getTransaction().commit();
        return lastTravelPlan;
    }

    public List<TravelPlan> retrieveTravelPlans() {
        entityManager.getTransaction().begin();
        String jpql = "select t from TravelPlan t order by t.startDate";
        TypedQuery<TravelPlan> query = entityManager.createQuery(jpql, TravelPlan.class);
        List<TravelPlan> travelPlanList = query.getResultList();
        entityManager.getTransaction().commit();
        return travelPlanList;
    }

    public TravelPlan insertTravelPlan(TravelPlan travelPlan) {
        entityManager.getTransaction().begin();
        entityManager.persist(travelPlan);
        entityManager.getTransaction().commit();
//        System.out.println("Travel plan has been inserted");
        return travelPlan;
    }

}
