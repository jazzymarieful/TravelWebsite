package sr.unasat.travelwebsite.dao;

import sr.unasat.travelwebsite.entity.Account;
import sr.unasat.travelwebsite.jpaconfig.JPAConfiguration;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class AccountDAO {

    private EntityManager entityManager = JPAConfiguration.getEntityManager();

    public boolean verifyAccount(String username, String password) {
        boolean isVerified = false;
        List<Account> accountList;
        entityManager.getTransaction().begin();
        String jpql = "select a from Account a where a.username = :username and a.password = :password";
        TypedQuery<Account> query = entityManager.createQuery(jpql, Account.class);
        accountList = query.setParameter("username", username).setParameter("password", password).getResultList();
        if (!accountList.isEmpty()) {
            isVerified = true;
            System.out.println("Account verified");
        } else {
            System.out.println("Account couldn't be verified");
        }
        entityManager.getTransaction().commit();
        return isVerified;
    }

    public Account retrieveAccount(String username, String password) {
        List<Account> accountList;
        Account account = null;
        entityManager.getTransaction().begin();
        String jpql = "select a from Account a where a.username = :username and a.password = :password";
        TypedQuery<Account> query = entityManager.createQuery(jpql, Account.class);
        accountList = query.setParameter("username", username).setParameter("password", password).getResultList();
        if (!accountList.isEmpty()) {
            account = accountList.get(0);
        } else {
            System.out.println("Account couldn't be retrieved");
        }
        entityManager.getTransaction().commit();
        return account;
    }
}
