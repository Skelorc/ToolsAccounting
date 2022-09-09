package wns.services;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import wns.constants.Messages;
import wns.entity.User;
import wns.dto.UserDTO;
import wns.repo.UsersRepo;
import wns.utils.Mapper;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    private final UsersRepo usersRepo;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public Messages saveUser(UserDTO dto) {
        User userFromDb = usersRepo.findByUsername(dto.getUsername());
        if (userFromDb == null) {
            User user = modelMapper.map(dto, User.class);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            usersRepo.save(user);
            return Messages.USER_CREATE;
        } else
            return Messages.USER_EXISTS;
    }

    public List<UserDTO> getAllUsers() {
        List<User> all = usersRepo.findAll();
        return all.stream()
                .map(x -> modelMapper.map(x, UserDTO.class))
                .collect(Collectors.toList());
    }

    public UserDTO getDTOByID(long id) {
        Optional<User> byId = usersRepo.findById(id);
        return byId.map(x -> modelMapper.map(x, UserDTO.class)).orElseGet(UserDTO::new);
    }

    public Messages updateUser(UserDTO userDTO) {
        User user = usersRepo.findById(userDTO.getId()).orElse(null);
        if (user != null) {
            modelMapper.map(userDTO, user);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            usersRepo.save(user);
            return Messages.USER_UPDATE;
        } else
            return Messages.USER_NOT_FOUND;
    }

    public void deleteUser(long id) {
        usersRepo.deleteById(id);
    }
}
