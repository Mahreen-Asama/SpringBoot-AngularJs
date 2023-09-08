package com.redmath.studentapp.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUname(String uname);

    @Query(value = "SELECT u FROM Users u WHERE u.uname = ?1")
    User findByUname2(String uname);

    @Query(value = "SELECT * FROM users WHERE uname = ?", nativeQuery = true)
    User findByUname3(String username);
}
