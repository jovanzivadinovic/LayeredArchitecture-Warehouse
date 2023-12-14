/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.warehouse.service;

import com.mycompany.warehouse.dao.EmployeeDAO;
import com.mycompany.warehouse.dao.OrderDAO;
import com.mycompany.warehouse.dao.ResourcesManager;
import com.mycompany.warehouse.data.Employee;
import com.mycompany.warehouse.exception.WarehouseException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zivad
 */
public class EmployeeService {
    private static final EmployeeService instance = new EmployeeService();
    
    
    public static EmployeeService getInstance(){
        return instance;
    }
    
    public void addNewEmployee(Employee employee) throws WarehouseException{
        Connection con = null;
        try{
            con = ResourcesManager.getConnection();
            con.setAutoCommit(false);
            EmployeeDAO.getInstance().create(con, employee);
            con.commit();
            
        }
        catch(SQLException ex){
            ResourcesManager.rollbackTransactions(con);
            throw new WarehouseException("Failed to add new employee " + employee, ex);
        }
        finally{
            ResourcesManager.closeConnection(con);
        }
    }
    
    public Employee findEmployee(int id) throws WarehouseException{
        Connection con = null;
        try{
            con = ResourcesManager.getConnection();
            return EmployeeDAO.getInstance().find(con, id);
        }
        catch(SQLException ex){
            throw new WarehouseException("Failed to find employee with ID " + id, ex);
        }
        finally{
            ResourcesManager.closeConnection(con);
        }
    }
    
    
    public void deleteEmployee(int id) throws WarehouseException{
        Connection con = null;
        try {
            con = ResourcesManager.getConnection();
            con.setAutoCommit(false);

            Employee employee = EmployeeDAO.getInstance().find(con, id);
            if (employee != null) {
                EmployeeDAO.getInstance().delete(con, id);
                OrderDAO.getInstance().deleteByEmployee(con, id);
            }
            
            con.commit();
        } catch (SQLException ex) {
            ResourcesManager.rollbackTransactions(con);
            throw new WarehouseException("Failed to delete employee with ID " + id, ex);
        } finally {
            ResourcesManager.closeConnection(con);
        }
    }
    
    public void updateEmployee(Employee employee) throws WarehouseException {
        Connection con = null;
        try {
            con = ResourcesManager.getConnection();
            con.setAutoCommit(false);

            EmployeeDAO.getInstance().update(con, employee);

            con.commit();
        } catch (SQLException ex) {
            ResourcesManager.rollbackTransactions(con);
            throw new WarehouseException("Failed to update employee " + employee, ex);
        } finally {
            ResourcesManager.closeConnection(con);
        }
    }
    
    public List<Employee> findAllEmployees() throws WarehouseException{
        Connection con = null;
        List <Employee> employees = new ArrayList<>();
        try{
            con = ResourcesManager.getConnection();
            con.setAutoCommit(false);
            employees = EmployeeDAO.getInstance().findAll(con);
            return employees;
        }
        catch(SQLException ex){
            throw new WarehouseException("Failed to find all employees", ex);
        }
        finally{
            ResourcesManager.closeConnection(con);
        }
    }
}
