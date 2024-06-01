package com.breezevillepark_ms.bvp_ms.management;

import com.breezevillepark_ms.bvp_ms.employee.Gender;
import com.breezevillepark_ms.bvp_ms.employee.Role;
import com.breezevillepark_ms.bvp_ms.order.Order;
import com.breezevillepark_ms.bvp_ms.payment.Payment;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponse {
    private Integer employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private Gender gender;
    private LocalDateTime joinedDate;
    private String nin;
    private String phoneNumber;
    private String password;
    private Role role;
    private LocalDate dateOfBirth;
    private List<Payment> payments;
    private List<Order> orders;
    private Double salary;
    private List<String> academicQualifications;

}
