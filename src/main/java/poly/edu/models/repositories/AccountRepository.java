package poly.edu.models.repositories;

import org.springframework.stereotype.Repository;

import poly.edu.models.dao.AbstractDAO;
import poly.edu.models.dao.AccountDAO;
import poly.edu.models.entities.Account;

@Repository
public class AccountRepository extends AbstractDAO<Account, String> implements AccountDAO {

    public AccountRepository() {
        super(Account.class);
    }

    @Override
    public Account findByUsernameAndPassword(String username, String password) {
        return entityManager.createQuery(
                "FROM Account a WHERE a.username = :u AND a.password = :p",
                Account.class
        )
        .setParameter("u", username)
        .setParameter("p", password)
        .getResultStream()
        .findFirst()
        .orElse(null);
    }
}


