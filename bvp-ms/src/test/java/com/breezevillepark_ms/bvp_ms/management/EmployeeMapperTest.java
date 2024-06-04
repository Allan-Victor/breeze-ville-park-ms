package com.breezevillepark_ms.bvp_ms.management;

import com.breezevillepark_ms.bvp_ms.common.NinValidator;
import com.breezevillepark_ms.bvp_ms.common.PhoneNumberValidator;
import com.breezevillepark_ms.bvp_ms.employee.Employee;
import com.breezevillepark_ms.bvp_ms.employee.Gender;
import com.breezevillepark_ms.bvp_ms.employee.Role;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeMapperTest {

    private EmployeeMapper underTest;

    @Mock
    private PhoneNumberValidator validator;

    @Mock
    private NinValidator ninValidator;
    @Mock
    private PasswordEncoder encoder;
    private AutoCloseable autoCloseable;



    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new EmployeeMapper(encoder,validator, ninValidator);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void itShouldToEmployee() {
        // Given
        var faker = new Faker();
        String nin = faker.regexify("[C-W0-9]{14}");
        String phoneNumber = faker.phoneNumber().subscriberNumber(10) ;
        String password = encoder.encode(faker.regexify("[098][#$%]"));

        // ... given an employee creation request
        EmployeeCreationRequest request = new EmployeeCreationRequest(
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().emailAddress(),
                Gender.FEMALE,
                LocalDateTime.now(),
                nin,
                phoneNumber,
                password,
                Role.USER,
                LocalDate.of(1994,4,6),
                3.77,
                Collections.emptyList());

        when(validator.test(phoneNumber)).thenReturn(true);
        when(ninValidator.test(nin)).thenReturn(true);
        when(encoder.encode(password)).thenReturn(password);

        // When
        Employee employee1 = underTest.toEmployee(request);

        // Then
        assertEquals(request.firstName(), employee1.getFirstName());
        assertEquals(request.lastName(), employee1.getLastName());
        assertEquals(request.email(), employee1.getEmail());
        assertEquals(request.gender(), employee1.getGender());
        assertEquals(request.joinedDate(), employee1.getJoinedDate());
        assertEquals(request.nin(), employee1.getNin());
        assertEquals(request.phoneNumber(), employee1.getPhoneNumber());
        assertEquals(request.password(), employee1.getPassword());
        assertEquals(request.role(), employee1.getRole());
        assertEquals(request.dateOfBirth(), employee1.getDateOfBirth());
        assertEquals(request.salary(), employee1.getSalary());
        assertEquals(request.academicQualifications(), employee1.getAcademicQualifications());

        verify(validator, times(1)).test(phoneNumber);
        verify(ninValidator, times(1)).test(nin);
        verify(encoder, times(1)).encode(password);
    }

    @Test
    void itShouldThrowExceptionWhenPhoneValidatorFails() {
        // Given
        String phoneNumber = "90876654";
        var faker = new Faker();
        String nin = faker.regexify("[C-W0-9]{14}");

        // ... given an employee creation request
        EmployeeCreationRequest request = new EmployeeCreationRequest(
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().emailAddress(),
                Gender.FEMALE,
                LocalDateTime.now(),
                nin,
                phoneNumber,
                faker.regexify("[098][#$%]"),
                Role.USER,
                LocalDate.of(1994,4,6),
                3.77,
                Collections.emptyList());

        given(validator.test(phoneNumber)).willReturn(false);

        // When
        // Then
        assertThatThrownBy(() ->underTest.toEmployee(request))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Phone Number " + phoneNumber + " is not valid");
    }

    @Test
    void itShouldThrowExceptionWhenNinValidatorFails() {
        // Given
        var faker = new Faker();
        String nin = faker.regexify("[C-W0-9]{14}");

        // ... given an employee creation request
        EmployeeCreationRequest request = new EmployeeCreationRequest(
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().emailAddress(),
                Gender.FEMALE,
                LocalDateTime.now(),
                nin,
                faker.phoneNumber().subscriberNumber(10) ,
                faker.regexify("[098][#$%]"),
                Role.USER,
                LocalDate.of(1994,4,6),
                3.77,
                Collections.emptyList());

        given(validator.test(request.phoneNumber())).willReturn(true);
        given(ninValidator.test(nin)).willReturn(false);

        // When
        // Then
        assertThatThrownBy(() ->underTest.toEmployee(request))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Nin" + nin + " is not valid");
    }

    @Test
    void itShouldToEmployeeResponse() {
        // Given
        var faker = new Faker();
        Employee employee1 = new Employee(
                1,
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().emailAddress(),
                Gender.FEMALE,
                LocalDateTime.now(),
                faker.regexify("[C-W0-9]{14}"),
                faker.phoneNumber().subscriberNumber(10),
                faker.regexify("[098][#$%]"),
                Role.USER,
                LocalDate.of(1994,4,6),
                Collections.emptyList(),
                Collections.emptyList(),
                faker.number().randomDouble(2,2,10),
                Collections.emptyList());

        // When

        EmployeeResponse employeeResponse = underTest.toEmployeeResponse(employee1);

        // Then
        assertEquals(employee1.getEmployeeId(), employeeResponse.getEmployeeId());
        assertEquals(employee1.getFirstName(), employeeResponse.getFirstName());
        assertEquals(employee1.getLastName(), employeeResponse.getLastName());
        assertEquals(employee1.getEmail(), employeeResponse.getEmail());
        assertEquals(employee1.getGender(), employeeResponse.getGender());
        assertEquals(employee1.getJoinedDate(), employeeResponse.getJoinedDate());
        assertEquals(employee1.getNin(), employeeResponse.getNin());
        assertEquals(employee1.getPhoneNumber(), employeeResponse.getPhoneNumber());
        assertEquals(employee1.getPassword(), employeeResponse.getPassword());
        assertEquals(employee1.getRole(), employeeResponse.getRole());
        assertEquals(employee1.getDateOfBirth(), employeeResponse.getDateOfBirth());
        assertEquals(employee1.getPayments(), employeeResponse.getPayments());
        assertEquals(employee1.getOrders(), employeeResponse.getOrders());
        assertEquals(employee1.getSalary(), employeeResponse.getSalary());
        assertEquals(employee1.getAcademicQualifications(), employeeResponse.getAcademicQualifications());

    }

    @Test
    void itShouldFromUpdateRequest() {
        // Given
        var faker = new Faker();
        String phone = faker.phoneNumber().subscriberNumber(10);
        String nin = faker.regexify("[C-W0-9]{14}");
        // ... given an employee update request
        EmployeeUpdateRequest request = new EmployeeUpdateRequest(
                1,
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().emailAddress(),
                Gender.FEMALE,
                LocalDateTime.now(),
                nin,
                phone,
                faker.regexify("[098][#$%]"),
                Role.USER,
                LocalDate.of(1994,4,6),
                3.77,
                Collections.emptyList());

        given(validator.test(phone)).willReturn(true);
        given(ninValidator.test(nin)).willReturn(true);

        // When
        Employee employee = underTest.fromUpdateRequest(request);

        // Then
        assertEquals(request.firstName(), employee.getFirstName());
        assertEquals(request.lastName(), employee.getLastName());
        assertEquals(request.email(), employee.getEmail());
        assertEquals(request.gender(), employee.getGender());
        assertEquals(request.joinedDate(), employee.getJoinedDate());
        assertEquals(request.nin(), employee.getNin());
        assertEquals(request.phoneNumber(), employee.getPhoneNumber());
        assertEquals(request.password(), employee.getPassword());
        assertEquals(request.role(), employee.getRole());
        assertEquals(request.dateOfBirth(), employee.getDateOfBirth());
        assertEquals(request.salary(), employee.getSalary());
        assertEquals(request.academicQualifications(), employee.getAcademicQualifications());
    }
}