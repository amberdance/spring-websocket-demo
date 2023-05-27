package ru.hard2code.repository;

import org.springframework.data.repository.CrudRepository;
import ru.hard2code.domain.model.UserRole;

import java.util.Optional;

public interface UserRoleRepository
        extends CrudRepository<UserRole, Integer> {

    Optional<UserRole> findByName(UserRole.Role role);


}
