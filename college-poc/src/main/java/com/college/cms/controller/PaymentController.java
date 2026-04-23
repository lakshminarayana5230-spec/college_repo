package com.college.cms.controller;

import com.college.cms.entity.Payment;
import com.college.cms.entity.Payment.PaymentStatus;
import com.college.cms.service.PaymentService;
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

@Tag(name = "Payments", description = "CRUD APIs for payments")
@RestController
@RequestMapping("/payments")
public class PaymentController {
  @Autowired private PaymentService svc;

  public record PaymentRequest(
      @Schema(example = "1", description = "Enrollment id") @NotNull Long enrollmentId,
      @Schema(example = "2026-01-23", description = "Payment date (YYYY-MM-DD). If omitted, defaults to today.")
          LocalDate paymentDate,
      @Schema(example = "250.0") @NotNull Double amount) {}

  public record PaymentResponse(
      @Schema(example = "1") Long id,
      @Schema(example = "1") Long enrollmentId,
      @Schema(example = "+91-9876543210") String studentPhoneNumber,
      @Schema(example = "2026-01-23") LocalDate paymentDate,
      @Schema(example = "SUCCESS") PaymentStatus paymentStatus,
      @Schema(example = "250.0") Double amount) {
    public static PaymentResponse fromEntity(Payment p) {
      Long enrollmentId = p.getEnrollment() != null ? p.getEnrollment().getId() : null;
      return new PaymentResponse(
          p.getId(), enrollmentId, p.getStudentPhoneNumber(), p.getPaymentDate(), p.getPaymentStatus(), p.getAmount());
    }
  }

  @Operation(summary = "List payments")
  @GetMapping
  public List<PaymentResponse> list() {
    return svc.list().stream().map(PaymentResponse::fromEntity).toList();
  }

  @Operation(summary = "Get payment by id")
  @GetMapping("/{id}")
  public PaymentResponse get(@Parameter(example = "1") @PathVariable Long id) {
    return PaymentResponse.fromEntity(svc.get(id));
  }

  @Operation(summary = "Create payment")
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public PaymentResponse create(@Valid @RequestBody PaymentRequest req) {
    return PaymentResponse.fromEntity(
        svc.create(req.enrollmentId(), req.paymentDate(), req.amount()));
  }

  @Operation(summary = "Update payment")
  @PutMapping("/{id}")
  public PaymentResponse update(@PathVariable Long id, @Valid @RequestBody PaymentRequest req) {
    return PaymentResponse.fromEntity(
        svc.update(id, req.enrollmentId(), req.paymentDate(), req.amount()));
  }

  @Operation(summary = "Delete payment")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    svc.delete(id);
  }
}
