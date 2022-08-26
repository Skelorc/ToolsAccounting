package wns.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import wns.entity.User;
import wns.repo.UsersRepo;

@Service
public class AuthService implements UserDetailsService {
    private final UsersRepo usersRepo;

    public AuthService(UsersRepo usersRepo) {
        this.usersRepo = usersRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String fullName) throws UsernameNotFoundException {
        User user = usersRepo.findByUsername(fullName);
        if(user == null)
        {
            throw new UsernameNotFoundException("User not found");
        }
        user.set_active(true);
        return user;
    }
}
