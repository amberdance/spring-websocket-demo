package ru.hard2code.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hard2code.domain.model.User;
import ru.hard2code.domain.model.UserRole;
import ru.hard2code.repository.UserRepository;
import ru.hard2code.repository.UserRoleRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl
        implements UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;


    @Override
    public void createUser(User user) {
        var role =
                userRoleRepository.findByName(UserRole.Role.USER).orElseThrow(
                        () -> new RuntimeException("Role was not found"));

        user.setRole(role);
        userRepository.save(user);
    }
}
