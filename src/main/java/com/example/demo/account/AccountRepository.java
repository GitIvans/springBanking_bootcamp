package com.example.demo.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT s FROM Account s WHERE s.name = ?1")
    Optional<Account> findAccountByName(String name);
}
