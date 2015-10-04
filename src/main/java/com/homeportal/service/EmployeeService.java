/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.service;

import com.homeportal.bean.EmployeeBean;
import com.homeportal.dao.impl.EmployeeDaoImpl;
import com.homeportal.model.Employee;
import com.homeportal.util.EmployeeUtil;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Peter
 */
@Service
public class EmployeeService {

    private EmployeeDaoImpl employeeDaoImpl;
    private EmployeeUtil employeeUtil;
    
    /**
     * Get all contacts
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<EmployeeBean> getEmployeesList() {
        return employeeDaoImpl.getEmployees();
    }

    /**
     * Get all contacts
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<EmployeeBean> getEmployeesList(int userId,String status) {
        return employeeDaoImpl.getEmployees(userId, status);
    }
    
    /**
     * Get all contacts
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<Employee> getEmployeesList(int userId, int houseId) {
        return employeeDaoImpl.getEmployees(userId, houseId);
    }

    /**
     * Create new Owner/Owners
     *
     * @param data - json data from request
     * @return created contacts
     */
    @Transactional
    public List<Employee> create(Object data) {

        List<Employee> newEmployee = new ArrayList<Employee>();
        List<Employee> list = employeeUtil.getEmployeesFromRequest(data);
        for (Employee v : list) {
            newEmployee.add(employeeDaoImpl.saveEmployees(v));
        }

        return newEmployee;
    }

    /**
     * Update contact/contacts
     *
     * @param data - json data from request
     * @return updated contacts
     */
    @Transactional
    public List<Employee> update(Object data) {

        List<Employee> returnEmployees = new ArrayList<Employee>();
        List<Employee> updatedEmployees = employeeUtil.getEmployeesFromRequest(data);
        for (Employee v : updatedEmployees) {
            returnEmployees.add(employeeDaoImpl.updateEmployee(v));
        }
        return returnEmployees;
    }
    
    /**
     * Update contact/contacts
     *
     * @param data - json data from request
     * @return updated contacts
     */
    @Transactional
    public List<Employee> updateViaPortal(Object data) {

        List<Employee> returnEmployees = new ArrayList<Employee>();
        List<Employee> updatedEmployees = employeeUtil.getEmployeesFromRequest(data);
        for (Employee v : updatedEmployees) {
            returnEmployees.add(employeeDaoImpl.updateEmployeeViaPortal(v));
        }
        return returnEmployees;
    }

    /**
     * Delete contact/contacts
     *
     * @param data - json data from request
     */
    @Transactional
    public void delete(Object data) {

        //it is an array - have to cast to array object
        if (data.toString().indexOf('[') > -1) {
            List<Integer> deleteOwners = employeeUtil.getListIdFromJSON(data);
            for (Integer id : deleteOwners) {
                employeeDaoImpl.deleteEmployees(id);
            }

        } else { //it is only one object - cast to object/bean
            Integer id = Integer.parseInt(data.toString());
            employeeDaoImpl.deleteEmployees(id);
        }
    }

    /**
     * @param employeeDaoImpl the employeeDaoImpl to set
     */
    @Autowired
    public void setEmployeeDaoImpl(EmployeeDaoImpl employeeDaoImpl) {
        this.employeeDaoImpl = employeeDaoImpl;
    }

    /**
     * @param employeeUtil the employeeUtil to set
     */
    @Autowired
    public void setEmployeeUtil(EmployeeUtil employeeUtil) {
        this.employeeUtil = employeeUtil;
    }
}
