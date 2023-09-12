package com.example.application.backend.repository;

import com.example.application.backend.entity.Case;
import com.example.application.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CaseRepository extends JpaRepository<Case, Integer> {
    Case findCaseByIdCase(Integer Case);

    Case findCaseByClient(User client);

    @Query("SELECT c FROM Case c " +
            "where lower(c.title) like lower(concat('%', :searchTerm,'%')) " +
            " or lower(c.client.firstName) like lower(concat('%', :searchTerm,'%'))"+
            " or lower(c.abogado.firstName) like lower(concat('%', :searchTerm,'%'))"

    )
    Collection<Case> search(@Param("searchTerm") String caseFilter);
}
