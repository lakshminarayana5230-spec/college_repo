package com.college.cms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;

@Entity
@Table(name = "payment", schema = "college")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
  @Id
  @GeneratedValue
  private Long id;

  /** Authoritative relationship to Enrollment (FK enroll_id). */
  @JsonIgnore
  @ManyToOne(optional = false)
  @JoinColumn(name = "enroll_id", nullable = false)
  private Enrollment enrollment;

  /**
   * Kept for compatibility/search in API responses.
   * Populated from the enrolled student's phone number.
   */
  @Column(name = "student_phone_number")
  private String studentPhoneNumber;

  /** Payment date (date only). */
  @Column(name = "payment_date")
  private LocalDate paymentDate;

  @Enumerated(EnumType.STRING)
  @Column(name = "payment_status", nullable = false)
  private PaymentStatus paymentStatus;

  private Double amount;

  public enum PaymentStatus {
    PENDING,
    SUCCESS,
    FAILED
  }
}
