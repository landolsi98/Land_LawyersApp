package com.example.application.backend.repository;

import com.example.application.backend.entity.Avocat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvocatRepository  extends JpaRepository<Avocat,Integer>{
}
