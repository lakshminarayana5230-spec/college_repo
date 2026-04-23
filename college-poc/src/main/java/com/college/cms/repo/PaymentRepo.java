package com.college.cms.repo;

import com.college.cms.entity.*;
import org.springframework.data.jpa.repository.*;

public interface PaymentRepo extends JpaRepository<Payment, Long> {
}
