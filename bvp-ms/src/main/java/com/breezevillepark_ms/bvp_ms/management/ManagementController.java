package com.breezevillepark_ms.bvp_ms.management;

import com.breezevillepark_ms.bvp_ms.common.PageResponse;
import com.breezevillepark_ms.bvp_ms.employee.EmployeeService;
import com.breezevillepark_ms.bvp_ms.setting.Setting;
import com.breezevillepark_ms.bvp_ms.setting.SettingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("management")
@RequiredArgsConstructor
public class ManagementController {
    private final EmployeeService employeeService;
    private final SettingService settingService;


    @PostMapping("/employee/add")
    public ResponseEntity<Integer> createEmployee(@Valid @RequestBody EmployeeCreationRequest creationRequest){
        return ok(employeeService.addEmployee(creationRequest));
    }

    @GetMapping("/employees")
    public ResponseEntity<PageResponse<EmployeeResponse>> getAllEmployees(@RequestParam(name = "page",defaultValue = "0") int page,
                                                                      @RequestParam(name = "size", defaultValue = "10") int size){
        return ok(employeeService.findAllEmployees(page, size));
    }

    @GetMapping("/employee/{emp_id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable("emp_id") Integer empId){
        return ok(employeeService.findEmployeeById(empId));
    }
    @PatchMapping("/employee/update/")
    public ResponseEntity<Integer> updateEmployee(@Valid @RequestBody EmployeeUpdateRequest employeeUpdateRequest){
        return  ok(employeeService.updateEmployee(employeeUpdateRequest));
    }

    @DeleteMapping("/employee/delete/{emp_id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteEmployee(@PathVariable("emp_id") Integer empId){
        employeeService.removeEmployeeById(empId);
        return ResponseEntity.ok().build();
    }

    /*
    * For Settings
    * */

    @PostMapping("/setting/save")
    public ResponseEntity<Integer> saveSetting(@Valid @RequestBody SettingRequest settingRequest){
        return ok(settingService.saveSetting(settingRequest));
    }

    @GetMapping("/setting")
    public ResponseEntity<List<Setting>> viewSetting(){
        return ok(settingService.loadSetting());
    }

    @PutMapping("/setting/update/")
    public ResponseEntity<Integer> updateSetting(@Valid @RequestBody SettingUpdateRequest settingUpdateRequest){
        return  ok(settingService.updateSetting(settingUpdateRequest));
    }


}
