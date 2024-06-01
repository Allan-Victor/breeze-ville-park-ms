package com.breezevillepark_ms.bvp_ms.management;

import com.breezevillepark_ms.bvp_ms.common.PhoneNumberValidator;
import com.breezevillepark_ms.bvp_ms.employee.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeeMapper {
    private final PasswordEncoder encoder;
    private final PhoneNumberValidator validator;
    public Employee toEmployee(EmployeeCreationRequest creationRequest){
        String phoneNumber = creationRequest.phoneNumber();
        if (!validator.test(phoneNumber)) {
            throw new IllegalStateException("Phone Number " + phoneNumber + " is not valid");
        }
        return Employee.builder()
                .firstName(creationRequest.firstName())
                .lastName(creationRequest.lastName())
                .email(creationRequest.email())
                .gender(creationRequest.gender())
                .joinedDate(creationRequest.joinedDate())
                .nin(creationRequest.nin())
                .phoneNumber(phoneNumber)
                .password(encoder.encode(creationRequest.password()))
                .role(creationRequest.role())
                .dateOfBirth(creationRequest.dateOfBirth())
                .academicQualifications(creationRequest.academicQualifications())
                .build();
    }

    public EmployeeResponse toEmployeeResponse(Employee employee){
        return EmployeeResponse.builder()
                .employeeId(employee.getEmployeeId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .gender(employee.getGender())
                .joinedDate(employee.getJoinedDate())
                .nin(employee.getNin())
                .phoneNumber(employee.getPhoneNumber())
                .password(employee.getPassword())
                .role(employee.getRole())
                .dateOfBirth(employee.getDateOfBirth())
                .orders(employee.getOrders())
                .salary(employee.getSalary())
                .academicQualifications(employee.getAcademicQualifications())
                .build();
    }

    public Employee fromUpdateRequest(EmployeeUpdateRequest employeeUpdateRequest){
        String phoneNumber = employeeUpdateRequest.phoneNumber();
        if (!validator.test(phoneNumber)) {
            throw new IllegalStateException("Phone Number " + phoneNumber + " is not valid");
        }
        return Employee.builder()
                .employeeId(employeeUpdateRequest.id())
                .firstName(employeeUpdateRequest.firstName())
                .lastName(employeeUpdateRequest.lastName())
                .email(employeeUpdateRequest.email())
                .gender(employeeUpdateRequest.gender())
                .joinedDate(employeeUpdateRequest.joinedDate())
                .nin(employeeUpdateRequest.nin())
                .phoneNumber(phoneNumber)
                .password(employeeUpdateRequest.password())
                .role(employeeUpdateRequest.role())
                .dateOfBirth(employeeUpdateRequest.dateOfBirth())
                .salary(employeeUpdateRequest.salary())
                .academicQualifications(employeeUpdateRequest.academicQualifications())
                .build();
    }
}
