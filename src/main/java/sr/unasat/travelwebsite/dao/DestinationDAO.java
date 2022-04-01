package sr.unasat.travelwebsite.dao;

import sr.unasat.travelwebsite.entity.Destination;
import sr.unasat.travelwebsite.reportresultset.QueriedDestination;
import sr.unasat.travelwebsite.jpaconfig.JPAConfiguration;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class DestinationDAO {

    private EntityManager entityManager = JPAConfiguration.getEntityManager();

    public List<Destination> retrieveDestinationList() {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("select d from Destination d");
        List<Destination> destinationList = query.getResultList();
        entityManager.getTransaction().commit();
        System.out.println("destinationDAO ran");
        return destinationList;
    }

    public List<QueriedDestination> retrieveQueriedDestinationList() {
        entityManager.getTransaction().begin();
        String jpql = "select new sr.unasat.travelwebsite.reportresultset.QueriedDestination(d.country, d.city, d.location) from Destination d";
        TypedQuery<QueriedDestination> query = entityManager.createQuery(jpql, QueriedDestination.class);
        List<QueriedDestination> destinationList = query.getResultList();
        entityManager.getTransaction().commit();
        System.out.println(destinationList);
        return destinationList;
    }

    public Destination retrieveDestinationById(Long destinationId) {
        entityManager.getTransaction().begin();
        String jpql = "select d from Destination d where d.destinationId = :destinationId";
        TypedQuery<Destination> query = entityManager.createQuery(jpql, Destination.class);
        Destination destination = query.setParameter("destinationId", destinationId).getResultList().stream().findFirst().orElse(null);
        entityManager.getTransaction().commit();
        return destination;
    }

    public Destination insertDestination(Destination destination) {
        entityManager.getTransaction().begin();
        entityManager.persist(destination);
        entityManager.getTransaction().commit();
        System.out.println("Destination has been inserted");
        return destination;
    }

    public boolean updateDestination(Destination destination) {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("update Destination d set d.country = :country, " +
                "d.city = :city," +
                "d.location = :location " +
                "where d.destinationId = :destinationId");
        query.setParameter("country", destination.getCountry());
        query.setParameter("city", destination.getCity());
        query.setParameter("location", destination.getLocation());
        query.setParameter("destinationId", destination.getDestinationId());
        int rowsUpdated = query.executeUpdate();
        System.out.println("Entities updated: " + rowsUpdated);
        entityManager.getTransaction().commit();
        return rowsUpdated > 0;
    }

    public boolean deleteDestination(Long destinationId) {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("delete from Destination d where d.destinationId = :destinationId");
        query.setParameter("destinationId", destinationId);
        int rowsDeleted = query.executeUpdate();
        System.out.println("Destinations deleted: " + rowsDeleted);
        entityManager.getTransaction().commit();
        return rowsDeleted > 0;
    }

    public int checkRecordIncrease() {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery ("select count(d) from Destination  d");
        int result = (int) query.getSingleResult();
        entityManager.getTransaction().commit();
        return result;
    }

}
