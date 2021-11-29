package com.example.backend.service;

import com.example.backend.dto.EmployeeDTO;
import com.example.backend.entity.Employee;
import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.repository.EmployeeRepository;
import com.example.backend.util.CommonResponse;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;
    private ModelMapper modelMapper;
    private final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;

    }


    public CommonResponse saveEmployee(EmployeeDTO employeeDTO) {

        CommonResponse commonResponse = new CommonResponse();

        try {

            Employee employee = modelMapper.map(employeeDTO,Employee.class);
            employeeRepository.save(employee);
            commonResponse.setStatus(true);

        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("resource not found:"+e);
        }
       return commonResponse;
    }


    public CommonResponse updateEmployee(String id,EmployeeDTO employeeDTO) {

//        CommonResponse commonResponse = new CommonResponse();
//
//        try {
//
//            Employee employee = employeeRepository.findById(Long.valueOf(employeeDTO.getId())).get();
//            employee = modelMapper.map(employeeDTO,Employee.class);
//            employeeRepository.save(employee);
//            commonResponse.setStatus(true);
//
//        }catch (ResourceNotFoundException e){
//
//            throw new ResourceNotFoundException("resourse not found:"+e);
//
//        }
//        return commonResponse;

        CommonResponse commonResponse= new CommonResponse();
        try {


            Employee employee = employeeRepository.getById(Long.valueOf(id));


            employee.setFirstName(employeeDTO.getFirstName());
            employee.setLastName(employeeDTO.getLastName());
            employee.setEmail(employeeDTO.getEmail());

            employeeRepository.save(employee);

            commonResponse.setStatus(true);




        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("resource not found:"+e);
        }

        return commonResponse;
    }

    public CommonResponse deleteEmployee(String id) {

        CommonResponse commonResponse = new CommonResponse();

        try {

            Employee employee = employeeRepository.getById(Long.valueOf(id));
            employeeRepository.delete(employee);
            commonResponse.setStatus(true);

        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("resource not found:"+e);
        }
        return commonResponse;
    }

    public CommonResponse getAll() {

        CommonResponse commonResponse =new CommonResponse();
        List<EmployeeDTO> employeeDTOS = null;
        try {

            List<Employee> employees = employeeRepository.findAll();
            employeeDTOS = castEmployeesIntoEmployeeDTOS(employees);
            commonResponse.setPayload(Collections.singletonList(employeeDTOS));
            commonResponse.setStatus(true);

        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("not found:"+e);
        }
        return commonResponse;
    }

    private List<EmployeeDTO> castEmployeesIntoEmployeeDTOS(List<Employee> employees) {

          List<EmployeeDTO> employeeDTOS = new ArrayList<>();

          for(Employee employee : employees){

              EmployeeDTO employeeDTO;
              employeeDTO = modelMapper.map(employee,EmployeeDTO.class);
              employeeDTOS.add(employeeDTO);

          }
         return employeeDTOS;
    }

    public CommonResponse getById(String id) {

        CommonResponse commonResponse = new CommonResponse();
        EmployeeDTO employeeDTO = null;

        try {

            Employee employee = employeeRepository.findById(Long.valueOf(id)).get();
            employeeDTO = modelMapper.map(employee,EmployeeDTO.class);
            commonResponse.setPayload(Collections.singletonList(employeeDTO));
            commonResponse.setStatus(true);

        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("resource not found:"+e);
        }

        return commonResponse;
    }

    public Employee findById(String id){
        return employeeRepository.findById(Long.valueOf(id)).get();
    }

}
