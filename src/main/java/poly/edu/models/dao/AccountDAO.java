package poly.edu.models.dao;

import poly.edu.models.entities.Account;


public interface AccountDAO extends InterfaceDAO<Account, String> {

    Account findByUsernameAndPassword(String username, String password);
}
