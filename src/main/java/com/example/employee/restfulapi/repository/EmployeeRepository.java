package com.example.employee.restfulapi.repository;

import com.example.employee.restfulapi.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>  {

    @Query("select e from Employee e where e.companyId = :companyId")
    List<Employee> findByCompanyId(@Param("companyId") Long companyId);


    @Query("select e from Employee e where e.gender = 'male'")
    List<Employee> findByGender();
}
