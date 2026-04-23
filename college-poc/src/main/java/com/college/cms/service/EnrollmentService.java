package com.college.cms.service;

import com.college.cms.entity.Enrollment;
import com.college.cms.entity.Enrollment.EnrollmentStatus;
import com.college.cms.repo.CourseRepo;
import com.college.cms.repo.EnrollmentRepo;
import com.college.cms.repo.StudentRepo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnrollmentService {
    @Autowired
    private EnrollmentRepo repo;
    @Autowired
    private StudentRepo srepo;
    @Autowired
    private CourseRepo crepo;

    public List<Enrollment> list() {
        return repo.findAll();
    }

    public Enrollment get(Long id) {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Enrollment not found: " + id));
    }

    public Enrollment create(Long studentId, Long courseId, LocalDate enrollmentDate) {
        var s = srepo.findById(studentId).orElseThrow(() -> new IllegalArgumentException("Student not found: " + studentId));
        var c = crepo.findById(courseId).orElseThrow(() -> new IllegalArgumentException("Course not found: " + courseId));

        LocalDate effectiveDate = (enrollmentDate != null) ? enrollmentDate : LocalDate.now();

        // Status is managed by business rules (e.g., payment SUCCESS => ACTIVE)
        EnrollmentStatus initialStatus = EnrollmentStatus.PENDING;

        return repo.save(
                Enrollment.builder()
                        .student(s)
                        .course(c)
                        .enrollmentDate(effectiveDate)
                        .enrollmentStatus(initialStatus)
                        .build());
    }

    public Enrollment update(Long id, Long studentId, Long courseId, LocalDate enrollmentDate) {
        Enrollment existing = get(id);

        if (studentId != null) {
            var s = srepo.findById(studentId).orElseThrow(() -> new IllegalArgumentException("Student not found: " + studentId));
            existing.setStudent(s);
        }

        if (courseId != null) {
            var c = crepo.findById(courseId).orElseThrow(() -> new IllegalArgumentException("Course not found: " + courseId));
            existing.setCourse(c);
        }

        if (enrollmentDate != null) {
            existing.setEnrollmentDate(enrollmentDate);
        }

        // Do NOT allow updating enrollmentStatus from EnrollmentController.
        // It is updated automatically via PaymentService.

        return repo.save(existing);
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new IllegalArgumentException("Enrollment not found: " + id);
        }
        repo.deleteById(id);
    }
}
