package com.example.backend.controller;

import com.example.backend.dto.EmployeeDTO;
import com.example.backend.service.EmployeeService;
import com.example.backend.util.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/ems")
public class EmployeeController {

   private EmployeeService employeeService;

   @Autowired
   public EmployeeController(EmployeeService employeeService) {
      this.employeeService = employeeService;
   }


   @PostMapping("/")
   public CommonResponse saveEmployee(@RequestBody EmployeeDTO employeeDTO){

       return employeeService.saveEmployee(employeeDTO);

   }

   @PutMapping("/update/{id}")
   public CommonResponse updateEmployee(@PathVariable String id,@RequestBody EmployeeDTO employeeDTO){

      return employeeService.updateEmployee(id,employeeDTO);

   }

   @DeleteMapping("/{id}")
   public CommonResponse deleteEmployee(@PathVariable String id){

      return employeeService.deleteEmployee(id);

   }

   @GetMapping("/")
   public CommonResponse getAll(){
      return employeeService.getAll();
   }

   @GetMapping("/{id}")
   public CommonResponse getById(@PathVariable String id){
      return employeeService.getById(id);
   }



}
