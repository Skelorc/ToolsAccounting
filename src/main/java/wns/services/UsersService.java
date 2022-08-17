package wns.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import wns.entity.User;
import wns.repo.UsersRepo;

@Service
public class UsersService implements UserDetailsService {
    private final UsersRepo usersRepo;

    public UsersService(UsersRepo usersRepo) {
        this.usersRepo = usersRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = usersRepo.findByUsername(username);
        if(user == null)
        {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
}
