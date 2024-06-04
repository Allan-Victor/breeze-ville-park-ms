package com.breezevillepark_ms.bvp_ms.employee;

import com.breezevillepark_ms.bvp_ms.AbstractTestContainersUnitTest;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EmployeeRepositoryTest extends AbstractTestContainersUnitTest {
    @Autowired
    private EmployeeRepository underTest;

    @BeforeEach
    void setUp() {
    }

    @Test
    void itShouldFindByEmail() {
        //Given
        var faker = new Faker();
        String email = faker.internet().emailAddress() ;

        Employee employee = new Employee(
                1,
                faker.name().firstName(),
                faker.name().lastName(),
                email,
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
                Collections.emptyList()

        );
        underTest.save(employee);
        //When
        Optional<Employee> byEmail = underTest.findByEmail(email);
        //Then
        assertThat(byEmail).isPresent();
    }

    @Test
    void itShouldFindByEmailFailWhenNoEmailFound() {
        // Given
        var faker = new Faker();
        String email = faker.internet().emailAddress();
        // When
        Optional<Employee> optionalEmployee = underTest.findByEmail(email);
        // Then
        assertThat(optionalEmployee).isNotPresent();

    }


    @Test
    void itShouldExistsByNin() {
        //Given
        var faker = new Faker();
        String nin =  faker.regexify("[C-W0-9]{14}");

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
               Collections.emptyList()

       );
       underTest.save(employee);

        //When
        var actual = underTest.existsByNin(nin);

        //Then
        assertThat(actual).isTrue();

    }
    @Test
    void itShouldExistsByNinFailsWhenNinNotPresent() {
        //Given
        String nin = "CM234567890987";

        //When
        var actual = underTest.existsByNin(nin);

        //Then
        assertThat(actual).isFalse();

    }
}