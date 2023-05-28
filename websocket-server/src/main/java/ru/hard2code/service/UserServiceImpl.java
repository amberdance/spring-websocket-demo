package ru.hard2code.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hard2code.model.User;
import ru.hard2code.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl
        implements UserService {

    private final UserRepository userRepository;


    @Override
    public void createUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User createRandomUser() {
        var random = UUID.randomUUID().toString().substring(0, 8);
        var user = User.builder()
                .username("@" + random)
                .email("somemail@" + random + ".com")
                .firstName(random)
                .lastName(random)
                .build();

        return userRepository.save(user);
    }

    @Override
    public List<User> findAllUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }
}
