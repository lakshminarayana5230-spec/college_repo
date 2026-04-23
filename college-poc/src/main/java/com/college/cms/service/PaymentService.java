package com.college.cms.service;

import com.college.cms.entity.Enrollment;
import com.college.cms.entity.Enrollment.EnrollmentStatus;
import com.college.cms.entity.Payment;
import com.college.cms.entity.Payment.PaymentStatus;
import com.college.cms.repo.EnrollmentRepo;
import com.college.cms.repo.PaymentRepo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepo repo;
    @Autowired
    private EnrollmentRepo erepo;

    public List<Payment> list() {
        return repo.findAll();
    }

    public Payment get(Long id) {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Payment not found: " + id));
    }

    public Payment create(Long enrollmentId, LocalDate paymentDate, Double amount) {
        var e =
                erepo
                        .findById(enrollmentId)
                        .orElseThrow(() -> new IllegalArgumentException("Enrollment not found: " + enrollmentId));

        String phoneNumber = (e.getStudent() != null) ? e.getStudent().getPhoneNumber() : null;

        LocalDate effectiveDate = (paymentDate != null) ? paymentDate : LocalDate.now();
        PaymentStatus effectiveStatus = PaymentStatus.SUCCESS;

        // Business rule: once payment is SUCCESS, enrollment should be ACTIVE.
        applyEnrollmentStatusRule(e, effectiveStatus);

        return repo.save(
                Payment.builder()
                        .enrollment(e)
                        .studentPhoneNumber(phoneNumber)
                        .paymentDate(effectiveDate)
                        .paymentStatus(effectiveStatus)
                        .amount(amount)
                        .build());
    }

    public Payment update(Long id, Long enrollmentId, LocalDate paymentDate, Double amount) {
        Payment existing = get(id);

        if (enrollmentId != null) {
            var e =
                    erepo
                            .findById(enrollmentId)
                            .orElseThrow(() -> new IllegalArgumentException("Enrollment not found: " + enrollmentId));
            existing.setEnrollment(e);

            String phoneNumber = (e.getStudent() != null) ? e.getStudent().getPhoneNumber() : null;
            existing.setStudentPhoneNumber(phoneNumber);
        }

        if (paymentDate != null) {
            existing.setPaymentDate(paymentDate);
        } else if (existing.getPaymentDate() == null) {
            existing.setPaymentDate(LocalDate.now());
        }

        // Service rule: payment processing here implies SUCCESS.
        existing.setPaymentStatus(PaymentStatus.SUCCESS);
        existing.setAmount(amount);
        Enrollment enrollment = existing.getEnrollment();
        if (enrollment != null) {
            applyEnrollmentStatusRule(enrollment, existing.getPaymentStatus());
        }

        return repo.save(existing);
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new IllegalArgumentException("Payment not found: " + id);
        }
        repo.deleteById(id);
    }

    private void applyEnrollmentStatusRule(Enrollment enrollment, PaymentStatus paymentStatus) {
        if (enrollment == null || paymentStatus == null) {
            return;
        }

        if (paymentStatus == PaymentStatus.SUCCESS) {
            if (enrollment.getEnrollmentStatus() != EnrollmentStatus.ACTIVE) {
                enrollment.setEnrollmentStatus(EnrollmentStatus.ACTIVE);
                erepo.save(enrollment);
            }
        }
    }
}
