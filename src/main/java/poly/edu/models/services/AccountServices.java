package poly.edu.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import poly.edu.models.dao.AccountDAO;
import poly.edu.models.entities.Account;

@Service
@Transactional
public class AccountServices implements ServiceInterface<Account, String> {
	
	@Autowired
	private AccountDAO accountDAO;

	@Override
	public void save(Account entity) {
		// TODO Auto-generated method stub
		boolean found = accountDAO.findById(entity.getUsername()) != null ? true : false;
		
		if (found) {
			accountDAO.update(entity);
			return;
		}
		
		accountDAO.create(entity);
	}

	@Override
	public Account delete(String id) {
		// TODO Auto-generated method stub
		return accountDAO.delete(id);
	}

	@Override
	public Account findById(String id) {
		// TODO Auto-generated method stub
		Account account =  accountDAO.findById(id);
		
		if (account == null) {
	        throw new RuntimeException("Account not found");
	    }
		
		return account;
	}

	@Override
	public List<Account> findAll() {
		// TODO Auto-generated method stub
		return accountDAO.findAll();
	}
	
	
	public Boolean login(String username, String password) {
		
		Account account = accountDAO.findByUsernameAndPassword(username, password);
		if (account == null) return false;
		
		return true;
	}
	
	public List<Account> filter(String keyword, Boolean activated, Boolean admin) {

	    List<Account> accounts = accountDAO.findAll();

	    return accounts.stream()
	        .filter(a -> {
	            if (keyword != null && !keyword.isBlank()) {
	                boolean matchUsername = a.getUsername() != null && a.getUsername().toLowerCase().contains(keyword.toLowerCase());
	                boolean matchFullname = a.getFullname() != null && a.getFullname().toLowerCase().contains(keyword.toLowerCase());
	                if (!matchUsername && !matchFullname) {
	                    return false;
	                }
	            }

	            if (activated != null) {
	                if (!activated.equals(a.getActivated())) {
	                    return false;
	                }
	            }

	            if (admin != null) {
	                if (!admin.equals(a.getAdmin())) {
	                    return false;
	                }
	            }

	            return true;
	        }).toList();
	}
}
