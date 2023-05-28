package ru.hard2code.service;

import ru.hard2code.model.User;

import java.util.List;

public interface UserService {

    void createUser(User user);

    User createRandomUser();

    List<User> findAllUsers();
}
