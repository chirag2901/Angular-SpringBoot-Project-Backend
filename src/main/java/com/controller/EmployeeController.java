package com.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dao.EmployeeRepository;
import com.exception.ResourceNotFoundExpception;
import com.model.Employee;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class EmployeeController {
	@Autowired
	 EmployeeRepository employeeRepository;
	
	//we develop get all employees restApi
	@GetMapping("/employees")
	public List<Employee> getAllEmployee(){
		return employeeRepository.findAll();
		
	} 
	
	@PostMapping("/employees")
	public Employee addEmployee(@RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}
	
	//get employee by id restApi
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable long id) {
		Employee employee = employeeRepository.findById(id)
		.orElseThrow(()-> new ResourceNotFoundExpception("EmployeeNot exist with this id "+id));
		return new ResponseEntity<Employee>(employee,HttpStatus.OK);
	}
	
	//update employee RestApi
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id,@RequestBody Employee employeeDetails){
		Employee employee = employeeRepository.findById(id)
		.orElseThrow(()-> new ResourceNotFoundExpception("EmployeeNot exist with this id "+id));
		employee.setFirstName(employeeDetails.getFirstName());
		employee.setLastName(employeeDetails.getLastName());
		employee.setEmail(employeeDetails.getEmail());
		Employee updatedEmployee = employeeRepository.save(employee);
		return new ResponseEntity<Employee>(updatedEmployee,HttpStatus.OK);
	}
	
	//detele employeeRest Employee
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
		Employee employee = employeeRepository.findById(id)
		.orElseThrow(()-> new ResourceNotFoundExpception("EmployeeNot exist with this id "+id));
		employeeRepository.delete(employee);
		Map<String,Boolean> response = new HashMap<>();
		response.put("deleted",Boolean.TRUE);
		
		return new ResponseEntity<Map<String,Boolean>>(response,HttpStatus.OK);
	}
	
}
