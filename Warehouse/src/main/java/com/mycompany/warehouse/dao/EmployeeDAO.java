/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.warehouse.dao;

import com.mycompany.warehouse.data.Employee;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zivad
 */
public class EmployeeDAO {
    private static final EmployeeDAO instance = new EmployeeDAO();
    
    
    public static EmployeeDAO getInstance(){
        return instance;
    }
    
    public Employee find(Connection con, int id) throws SQLException{
        PreparedStatement ps = null;
        ResultSet rs = null;
        Employee employee = null;
        try {
            ps = con.prepareStatement("SELECT * FROM Employees WHERE EmployeeId=?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                employee = new Employee(rs.getInt("EmployeeId"), rs.getString("LastName"), rs.getString("FirstName"),
                        rs.getDate("BirthDate"));
            }
        } finally {
            ResourcesManager.closeResources(rs, ps);
        }
        return employee;
    }
    
    public void delete(Connection con, int id) throws SQLException{
        PreparedStatement ps = null;
        try{
            ps = con.prepareStatement("DELETE FROM Employees WHERE EmployeeId=?");
            ps.setInt(1, id);
            ps.executeUpdate();
            
        }
        finally{
            ResourcesManager.closeResources(null, ps);
        }
    }
    
    public void update(Connection con, Employee employee) throws SQLException{
        PreparedStatement ps = null;
        try{
            ps = con.prepareStatement("UPDATE Employees SET LastName=?, FirstName=?, BirthDate=? WHERE EmployeeId=?");
            ps.setString(1, employee.getLastName());
            ps.setString(2, employee.getFirstName());
            ps.setDate(3, (Date) employee.getBirthDate());
            ps.setInt(4, employee.getEmployeeId());
            ps.executeUpdate();
        }
        finally{
            ResourcesManager.closeResources(null, ps);
        }
    }
    
    
    public void create(Connection con, Employee employee) throws SQLException{
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = con.prepareStatement("INSERT INTO Employees(LastName, FirstName, BirthDate) VALUES(?,?,?)");
            ps.setString(1, employee.getLastName());
            ps.setString(2, employee.getFirstName());
            ps.setDate(3, (Date) employee.getBirthDate());
            ps.executeUpdate();
        }
        finally{
            ResourcesManager.closeResources(rs, ps);
        }
    }
    
    
    public List<Employee> findAll(Connection con) throws SQLException{
                PreparedStatement ps = null;
                ResultSet rs = null;
                List <Employee> employees = new ArrayList<>();
                try{
                    ps = con.prepareStatement("SELECT * FROM employees");
                   
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        employees.add(new Employee(rs.getInt("EmployeeId"), rs.getString("LastName"), 
                                rs.getString("FirstName"), rs.getDate("BirthDate")));
                    }
                }
                finally{
                    ResourcesManager.closeResources(rs, ps);
                }
                return employees;
    }
 
        
    
}
