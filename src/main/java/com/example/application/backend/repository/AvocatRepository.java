package com.example.application.backend.repository;

import com.example.application.backend.entity.Avocat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface AvocatRepository  extends JpaRepository<Avocat,Integer>{

    @Query("SELECT a FROM Avocat a " +
            "where lower(a.firstName) like lower(concat('%', :searchTerm,'%')) " +
            " or lower(a.lastName) like lower(concat('%', :searchTerm,'%'))"
    )
    Collection<Avocat> search(@Param("searchTerm") String filterLawyer);
}
