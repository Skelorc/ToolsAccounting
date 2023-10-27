package wns.services;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wns.aspects.ToLog;
import wns.entity.User;
import wns.dto.UserDTO;
import wns.repo.UsersRepo;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class UserService {
    private final UsersRepo usersRepo;
    private final PasswordEncoder passwordEncoder;

    @ToLog
    public void saveUser(User user) {
        User userFromDb = usersRepo.findByUsername(user.getUsername());
        if (userFromDb == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            usersRepo.save(user);
        }
    }

    @Transactional(readOnly = true)
    public List<UserDTO> getAll() {
        List<User> all = (List<User>) usersRepo.findAll();
        return all.stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public User getUserByID(long id) {
        return usersRepo.findById(id).get();
    }

    @ToLog
    public void updateUser(User user) {
        User userFromDb = usersRepo.findById(user.getId()).get();
        userFromDb.setUsername(user.getUsername());
        userFromDb.setFullName(user.getFullName());
        userFromDb.setPhoneNumber(user.getPhoneNumber());
        userFromDb.setRoles(user.getRoles());
        if(!user.getPassword().isEmpty())
        {
            if(!passwordEncoder.matches(user.getPassword(),userFromDb.getPassword())) {
                String encode = passwordEncoder.encode(user.getPassword());
                userFromDb.setPassword(encode);
            }
        }
        usersRepo.save(userFromDb);
    }

    public void delete(long id) {
        usersRepo.deleteById(id);
    }

}
