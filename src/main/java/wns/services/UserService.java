package wns.services;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import wns.aspects.ToLog;
import wns.constants.Messages;
import wns.entity.Owner;
import wns.entity.User;
import wns.dto.UserDTO;
import wns.repo.UsersRepo;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService  implements MainService {
    private final UsersRepo usersRepo;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @ToLog
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
        List<User> all = (List<User>) usersRepo.findAll();
        return all.stream()
                .map(x -> modelMapper.map(x, UserDTO.class))
                .collect(Collectors.toList());
    }

    public User getUserByID(long id) {
        return usersRepo.findById(id).get();
    }

    @ToLog
    public void updateUser(User user) {
        User userFromDb = usersRepo.findById(user.getId()).get();
        userFromDb.setUsername(user.getUsername());
        userFromDb.setFullName(user.getFullName());
        userFromDb.setPhoneNumber(user.getPhoneNumber());
        if(!user.getPassword().isEmpty())
        {
            if(!passwordEncoder.matches(user.getPassword(),userFromDb.getPassword())) {
                String encode = passwordEncoder.encode(user.getPassword());
                userFromDb.setPassword(encode);
            }
        }
        usersRepo.save(userFromDb);
    }

    @Override
    public void delete(long id) {
        usersRepo.deleteById(id);
    }
}
