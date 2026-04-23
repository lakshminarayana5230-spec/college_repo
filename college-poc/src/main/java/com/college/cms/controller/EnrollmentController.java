package com.college.cms.controller;

import com.college.cms.entity.Enrollment;
import com.college.cms.service.EnrollmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Enrollments", description = "CRUD APIs for enrollments")
@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {
  @Autowired private EnrollmentService svc;

  public record EnrollmentRequest(
      @Schema(example = "1") @NotNull Long studentId,
      @Schema(example = "1") @NotNull Long courseId,
      @Schema(example = "2026-01-23", description = "Defaults to today when omitted")
          LocalDate enrollmentDate) {}

  @Operation(summary = "List enrollments")
  @GetMapping
  public List<Enrollment> list() {
    return svc.list();
  }

  @Operation(summary = "Get enrollment by id")
  @GetMapping("/{id}")
  public Enrollment get(@Parameter(example = "1") @PathVariable Long id) {
    return svc.get(id);
  }

  @Operation(
      summary = "Create enrollment",
      description = "Creates a student-course enrollment. Status is set automatically (initially PENDING; becomes ACTIVE after payment SUCCESS).")
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public Enrollment create(@Valid @RequestBody EnrollmentRequest req) {
    return svc.create(req.studentId(), req.courseId(), req.enrollmentDate());
  }

  @Operation(summary = "Update enrollment")
  @PutMapping("/{id}")
  public Enrollment update(@PathVariable Long id, @Valid @RequestBody EnrollmentRequest req) {
    return svc.update(id, req.studentId(), req.courseId(), req.enrollmentDate());
  }

  @Operation(summary = "Delete enrollment")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    svc.delete(id);
  }
}
