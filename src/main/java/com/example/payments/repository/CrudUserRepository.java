package com.example.payments.repository;

import com.example.payments.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface CrudUserRepository extends JpaRepository<User, Integer> {

    User getByEmail(String email);

    @Query("SELECT u.balance FROM User u where u.id=:userId")
    Long getCurrentBalance(@Param("userId") int userId);

}
