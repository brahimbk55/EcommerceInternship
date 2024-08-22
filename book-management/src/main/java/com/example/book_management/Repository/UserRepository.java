package com.example.book_management.Repository;

import com.example.book_management.Entities.App_user;
import com.example.book_management.Entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<App_user,Long> {
    Optional<App_user> findByEmail(String email);

    App_user findByRole(Role role);


}
