package com.example.application.backend.repository;

import com.example.application.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    User findByUsername(String username);


    User findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.authority.idRol = :idRol")
    List<User> findUserByIdRol(@Param("idRol") Integer idRol);

    @Query ("SELECT u FROM User u " +
            "where lower(u.firstName) like lower(concat('%', :searchTerm,'%')) " +
            " or lower(u.lastName) like lower(concat('%', :searchTerm,'%'))"
    )
    Collection<User> search(@Param("searchTerm") String filter);
}
