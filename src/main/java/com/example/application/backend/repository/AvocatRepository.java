package com.example.application.backend.repository;

import com.example.application.backend.entity.Abogado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface AvocatRepository  extends JpaRepository<Abogado,Integer>{

    @Query("SELECT a FROM Abogado a " +
            "where lower(a.firstName) like lower(concat('%', :searchTerm,'%')) " +
            " or lower(a.position) like lower(concat('%', :searchTerm,'%'))"
    )
    Collection<Abogado> search(@Param("searchTerm") String filterLawyer);
}
