package com.example.employee.restfulapi.controller;

import com.example.employee.restfulapi.entity.Company;
import com.example.employee.restfulapi.entity.Employee;
import com.example.employee.restfulapi.repository.CompanyRepository;
import com.example.employee.restfulapi.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/employees")
public class EmployeeController {


    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    //在此处完成Employee API
    //获取company列表
    @RequestMapping(value="", method= RequestMethod.GET)
    public List<Employee> getEmployeeList() {
        List<Employee>  employeeList = employeeRepository.findAll();
        return employeeList;
    }


    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public Employee getEmployee(@PathVariable Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @RequestMapping(value="/page/{page}/pageSize/{pageSize}", method=RequestMethod.GET)
    public Page<Employee> getEmployeeBasePage(@PathVariable int page,@PathVariable int pageSize) {
        Pageable pageable = PageRequest.of(page-1, pageSize, Sort.Direction.ASC, "id");

        Page<Employee> employeeList = employeeRepository.findAll(pageable);

        return employeeList;
    }

    @RequestMapping(value="/male", method=RequestMethod.GET)
    public List<Employee> getEmployeeByGender() {
        return employeeRepository.findByGender();
    }

    @RequestMapping(value="", method=RequestMethod.POST)
    public Employee postEmployee(@ModelAttribute Employee employee) {
        this.employeeRepository.save(employee);
        return employee;
    }

    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public Employee putEmployee(@PathVariable Long id, @ModelAttribute Employee employee) {
        Employee employee1 = employeeRepository.findById(id).orElse(null);
        employee1.setName(employee.getName());
        employee1.setAge(employee.getAge());
        employee1.setGender(employee.getGender());
        employee1.setSalary(employee.getSalary());
        employee1.setCompanyId(employee.getCompanyId());
        employeeRepository.save(employee1);
        return employee1;
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public String deleteEmployee(@PathVariable Long id) {
        employeeRepository.delete(employeeRepository.findById(id).orElse(null));
        return "success";
    }

}
