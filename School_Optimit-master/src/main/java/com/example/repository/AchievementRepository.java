package com.example.repository;

import com.example.entity.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AchievementRepository extends JpaRepository<Achievement,Integer> {

    Optional<Achievement> findByName(String name);

    List<Achievement> findAllByUserId(Integer userId);
}
