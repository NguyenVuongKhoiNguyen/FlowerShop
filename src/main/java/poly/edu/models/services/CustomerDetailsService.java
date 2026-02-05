package poly.edu.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import poly.edu.models.entities.Account;

import java.util.List;

@Service
public class CustomerDetailsService implements UserDetailsService {

    @Autowired
    private AccountServices accountServices;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        Account account;
        try {
            account = accountServices.findById(username);
        } catch (Exception e) {
            throw new UsernameNotFoundException("User not found");
        }

        // Role
        String role = account.getAdmin() != null && account.getAdmin()
                ? "ROLE_ADMIN"
                : "ROLE_USER";

        return new User(
                account.getUsername(),
                account.getPassword(),      // plain password (school project)
                account.getActivated(),     // enabled
                true,                       // accountNonExpired
                true,                       // credentialsNonExpired
                true,                       // accountNonLocked
                List.of(new SimpleGrantedAuthority(role))
        );
    }
}
