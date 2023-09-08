package com.redmath.studentapp.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByName(String name);

    @Query(value="select * from students s where s.name like ?", nativeQuery = true)
    List<Student> findByNameLike(String name);
}
