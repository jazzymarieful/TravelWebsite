package sr.unasat.travelwebsite.dao;

import sr.unasat.travelwebsite.entity.TravelGroup;
import sr.unasat.travelwebsite.entity.Traveler;
import sr.unasat.travelwebsite.jpaconfig.JPAConfiguration;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class TravelerDAO {

    private EntityManager entityManager = JPAConfiguration.getEntityManager();

    private TravelGroupDAO travelGroupDAO = new TravelGroupDAO();

    public List<Traveler> retrieveAllTravelers() {
        List<Traveler> travelerList;
        entityManager.getTransaction().begin();
        String jpql = "select t from Traveler t";
        Query query = entityManager.createQuery(jpql);
        travelerList = query.getResultList();
        entityManager.getTransaction().commit();
        System.out.println("travelerDAO ran");
//        JPAConfiguration.shutdown();
        return travelerList;
    }

    public boolean insertTraveler(Traveler traveler) {
        entityManager.getTransaction().begin();
//        int tableSize = checkRecordIncrease();
        entityManager.persist(traveler);
        entityManager.getTransaction().commit();
//        if (checkRecordIncrease() > tableSize) {
//            System.out.println("Traveler inserted");
//            return true;
//        } else {
//            return false;
//        }
        System.out.println("Traveler inserted");
        return true;
    }

    public int checkRecordIncrease() {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery ("select count(t) from Traveler t");
        int result = (int) query.getSingleResult();
        entityManager.getTransaction().commit();
        return result;
    }

    public Traveler findTravelerByPassport(String passport) {
        String jpql = "select t from Traveler t where t.passport = :passport";
        TypedQuery<Traveler> query = entityManager.createQuery(jpql, Traveler.class);
        Traveler traveler = query.setParameter("passport", passport).getResultList().stream().findFirst().orElse(null);
        return traveler;
    }

    public boolean updateTraveler(Traveler traveler) {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("update Traveler t set t.firstName = :firstName, " +
                "t.lastName = :lastName," +
                "t.age = :age " +
                "where t.passport = :passport");
        query.setParameter("firstName", traveler.getFirstName());
        query.setParameter("lastName", traveler.getLastName());
        query.setParameter("passport", traveler.getPassport());
        query.setParameter("age", traveler.getAge());
        int rowsUpdated = query.executeUpdate();
        System.out.println("Travelers updated: " + rowsUpdated);
        entityManager.getTransaction().commit();
        return rowsUpdated > 0;
    }

    public boolean deleteTraveler(String passport) {
        entityManager.getTransaction().begin();
        int rowsDeleted = 0;
        if (findTravelerByPassport(passport) != null) {
            TravelGroup travelGroup = travelGroupDAO.findTravelGroupByTraveler(passport);
            if (travelGroup.getTravelerCount() > 1) {
                String jpql = "delete from Traveler t where t.passport = :passport";
                Query query = entityManager.createQuery(jpql);
                query.setParameter("passport", passport);
                rowsDeleted = query.executeUpdate();
                travelGroupDAO.updateTravelGroupDecreaseTravelerCount(travelGroup);
            } else {
                System.out.println("Only one traveler left in travel package \nRemoval of traveler not allowed");
            }
            travelGroup.getTravelerCount();
        } else {
            System.out.println("Traveler not found");
        }
        System.out.println("Travelers deleted: " + rowsDeleted);
        entityManager.getTransaction().commit();
        return rowsDeleted > 0;
    }

}
