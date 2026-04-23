package com.college.cms.controller;

import com.college.cms.entity.Student;
import com.college.cms.service.StudentService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Students", description = "CRUD APIs for students")
@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService svc;

    public record StudentRequest(
            @Schema(example = "John Doe") @NotBlank String name,
            @Schema(example = "Hyderabad, India") String address,
            @Schema(example = "+91-9876543210") @NotBlank String phoneNumber) {
    }

    @Operation(summary = "List students")
    @GetMapping
    public List<Student> list() {
        return svc.list();
    }

    @Operation(summary = "Get student by id")
    @GetMapping("/{id}")
    public Student get(@Parameter(example = "1") @PathVariable Long id) {
        return svc.get(id);
    }

    @Operation(summary = "Get student by phone number")
    @GetMapping("/by-phone")
    public Student getByPhoneNumber(@RequestParam String phoneNumber) {
        return svc.getByPhoneNumber(phoneNumber);
    }

    @Operation(summary = "Create student")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Student create(@Valid @RequestBody StudentRequest req) {
        return svc.create(
                Student.builder().name(req.name()).address(req.address()).phoneNumber(req.phoneNumber()).build());
    }

    @Operation(summary = "Update student")
    @PutMapping("/{id}")
    public Student update(@PathVariable Long id, @Valid @RequestBody StudentRequest req) {
        return svc.update(
                id,
                Student.builder().name(req.name()).address(req.address()).phoneNumber(req.phoneNumber()).build());
    }

    @Operation(summary = "Delete student")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        svc.delete(id);
    }
}
