package com.myorg.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.myorg.model.User;

@Repository
public interface UserDao /*extends CrudRepository<User, Long>*/ {
    User findByUsername(String username);
    User save(User user);
    List<User> findAll();
    void delete(long id);
    User findOne(String username);

    User findById(Long id);
}
