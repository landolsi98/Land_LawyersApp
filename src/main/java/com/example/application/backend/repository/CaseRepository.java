package com.example.application.backend.repository;

import com.example.application.backend.entity.Case;
import com.example.application.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaseRepository extends JpaRepository<Case, Integer> {
    Case findCaseByIdCase(Integer Case);

    Case findCaseByClient(User client);
}
