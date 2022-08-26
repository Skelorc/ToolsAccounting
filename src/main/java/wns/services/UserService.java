package wns.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import wns.constants.Answers;
import wns.constants.Roles;
import wns.entity.User;
import wns.entity.UserDTO;
import wns.repo.UsersRepo;

import java.util.Collections;
import java.util.Objects;

@Service
public class UserService {
    private final UsersRepo usersRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UsersRepo usersRepo, PasswordEncoder passwordEncoder) {
        this.usersRepo = usersRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public String saveUser(UserDTO dto) {
        User userFromDb = usersRepo.findByUsername(dto.getUsername());
        if (userFromDb == null) {
            createUserFromDTOAndSave(dto);
            return Answers.USER_CREATE.getValue();
        }
        else
            return Answers.USER_EXISTS.getValue();
    }

    private void createUserFromDTOAndSave(UserDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setFullName(dto.getFullName());
        user.setRoles(dto.getRoles());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        usersRepo.save(user);
    }


}
