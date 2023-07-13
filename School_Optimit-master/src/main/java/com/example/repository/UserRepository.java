package com.example.repository;


import com.example.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Integer> {


    Optional<User> findByPhoneNumber(String phoneNumber);

    Optional<User> findByPhoneNumberAndVerificationCode(String phoneNumber, Integer verificationCode);

    boolean existsByPhoneNumber(String phoneNumber);
}
