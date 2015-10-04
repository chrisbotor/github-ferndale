/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.dao;

import com.homeportal.bean.EmployeeBean;
import com.homeportal.model.Employee;
import java.util.List;

/**
 *
 * @author Peter
 */
public interface IEmployee {
    
    List<EmployeeBean> getEmployees();
    List<EmployeeBean> getEmployees(int userId,String status);
    List<Employee> getEmployees(int userId, int houseId);
    void deleteEmployees(int id);
    Employee saveEmployees(Employee employee);
    Employee updateEmployee(Employee employee);
    Employee updateEmployeeViaPortal(Employee employee);
}
