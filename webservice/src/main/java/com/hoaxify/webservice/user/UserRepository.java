package com.hoaxify.webservice.user;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByActivationToken( String activationToken);

   }