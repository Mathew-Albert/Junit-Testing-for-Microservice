package com.ust.userservice.repository;

import com.ust.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    User findByUserIdAndUserPassword(String userId, String userPassword);
}
