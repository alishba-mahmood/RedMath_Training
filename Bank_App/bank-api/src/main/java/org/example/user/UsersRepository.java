package org.example.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UsersRepository extends JpaRepository<Users,Long> {
    @Query(value = "SELECT * FROM users WHERE user_name = ?", nativeQuery = true)
    Users findByUserName(String user_mame);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Users u WHERE u.account_id = ?1", nativeQuery=true)
    void deleteByAccountId(@Param("accountId")Long accountId);
}
