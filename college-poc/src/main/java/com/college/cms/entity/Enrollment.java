package com.college.cms.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;

@Entity
@Table(name = "enrollment", schema = "college")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Enrollment {
  @Id
  @GeneratedValue
  private Long id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "student_id", nullable = false)
  private Student student;

  @ManyToOne(optional = false)
  @JoinColumn(name = "course_id", nullable = false)
  private Course course;

  @Column(name = "enrollment_date", nullable = false)
  private LocalDate enrollmentDate;

  @Enumerated(EnumType.STRING)
  @Column(name = "enrollment_status", nullable = false)
  private EnrollmentStatus enrollmentStatus;

  public enum EnrollmentStatus {
    PENDING,
    ACTIVE,
    COMPLETED,
    CANCELLED
  }
}
