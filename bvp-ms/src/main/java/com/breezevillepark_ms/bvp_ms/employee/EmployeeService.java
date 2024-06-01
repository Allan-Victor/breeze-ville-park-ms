package com.breezevillepark_ms.bvp_ms.employee;

import com.breezevillepark_ms.bvp_ms.common.PageResponse;
import com.breezevillepark_ms.bvp_ms.exception.DuplicateResourceException;
import com.breezevillepark_ms.bvp_ms.management.EmployeeCreationRequest;
import com.breezevillepark_ms.bvp_ms.management.EmployeeMapper;
import com.breezevillepark_ms.bvp_ms.management.EmployeeResponse;
import com.breezevillepark_ms.bvp_ms.management.EmployeeUpdateRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository repo;
    private final EmployeeMapper employeeMapper;

    public Integer addEmployee(EmployeeCreationRequest creationRequest) {
        // check if employee already exists in db
        if (repo.existsByNin(creationRequest.nin())){
            throw new DuplicateResourceException("The employee already exists in the system");
        }
        Employee newEmployee = employeeMapper.toEmployee(creationRequest);
        return repo.save(newEmployee).getEmployeeId();
    }

    public PageResponse<EmployeeResponse> findAllEmployees(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Employee> employees = repo.findAll(pageable);
        List<EmployeeResponse> responseList = employees.stream()
                .map(employeeMapper::toEmployeeResponse)
                .toList();
        return new PageResponse<>(
                responseList,
                employees.getNumber(),
                employees.getSize(),
                employees.getTotalElements(),
                employees.getTotalPages(),
                employees.isFirst(),
                employees.isLast()
        );
    }

    public EmployeeResponse findEmployeeById(Integer empId) {
        return repo.findById(empId)
                .map(employeeMapper::toEmployeeResponse)
                .orElseThrow(()-> new EntityNotFoundException("No Employee found with that id"));

    }

    public Integer updateEmployee(EmployeeUpdateRequest employeeUpdateRequest) {
        // check if employee to update is in the db
        if (!repo.existsByNin(employeeUpdateRequest.nin())){
            throw new EntityNotFoundException("The Employee does not exist");
        }
        Employee updateEmployee = employeeMapper.fromUpdateRequest(employeeUpdateRequest);
        return repo.save(updateEmployee).getEmployeeId();
    }

    public void removeEmployeeById(Integer empId) {
        // check if employee exists in db
        if (!repo.existsById(empId)){
            throw  new EntityNotFoundException("No Employee found with that id");
        }
        repo.deleteById(empId);
    }

}
