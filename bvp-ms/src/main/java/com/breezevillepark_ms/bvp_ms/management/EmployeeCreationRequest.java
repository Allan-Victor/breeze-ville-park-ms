package com.breezevillepark_ms.bvp_ms.management;


import com.breezevillepark_ms.bvp_ms.employee.Gender;
import com.breezevillepark_ms.bvp_ms.employee.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record EmployeeCreationRequest(

        @NotNull(message = "First Name cannot be null")
        @NotEmpty(message = "First Name cannot be empty")
        String firstName,

        @NotNull(message = "Last Name cannot be null")
        @NotEmpty(message = "Last Name cannot be empty")
        String lastName,

        @NotNull(message = "Email cannot be null")
        @Email(message = "Email should be valid")
        String email,
        Gender gender,
        LocalDateTime joinedDate,

        @NotNull(message = "Nin cannot be null")
        @NotEmpty(message = "Nin cannot be empty")
        String nin,

        String phoneNumber,
        String password,
        Role role,

        @Past
        LocalDate dateOfBirth,
        Double salary,
        List<String> academicQualifications
) {
}
