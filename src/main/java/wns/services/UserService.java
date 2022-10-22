package wns.services;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import wns.constants.Messages;
import wns.constants.Roles;
import wns.entity.User;
import wns.dto.UserDTO;
import wns.repo.UsersRepo;
import wns.utils.Mapper;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService  implements MainService {
    private final UsersRepo usersRepo;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public Messages saveUser(User user) {
        User userFromDb = usersRepo.findByUsername(user.getUsername());
        if (userFromDb == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            usersRepo.save(user);
            return Messages.USER_CREATE;
        } else
            return Messages.USER_EXISTS;
    }

    public List<UserDTO> getAll() {
        List<User> all = usersRepo.findAll();
        return all.stream()
                .map(x -> modelMapper.map(x, UserDTO.class))
                .collect(Collectors.toList());
    }

    public User getUserByID(long id) {
        return usersRepo.findById(id).get();
    }

    public void updateUser(User user) {
        usersRepo.save(user);
    }

    public void deleteUser(long id) {
        usersRepo.deleteById(id);
    }

   }
