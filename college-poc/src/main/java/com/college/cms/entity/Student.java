package com.college.cms.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "student", schema = "college")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {
  @Id @GeneratedValue private Long id;

  private String name;
  private String address;

  // Keep as String to support country codes and leading zeros
  private String phoneNumber;
}
