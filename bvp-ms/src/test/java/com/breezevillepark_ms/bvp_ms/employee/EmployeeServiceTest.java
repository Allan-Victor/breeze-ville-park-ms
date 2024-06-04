package com.breezevillepark_ms.bvp_ms.employee;

import com.breezevillepark_ms.bvp_ms.common.PageResponse;
import com.breezevillepark_ms.bvp_ms.exception.DuplicateResourceException;
import com.breezevillepark_ms.bvp_ms.management.EmployeeCreationRequest;
import com.breezevillepark_ms.bvp_ms.management.EmployeeMapper;
import com.breezevillepark_ms.bvp_ms.management.EmployeeResponse;
import com.breezevillepark_ms.bvp_ms.management.EmployeeUpdateRequest;
import com.github.javafaker.Faker;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;


class EmployeeServiceTest {
    private EmployeeService underTest;
    @Mock
    private EmployeeRepository employeeRepository;
    private AutoCloseable autoCloseable;

    @Captor
    private ArgumentCaptor<Employee> employeeArgumentCaptor;

    @Mock
    private EmployeeMapper employeeMapper;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new EmployeeService(employeeRepository,employeeMapper);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void itShouldAddEmployee() {
        // Given an employee and nin
        var faker = new Faker();
        String nin = faker.regexify("[C-W0-9]{14}");

        Employee employee = new Employee(
                1,
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().emailAddress(),
                Gender.FEMALE,
                LocalDateTime.now(),
                nin,
                faker.phoneNumber().subscriberNumber(10),
                faker.regexify("[098][#$%]"),
                Role.USER,
                LocalDate.of(1994,4,6),
                Collections.emptyList(),
                Collections.emptyList(),
                faker.number().randomDouble(2,2,10),
                Collections.emptyList());

        // ... given an employee creation request
        EmployeeCreationRequest request = new EmployeeCreationRequest(employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getGender(),
                employee.getJoinedDate(),
                employee.getNin(),
                employee.getPhoneNumber(),
                employee.getPassword(),
                employee.getRole(),
                employee.getDateOfBirth(),
                employee.getSalary(),
                employee.getAcademicQualifications());

        given(employeeRepository.existsByNin(nin)).willReturn(false);
        given(employeeMapper.toEmployee(request)).willReturn(employee);
        given(employeeRepository.save(employee)).willReturn(employee);



        // When
        Integer employeeId = underTest.addEmployee(request);


        // Then
        then(employeeRepository).should().save(employeeArgumentCaptor.capture());
        Employee employeeArgumentCaptorValue = employeeArgumentCaptor.getValue();
        assertThat(employeeArgumentCaptorValue).isEqualTo(employee);
        assertThat(employeeId).isEqualTo(employee.getEmployeeId());

        verify(employeeMapper, times(1)).toEmployee(request);
        verify(employeeRepository, times(1)).existsByNin(nin);
        verify(employeeRepository, times(1)).save(employee);

    }
    @Test
    void itShouldNotAddEmployeeWhenNinExists() {
        // Given an employee and nin
        var faker = new Faker();
        String nin = faker.regexify("[C-W0-9]{14}");

        Employee employee = new Employee(
                1,
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().emailAddress(),
                Gender.FEMALE,
                LocalDateTime.now(),
                nin,
                faker.phoneNumber().subscriberNumber(10),
                faker.regexify("[098][#$%]"),
                Role.USER,
                LocalDate.of(1994,4,6),
                Collections.emptyList(),
                Collections.emptyList(),
                faker.number().randomDouble(2,2,10),
                Collections.emptyList());

        // ... given an employee creation request
        EmployeeCreationRequest request = new EmployeeCreationRequest(employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getGender(),
                employee.getJoinedDate(),
                employee.getNin(),
                employee.getPhoneNumber(),
                employee.getPassword(),
                employee.getRole(),
                employee.getDateOfBirth(),
                employee.getSalary(),
                employee.getAcademicQualifications());

        // Mock services
        when(employeeRepository.existsByNin(nin)).thenReturn(true);

        // When
        assertThatThrownBy(() -> underTest.addEmployee(request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("The employee already exists in the system");

        // Then
        then(employeeRepository).should(never()).save(any());
        verify(employeeRepository, times(1)).existsByNin(nin);

    }


    @Test
    void itShouldFindAllEmployees() {
        // Given
        Pageable pageable = PageRequest.of(1, 20, Sort.by("createdDate").descending());
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

        Employee employee2 = new Employee(
                2,
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().emailAddress(),
                Gender.MALE,
                LocalDateTime.now(),
                faker.regexify("[C-W0-9]{14}"),
                faker.phoneNumber().subscriberNumber(10),
                faker.regexify("[098][#$%]"),
                Role.ADMIN,
                LocalDate.of(1964,4,6),
                Collections.emptyList(),
                Collections.emptyList(),
                faker.number().randomDouble(2,2,10),
                Collections.emptyList());

        List<Employee> employees = List.of(employee1, employee2);
        Page<Employee> employeePage = new PageImpl<>(employees, pageable, employees.size());

        given(employeeRepository.findAll(pageable)).willReturn(employeePage);
        given(employeeMapper.toEmployeeResponse(employee1)).willReturn(
                new EmployeeResponse(
                        employee1.getEmployeeId(),
                        employee1.getFirstName(),
                        employee1.getLastName(),
                        employee1.getEmail(),
                        employee1.getGender(),
                        employee1.getJoinedDate(),
                        employee1.getNin(),
                        employee1.getPhoneNumber(),
                        employee1.getPassword(),
                        employee1.getRole(),
                        employee1.getDateOfBirth(),
                        employee1.getPayments(),
                        employee1.getOrders(),
                        employee1.getSalary(),
                        employee1.getAcademicQualifications()
                )
        );
        given(employeeMapper.toEmployeeResponse(employee2)).willReturn(
                new EmployeeResponse(
                        employee2.getEmployeeId(),
                        employee2.getFirstName(),
                        employee2.getLastName(),
                        employee2.getEmail(),
                        employee2.getGender(),
                        employee2.getJoinedDate(),
                        employee2.getNin(),
                        employee2.getPhoneNumber(),
                        employee2.getPassword(),
                        employee2.getRole(),
                        employee2.getDateOfBirth(),
                        employee2.getPayments(),
                        employee2.getOrders(),
                        employee2.getSalary(),
                        employee2.getAcademicQualifications()
                )
        );

        // When
        PageResponse<EmployeeResponse> response = underTest.findAllEmployees(1, 20);

        // Then
        assertThat(response.getContent()).hasSize(2);
        assertThat(response.getPageNumber()).isEqualTo(employeePage.getNumber());
        assertThat(response.getPageSize()).isEqualTo(employeePage.getSize());
        assertThat(response.getTotalElements()).isEqualTo(employeePage.getTotalElements());
        assertThat(response.getTotalPages()).isEqualTo(employeePage.getTotalPages());
        assertThat(response.isFirst()).isEqualTo(employeePage.isFirst());
        assertThat(response.isLast()).isEqualTo(employeePage.isLast());

        verify(employeeRepository, times(1)).findAll(pageable);

    }

    @Test
    void itShouldFindEmployeeById() {
        // Given
        var faker = new Faker();
        int id = 1;
        Employee employee = new Employee(
                id,
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

        given(employeeRepository.findById(id)).willReturn(Optional.of(employee));
        EmployeeResponse expectedResponse = new EmployeeResponse(
                employee.getEmployeeId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getGender(),
                employee.getJoinedDate(),
                employee.getNin(),
                employee.getPhoneNumber(),
                employee.getPassword(),
                employee.getRole(),
                employee.getDateOfBirth(),
                employee.getPayments(),
                employee.getOrders(),
                employee.getSalary(),
                employee.getAcademicQualifications()

        );
        given(employeeMapper.toEmployeeResponse(employee)).willReturn(expectedResponse);

        // When
        EmployeeResponse employeeResponse = underTest.findEmployeeById(id);


        // Then
        assertThat(employeeResponse).isEqualTo(expectedResponse);
        verify(employeeRepository, times(1)).findById(id);
        verify(employeeMapper, times(1)).toEmployeeResponse(employee);
    }

    @Test
    void itShouldThrowExceptionWhenFindEmployeeByIdDoesNotFindId() {
        // Given
        int id = -1;

        given(employeeRepository.findById(id)).willReturn(Optional.empty());

        // When
        assertThatThrownBy(() -> underTest.findEmployeeById(id))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("No Employee found with that id");
    }

    @Test
    void itShouldUpdateEmployee() {
        // Given
        var faker = new Faker();
        String nin = faker.regexify("[C-W0-9]{14}") ;
        Employee employee = new Employee(
                1,
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().emailAddress(),
                Gender.FEMALE,
                LocalDateTime.now(),
                nin,
                faker.phoneNumber().subscriberNumber(10),
                faker.regexify("[098][#$%]"),
                Role.USER,
                LocalDate.of(1994,4,6),
                Collections.emptyList(),
                Collections.emptyList(),
                faker.number().randomDouble(2,2,10),
                Collections.emptyList());

        Employee updatedEmployee = new Employee(
                1,
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().emailAddress(),
                Gender.FEMALE,
                LocalDateTime.now(),
                nin,
                faker.phoneNumber().subscriberNumber(10),
                faker.regexify("[098][#$%]"),
                Role.ADMIN,
                LocalDate.of(1995,4,6),
                Collections.emptyList(),
                Collections.emptyList(),
                faker.number().randomDouble(2,2,10),
                Collections.emptyList());

        // ... given employee update request
        EmployeeUpdateRequest updateRequest = new EmployeeUpdateRequest(
                employee.getEmployeeId(),
                updatedEmployee.getFirstName(),
                updatedEmployee.getLastName(),
                updatedEmployee.getEmail(),
                updatedEmployee.getGender(),
                updatedEmployee.getJoinedDate(),
                updatedEmployee.getNin(),
                updatedEmployee.getPhoneNumber(),
                updatedEmployee.getPassword(),
                updatedEmployee.getRole(),
                updatedEmployee.getDateOfBirth(),
                updatedEmployee.getSalary(),
                updatedEmployee.getAcademicQualifications()
        );

        given(employeeRepository.existsByNin(nin)).willReturn(true);
        given(employeeMapper.fromUpdateRequest(updateRequest)).willReturn(updatedEmployee);
        given(employeeRepository.save(updatedEmployee)).willReturn(updatedEmployee);

        // When
        Integer updated = underTest.updateEmployee(updateRequest);

        // Then
        then(employeeRepository).should().save(updatedEmployee);
        assertThat(updated).isEqualTo(updatedEmployee.getEmployeeId());

    }

    @Test
    void itShouldThrowExceptionWhenUpdateEmployeeDoesNotExist() {
        // Given
        var faker = new Faker();
        String nin = faker.regexify("[C-W0-9]{14}") ;
        Employee employee = new Employee(
                1,
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().emailAddress(),
                Gender.FEMALE,
                LocalDateTime.now(),
                nin,
                faker.phoneNumber().subscriberNumber(10),
                faker.regexify("[098][#$%]"),
                Role.USER,
                LocalDate.of(1994,4,6),
                Collections.emptyList(),
                Collections.emptyList(),
                faker.number().randomDouble(2,2,10),
                Collections.emptyList());

        Employee updatedEmployee = new Employee(
                1,
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().emailAddress(),
                Gender.FEMALE,
                LocalDateTime.now(),
                faker.regexify("[C-W0-9]{14}") ,
                faker.phoneNumber().subscriberNumber(10),
                faker.regexify("[098][#$%]"),
                Role.ADMIN,
                LocalDate.of(1995,4,6),
                Collections.emptyList(),
                Collections.emptyList(),
                faker.number().randomDouble(2,2,10),
                Collections.emptyList());

        // ... given employee update request
        EmployeeUpdateRequest updateRequest = new EmployeeUpdateRequest(
                employee.getEmployeeId(),
                updatedEmployee.getFirstName(),
                updatedEmployee.getLastName(),
                updatedEmployee.getEmail(),
                updatedEmployee.getGender(),
                updatedEmployee.getJoinedDate(),
                updatedEmployee.getNin(),
                updatedEmployee.getPhoneNumber(),
                updatedEmployee.getPassword(),
                updatedEmployee.getRole(),
                updatedEmployee.getDateOfBirth(),
                updatedEmployee.getSalary(),
                updatedEmployee.getAcademicQualifications()
        );

        given(employeeRepository.existsByNin(nin)).willReturn(false);

        // When
        assertThatThrownBy(() -> underTest.updateEmployee(updateRequest))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("The Employee does not exist");

        // Then
        then(employeeRepository).should(never()).save(any());

    }

    @Test
    void itShouldRemoveEmployeeById() {
        // Given
        int id = 1;

        given(employeeRepository.existsById(id)).willReturn(true);

        // When
        underTest.removeEmployeeById(id);

        // Then
        verify(employeeRepository).deleteById(id);

    }

    @Test
    void itShouldThrowWhenRemoveByIdDoesNotFindId() {
        // Given
        int id = -1;

        given(employeeRepository.existsById(id)).willReturn(false);

        // When
        assertThatThrownBy(()-> underTest.removeEmployeeById(id))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("No Employee found with that id");

        // Then
        verify(employeeRepository, never()).deleteById(id);

    }
}