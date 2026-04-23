package com.college.cms.repo;

import com.college.cms.entity.Student;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepo extends JpaRepository<Student, Long> {

    Optional<Student> findByPhoneNumber(String phoneNumber);
}
