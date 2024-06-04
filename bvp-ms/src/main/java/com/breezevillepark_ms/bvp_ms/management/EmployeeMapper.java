package com.breezevillepark_ms.bvp_ms.management;

import com.breezevillepark_ms.bvp_ms.common.NinValidator;
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
    private final NinValidator ninValidator;

    public void validateNin(String nin){
        if (!ninValidator.test(nin)){
            throw new IllegalStateException("Nin" + nin + " is not valid");
        }
    }
    public void validatePhoneNumber(String phoneNumber){
        if (!validator.test(phoneNumber)) {
            throw new IllegalStateException("Phone Number " + phoneNumber + " is not valid");
        }
    }
    public Employee toEmployee(EmployeeCreationRequest creationRequest){
        validatePhoneNumber(creationRequest.phoneNumber());
        validateNin(creationRequest.nin());
        return Employee.builder()
                .firstName(creationRequest.firstName())
                .lastName(creationRequest.lastName())
                .email(creationRequest.email())
                .gender(creationRequest.gender())
                .joinedDate(creationRequest.joinedDate())
                .nin(creationRequest.nin())
                .phoneNumber(creationRequest.phoneNumber())
                .password(encoder.encode(creationRequest.password()))
                .role(creationRequest.role())
                .dateOfBirth(creationRequest.dateOfBirth())
                .salary(creationRequest.salary())
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
                .payments(employee.getPayments())
                .orders(employee.getOrders())
                .salary(employee.getSalary())
                .academicQualifications(employee.getAcademicQualifications())
                .build();
    }

    public Employee fromUpdateRequest(EmployeeUpdateRequest employeeUpdateRequest){
        validatePhoneNumber(employeeUpdateRequest.phoneNumber());
        validateNin(employeeUpdateRequest.nin());
        return Employee.builder()
                .employeeId(employeeUpdateRequest.id())
                .firstName(employeeUpdateRequest.firstName())
                .lastName(employeeUpdateRequest.lastName())
                .email(employeeUpdateRequest.email())
                .gender(employeeUpdateRequest.gender())
                .joinedDate(employeeUpdateRequest.joinedDate())
                .nin(employeeUpdateRequest.nin())
                .phoneNumber(employeeUpdateRequest.phoneNumber())
                .password(employeeUpdateRequest.password())
                .role(employeeUpdateRequest.role())
                .dateOfBirth(employeeUpdateRequest.dateOfBirth())
                .salary(employeeUpdateRequest.salary())
                .academicQualifications(employeeUpdateRequest.academicQualifications())
                .build();
    }
}
