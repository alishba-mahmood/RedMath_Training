package org.example.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByName(String name);

    @Query(value = "SELECT account_id FROM Account a WHERE a.name = :name", nativeQuery=true)
    Optional<Long> findIdByName(@Param("name")String name);
}
