package ru.hard2code.repository;

import org.springframework.data.repository.CrudRepository;
import ru.hard2code.domain.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {
}
