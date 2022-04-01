package sr.unasat.travelwebsite.dao;

import sr.unasat.travelwebsite.entity.Account;
import sr.unasat.travelwebsite.entity.TravelPackage;
import sr.unasat.travelwebsite.jpaconfig.JPAConfiguration;
import sr.unasat.travelwebsite.reportresultset.DestinationByVisitFrequency;
import sr.unasat.travelwebsite.reportresultset.PeriodByVisitFrequency;
import sr.unasat.travelwebsite.reportresultset.TravelersAgeByPeriod;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class TravelPackageDAO {

    private EntityManager entityManager = JPAConfiguration.getEntityManager();

    public TravelPackage retrieveLastTravelPackageByAccount(Account account) {
        entityManager.getTransaction().begin();
        String jpql = "select t from TravelPackage t where t.account.accountId = :accountId order by t.travelPackageId desc";
        TypedQuery<TravelPackage> query = entityManager.createQuery(jpql, TravelPackage.class);
        TravelPackage travelPackage = query.setParameter("accountId", account.getAccountId()).getResultList().stream().findFirst().orElse(null);
        entityManager.getTransaction().commit();
        return travelPackage;
    }

    public List<TravelPackage> retrieveAllTravelPackagesByAccount(Account account) {
        entityManager.getTransaction().begin();
        String jpql = "select t from TravelPackage t where t.account.username = :accountUsername and t.account.password = :accountPassword";
        TypedQuery<TravelPackage> query = entityManager.createQuery(jpql, TravelPackage.class);
        List<TravelPackage> travelPackageList = query.setParameter("accountUsername", account.getUsername()).
                setParameter("accountPassword", account.getPassword()).getResultList();
        entityManager.getTransaction().commit();
        System.out.println(travelPackageList);
        return travelPackageList;
    }

    public TravelPackage insertTravelPackage(TravelPackage travelPackage) {
        entityManager.getTransaction().begin();
        entityManager.persist(travelPackage);
        entityManager.getTransaction().commit();
//        System.out.println("Travel Package has been inserted");
        return travelPackage;
    }

    public boolean deleteTravelPackage(Long travelPackageId) {
        entityManager.getTransaction().begin();
        int rowsDeleted;
        String jpql = "delete from TravelPackage t where t.travelPackageId = :travelPackageId";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("travelPackageId", travelPackageId);
        rowsDeleted = query.executeUpdate();
        System.out.println("Travel packages deleted: " + rowsDeleted);
        entityManager.getTransaction().commit();
        return rowsDeleted != 0;
    }

    public List<DestinationByVisitFrequency> frequencyOfDestinationVisitsByYear(int year) {
        entityManager.getTransaction().begin();
        String jpql = "select new sr.unasat.travelwebsite.reportresultset.DestinationByVisitFrequency(tpts.destination, year(tpts.travelPlan.startDate), count(tpts.destination.destinationId)) " +
                "from TravelPackage t join t.travelGroup.travelers tgt join t.travelPlan.travelSegments tpts where year(tpts.travelPlan.startDate) = :startDate " +
                "group by tpts.destination.destinationId order by count(tpts.destination.destinationId)";
        TypedQuery<DestinationByVisitFrequency> query = entityManager.createQuery(jpql, DestinationByVisitFrequency.class);
        List<DestinationByVisitFrequency> resultList = query.setParameter("startDate", year).getResultList();
        entityManager.getTransaction().commit();
//        System.out.println(resultList);
        return resultList;
    }

    public List<DestinationByVisitFrequency> frequencyOfDestinationVisitsBySemiYear(int year, int period) {
        int first, second;
//        if (period == 6) {
//            first = 1; second = 2;
//        } else {
//            first = 3; second = 4;
//        }
        first = (period == 6) ? 1 : 3;
        second = (period == 6) ? 2 : 4;
        entityManager.getTransaction().begin();
        String jpql = "select new sr.unasat.travelwebsite.reportresultset.DestinationByVisitFrequency(tpts.destination, year(tpts.travelPlan.startDate), count(tpts.destination.destinationId)) " +
                "from TravelPackage t join t.travelGroup.travelers tgt join t.travelPlan.travelSegments tpts where year(tpts.travelPlan.startDate) = :startDate " +
                "and quarter(tpts.travelPlan.startDate) in (:first, :second) group by tpts.destination.destinationId order by count(tpts.destination.destinationId)";
        TypedQuery<DestinationByVisitFrequency> query = entityManager.createQuery(jpql, DestinationByVisitFrequency.class);
        List<DestinationByVisitFrequency> resultList = query.setParameter("startDate", year).setParameter("first", first).setParameter("second", second).getResultList();
        entityManager.getTransaction().commit();
        System.out.println("By semi");
//        System.out.println(resultList);
        return resultList;
    }

    public List<DestinationByVisitFrequency> frequencyOfDestinationVisitsByQuarter(int year, int period) {
        entityManager.getTransaction().begin();
        String jpql = "select new sr.unasat.travelwebsite.reportresultset.DestinationByVisitFrequency(tpts.destination, year(tpts.travelPlan.startDate), count(tpts.destination.destinationId)) " +
                "from TravelPackage t join t.travelGroup.travelers tgt join t.travelPlan.travelSegments tpts where year(tpts.travelPlan.startDate) = :startDate " +
                "and quarter(tpts.travelPlan.startDate) = :period group by tpts.destination.destinationId order by count(tpts.destination.destinationId)";
        TypedQuery<DestinationByVisitFrequency> query = entityManager.createQuery(jpql, DestinationByVisitFrequency.class);
        List<DestinationByVisitFrequency> resultList = query.setParameter("startDate", year).setParameter("period", period).getResultList();
        entityManager.getTransaction().commit();
        System.out.println("By quarter");
//        System.out.println(resultList);
        return resultList;
    }

    public List<PeriodByVisitFrequency> frequencyOfTravelersByPeriod(int period) {
        String jpqlPeriod = (period == 5) ? "year" : "quarter";
        entityManager.getTransaction().begin();
        String jpql = "select new sr.unasat.travelwebsite.reportresultset.PeriodByVisitFrequency(" + jpqlPeriod + "(t.travelPlan.startDate), count(t))" +
                " from TravelPackage t join t.travelGroup.travelers tgt group by " + jpqlPeriod + "(t.travelPlan.startDate)";
        TypedQuery<PeriodByVisitFrequency> query = entityManager.createQuery(jpql, PeriodByVisitFrequency.class);
        List<PeriodByVisitFrequency> resultList = query.getResultList();
        entityManager.getTransaction().commit();
//        System.out.println(resultList);
        return resultList;
    }

    //report voor traveler report handler
    public List<TravelersAgeByPeriod> frequencyOfTravelersByAge(int year) {
        List<TravelersAgeByPeriod> resultList = new ArrayList<>();
        entityManager.getTransaction().begin();
        String jpql1 = "select new sr.unasat.travelwebsite.reportresultset.TravelersAgeByPeriod('1-30'," + year + " , count(t)) from TravelPackage t join t.travelGroup.travelers tgt join t.travelPlan tp where (tgt.age between 1 and 30) and year(tp.startDate) = :year";
        TypedQuery<TravelersAgeByPeriod> query1 = entityManager.createQuery(jpql1, TravelersAgeByPeriod.class);
        resultList.add(query1.setParameter("year", year).getSingleResult());

        String jpql2 = "select new sr.unasat.travelwebsite.reportresultset.TravelersAgeByPeriod('31-60'," + year + ", count(t)) from TravelPackage t join t.travelGroup.travelers tgt join t.travelPlan tp where (tgt.age between 31 and 60) and year(tp.startDate) = :year";
        TypedQuery<TravelersAgeByPeriod> query2 = entityManager.createQuery(jpql2, TravelersAgeByPeriod.class);
        resultList.add(query2.setParameter("year", year).getSingleResult());

        String jpql3 = "select new sr.unasat.travelwebsite.reportresultset.TravelersAgeByPeriod('61-100'," + year + ", count(t)) from TravelPackage t join t.travelGroup.travelers tgt join t.travelPlan tp where (tgt.age between 61 and 100) and year(tp.startDate) = :year";
        TypedQuery<TravelersAgeByPeriod> query3 = entityManager.createQuery(jpql3, TravelersAgeByPeriod.class);
        resultList.add(query3.setParameter("year", year).getSingleResult());

        entityManager.getTransaction().commit();
//        System.out.println(resultList);
        return resultList;
    }


}
