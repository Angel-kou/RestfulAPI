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

import java.util.*;

@RestController
@RequestMapping(value="/companies")
public class CompanyController {

    private CompanyRepository companyRepository;
    private EmployeeRepository employeeRepository;
    @Autowired
    public CompanyController(CompanyRepository companyRepository,EmployeeRepository employeeRepository) {
        this.companyRepository = companyRepository;
        this.employeeRepository = employeeRepository;
    }

    //在此处完成Company API

    //获取company列表
    @RequestMapping(value="", method= RequestMethod.GET)
    public List<Company> getCompanyList() {
        List<Company>  companyList = companyRepository.findAll();
        return companyList;
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public Company getCompany(@PathVariable Long id) {
        return companyRepository.findById(id).orElse(null);
    }

    @RequestMapping(value="/{id}/employees", method=RequestMethod.GET)
    public List<Employee> getEmployeesByCompanyId(@PathVariable Long id) {
        return employeeRepository.findByCompanyId(id);
    }


    @RequestMapping(value="/page/{page}/pageSize/{pageSize}", method=RequestMethod.GET)
    public Page<Company> getCompaniesBasePage(@PathVariable int page,@PathVariable int pageSize) {
        Pageable pageable = PageRequest.of(page-1, pageSize, Sort.Direction.ASC, "id");

        Page<Company> companyList = companyRepository.findAll(pageable);

        return companyList;
    }


    @RequestMapping(value="", method=RequestMethod.POST)
    public Company postCompany(@ModelAttribute Company company) {
        this.companyRepository.save(company);
        return company;
    }

    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public Company putCompany(@PathVariable Long id, @ModelAttribute Company company) {
        Company company1 = companyRepository.findById(id).orElse(null);
        company1.setCompanyName(company.getCompanyName());
        company1.setEmployeesNumber(company.getEmployeesNumber());
        companyRepository.save(company1);
        return company1;
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public String deleteCompany(@PathVariable Long id) {
        companyRepository.delete(companyRepository.findById(id).orElse(null));
        return "success";
    }
}
