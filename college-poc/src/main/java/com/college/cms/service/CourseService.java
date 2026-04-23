package com.college.cms.service;

import com.college.cms.entity.Course;
import com.college.cms.repo.CourseRepo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
    @Autowired
    private CourseRepo repo;

    public List<Course> list() {
        return repo.findAll();
    }

    public Course get(Long id) {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Course not found: " + id));
    }

    public Course create(Course course) {
        course.setId(null);
        return repo.save(course);
    }

    public Course update(Long id, Course course) {
        Course existing = get(id);
        existing.setTitle(course.getTitle());
        existing.setCode(course.getCode());
        return repo.save(existing);
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new IllegalArgumentException("Course not found: " + id);
        }
        repo.deleteById(id);
    }
}
