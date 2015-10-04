/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homeportal.util;

import com.homeportal.model.Employee;
import java.util.ArrayList;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;

/**
 *
 * @author Peter
 */
@Component
public class EmployeeUtil {

    public List<Employee> getEmployeesFromRequest(Object data) {

        List<Employee> list;

        //it is an array - have to cast to array object
        if (data.toString().indexOf('[') > -1) {

            list = getListEmployeesFromJSON(data);

        } else { //it is only one object - cast to object/bean

            Employee employee = getEmployeeFromJSON(data);

            list = new ArrayList<Employee>();
            list.add(employee);
        }

        return list;
    }

    /**
     * Transform json data format into Employee object
     *
     * @param data - json data from request
     * @return
     */
    private Employee getEmployeeFromJSON(Object data) {
        JSONObject jsonObject = JSONObject.fromObject(data);
        Employee newEmployee = (Employee) JSONObject.toBean(jsonObject, Employee.class);
        return newEmployee;
    }

    /**
     * Transform json data format into list of Employee objects
     *
     * @param data - json data from request
     * @return
     */
    @SuppressWarnings("unchecked")
    private List<Employee> getListEmployeesFromJSON(Object data) {
        JSONArray jsonArray = JSONArray.fromObject(data);
        List<Employee> newEmployees = (List<Employee>) JSONArray.toCollection(jsonArray, Employee.class);
        return newEmployees;
    }

    /**
     * Tranform array of numbers in json data format into list of Integer
     *
     * @param data - json data from request
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Integer> getListIdFromJSON(Object data) {
        JSONArray jsonArray = JSONArray.fromObject(data);
        List<Integer> idEmployees = (List<Integer>) JSONArray.toCollection(jsonArray, Integer.class);
        return idEmployees;
    }
}
