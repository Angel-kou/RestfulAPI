package com.example.employee.restfulapi;

import com.example.employee.restfulapi.controller.CompanyController;
import com.example.employee.restfulapi.controller.EmployeeController;
import com.example.employee.restfulapi.entity.Company;
import com.example.employee.restfulapi.entity.Employee;
import com.example.employee.restfulapi.repository.CompanyRepository;
import com.example.employee.restfulapi.repository.EmployeeRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class RestfulApiApplicationTests {
    @Test
    public void contextLoads() {
    }

    private MockMvc companyMvc;
    private MockMvc employeeMvc;
    @Mock
    private CompanyRepository companyRepository;
    private EmployeeRepository employeeRepository;

    @Before
    public void setUp() throws Exception {
        companyRepository = mock(CompanyRepository.class);
        employeeRepository = mock(EmployeeRepository.class);
        companyMvc = MockMvcBuilders.standaloneSetup(new CompanyController(companyRepository,employeeRepository)).build();
        employeeMvc =  MockMvcBuilders.standaloneSetup(new EmployeeController(employeeRepository)).build();
    }

    @Test
    public void testCompanyController() throws Exception {
        // 测试CompanyController
        RequestBuilder request = null;

        request = get("/companies");
        companyMvc.perform(request)
                .andExpect(status().isOk());

        request = get("/companies/1");
        companyMvc.perform(request)
                .andExpect(status().isOk());

        request = get("/companies/1/employees");
        companyMvc.perform(request)
                .andExpect(status().isOk());

        request = get("/companies/page/1/pageSize/5");
        companyMvc.perform(request)
                .andExpect(status().isOk());

        request = post("/companies")
                .param("companyName", "测试1")
                .param("employeesNumber", "20");
        companyMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("{\"companyName\":\"测试1\",\"employeesNumber\":20,\"empolyees\":[]}")));

        Company company = new Company("baidu",1000);
        when(companyRepository.findById(new Long((long)1))).thenReturn(Optional.ofNullable(company));
        request = put("/companies/1")
                .param("companyName", "测试2")
                .param("employeesNumber", "30");
        companyMvc.perform(request)
                .andExpect(status().isOk());

        request = delete("/companies/1");
        companyMvc.perform(request)
                .andExpect(content().string(equalTo("success")));

    }

    @Test
    public void testEmployeeController() throws Exception {
        RequestBuilder request = null;

        request = get("/employees");
        employeeMvc.perform(request)
                .andExpect(status().isOk());

        request = get("/employees/1");
        employeeMvc.perform(request)
                .andExpect(status().isOk());

        request = get("/employees/page/1/pageSize/5");
        employeeMvc.perform(request)
                .andExpect(status().isOk());

        request = get("/employees/male");
        employeeMvc.perform(request)
                .andExpect(status().isOk());

        request = post("/employees")
                .param("name", "测试1")
                .param("age", "20")
                .param("gender", "male")
                .param("salary", "4000")
                .param("companyId", "1");
        employeeMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("{\"id\":null,\"name\":\"测试1\",\"age\":20,\"gender\":\"male\",\"salary\":4000}")));

        Employee employee = new Employee("测试2",30,"female",6000, (long) 1);
        when(employeeRepository.findById(new Long((long)1))).thenReturn(Optional.ofNullable(employee));
        request = put("/employees/1")
                .param("name", "测试2")
                .param("age", "30")
                .param("gender", "female")
                .param("salary", "6000")
                .param("companyId", "1");
        employeeMvc.perform(request)
                .andExpect(status().isOk());


        request = delete("/employees/1");
        employeeMvc.perform(request)
                .andExpect(content().string(equalTo("success")));
    }
}
