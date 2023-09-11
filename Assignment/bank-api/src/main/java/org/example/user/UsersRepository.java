package org.example.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<Users,Long> {
    @Query(value = "SELECT * FROM users WHERE user_name = ?", nativeQuery = true)
    Users findByUserName(@Param("user_name")String user_name);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Users u WHERE u.account_id = ?1", nativeQuery=true)
    void deleteByAccountId(@Param("accountId")Long accountId);

    @Query(value="select * from USERS u where u.user_name like ?", nativeQuery = true)
    List<Users> findByNameLike(String name);

    @Query(value = "select * FROM Users u WHERE u.account_id = ?1", nativeQuery=true)
    Users getUserByAccId(@Param("accountId")Long accountId);


}
