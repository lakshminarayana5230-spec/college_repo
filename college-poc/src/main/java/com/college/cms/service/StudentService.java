package com.college.cms.service;

import com.college.cms.entity.Student;
import com.college.cms.repo.StudentRepo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    @Autowired
    private StudentRepo repo;

    public List<Student> list() {
        return repo.findAll();
    }

    public Student get(Long id) {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Student not found: " + id));
    }

    public Student getByPhoneNumber(String phoneNumber) {
        return repo.findByPhoneNumber(phoneNumber)
                .orElseThrow(
                        () -> new IllegalArgumentException("Student not found for phoneNumber: " + phoneNumber));
    }

    public Student create(Student student) {
        student.setId(null);
        return repo.save(student);
    }

    public Student update(Long id, Student student) {
        Student existing = get(id);
        existing.setName(student.getName());
        existing.setAddress(student.getAddress());
        existing.setPhoneNumber(student.getPhoneNumber());
        return repo.save(existing);
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new IllegalArgumentException("Student not found: " + id);
        }
        repo.deleteById(id);
    }
}
