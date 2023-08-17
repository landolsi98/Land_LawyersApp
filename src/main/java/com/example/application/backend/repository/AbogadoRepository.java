package com.example.application.backend.repository;

import com.example.application.backend.entity.Abogado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbogadoRepository extends JpaRepository<Abogado, Integer> {
}
