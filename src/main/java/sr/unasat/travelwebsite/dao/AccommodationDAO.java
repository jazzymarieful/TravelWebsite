package sr.unasat.travelwebsite.dao;

import sr.unasat.travelwebsite.entity.Accommodation;
import sr.unasat.travelwebsite.jpaconfig.JPAConfiguration;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class AccommodationDAO {

    private EntityManager entityManager = JPAConfiguration.getEntityManager();

    public Accommodation findAccommodationByDestinationAndRating(Long destinationId, int rating) {
        List<Accommodation> accommodationList;
        Accommodation closestAccommodationByRating;
        entityManager.getTransaction().begin();
        String jpql1 = "select a from Accommodation a where a.destination.destinationId = :destinationId and (a.rating = :rating or a.rating < :rating) order by a.rating desc";
        TypedQuery<Accommodation> query1 = entityManager.createQuery(jpql1, Accommodation.class);
        accommodationList = query1.setParameter("destinationId", destinationId).setParameter("rating", rating).setMaxResults(1).getResultList();
        if (accommodationList.size() != 0) {
            closestAccommodationByRating = accommodationList.get(0);
        } else {
            String jpql2 = "select a from Accommodation a where a.destination.destinationId = :destinationId and a.rating > :rating order by a.rating";
            TypedQuery<Accommodation> query2 = entityManager.createQuery(jpql2, Accommodation.class);
            accommodationList = query2.setParameter("destinationId", destinationId).setParameter("rating", rating).setMaxResults(1).getResultList();
            closestAccommodationByRating = accommodationList.get(0);
        }
        entityManager.getTransaction().commit();
        return closestAccommodationByRating;
    }
}
