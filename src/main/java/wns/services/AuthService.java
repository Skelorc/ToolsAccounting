package wns.services;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wns.entity.User;
import wns.repo.UsersRepo;


@Service
@AllArgsConstructor
public class AuthService implements UserDetailsService {
    private final UsersRepo usersRepo;


    @Transactional(readOnly = true)
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
