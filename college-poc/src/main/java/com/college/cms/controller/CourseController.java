package com.college.cms.controller;

import com.college.cms.entity.Course;
import com.college.cms.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Courses", description = "CRUD APIs for courses")
@RestController
@RequestMapping("/courses")
public class CourseController {
    @Autowired
    private CourseService svc;

    public record CourseRequest(
            @Schema(example = "Math") @NotBlank String title,
            @Schema(example = "MATH-101") @NotBlank String code) {
    }

    @Operation(summary = "List courses")
    @GetMapping
    public List<Course> list() {
        return svc.list();
    }

    @Operation(summary = "Get course by id")
    @GetMapping("/{id}")
    public Course get(@Parameter(example = "1") @PathVariable Long id) {
        return svc.get(id);
    }

    @Operation(summary = "Create course")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Course create(@Valid @RequestBody CourseRequest req) {
        return svc.create(Course.builder().title(req.title()).code(req.code()).build());
    }

    @Operation(summary = "Update course")
    @PutMapping("/{id}")
    public Course update(@PathVariable Long id, @Valid @RequestBody CourseRequest req) {
        return svc.update(id, Course.builder().title(req.title()).code(req.code()).build());
    }

    @Operation(summary = "Delete course")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        svc.delete(id);
    }
}
