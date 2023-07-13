package com.example.repository;

import com.example.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject,Integer> {
    Optional<Object> findByName(String name);
    Optional<Object> findByLevel(int level);
}
