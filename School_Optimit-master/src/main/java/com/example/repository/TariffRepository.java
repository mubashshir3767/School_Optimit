package com.example.repository;

import com.example.entity.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TariffRepository extends JpaRepository<Tariff,Integer> {
    List<Tariff> findAllByActiveAndDelete(boolean active, boolean delete);
    List<Tariff> findAllByDelete(boolean delete);
}
