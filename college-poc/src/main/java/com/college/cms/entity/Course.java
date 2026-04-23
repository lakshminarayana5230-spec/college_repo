package com.college.cms.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "course", schema = "college")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {
  @Id @GeneratedValue private Long id;
  private String title;
  private String code;
}
