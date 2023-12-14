/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.warehouse.dao;

import com.mycompany.warehouse.data.Customer;
import com.mycompany.warehouse.data.Employee;
import com.mycompany.warehouse.data.Order;
import com.mycompany.warehouse.data.Shipper;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zivad
 */
public class OrderDAO {

    private static OrderDAO instance = new OrderDAO();
    
    
    public static OrderDAO getInstance(){
        return instance;
    }
    
    
    public Order find(Connection con, int id) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Order order = null;
        try {
            ps = con.prepareStatement("SELECT * FROM Orders WHERE OrderId=?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Customer customer = CustomersDAO.getInstance().find(con,rs.getInt("CustomerId"));
                Shipper shipper = ShippersDAO.getInstance().find(con, rs.getInt("ShipperId"));
                Employee employee = EmployeeDAO.getInstance().find(con, rs.getInt("EmployeeId"));
                order = new Order(rs.getInt("OrderId"),rs.getDate("OrderDate"),shipper,customer,employee);
                
            }
        } finally {
            ResourcesManager.closeResources(rs, ps);
        }
        return order;
    }
    
    
    public void delete(Connection con, int id) throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("DELETE FROM Orders WHERE OrderId=?");
            ps.setInt(1, id);
            ps.executeUpdate();
            
        } finally {
            ResourcesManager.closeResources(null, ps);
        }
    }
    public void deleteByCustomer(Connection con, int id) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement("SELECT FROM ORDERS WHERE CustomerId=?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (!rs.next())
                return;
            ps = con.prepareStatement("DELETE FROM Orders WHERE CustomerId=?");
            ps.setInt(1, id);
            ps.executeUpdate();
            OrderDetailsDAO.getInstance().deleteByOrder(con, id);
        } finally {
            ResourcesManager.closeResources(null, ps);
        }
    }
    public void deleteByEmployee(Connection con, int id) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement("SELECT FROM ORDERS WHERE EmployeeId=?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (!rs.next())
                return;
            ps = con.prepareStatement("DELETE FROM Orders WHERE EmployeeId=?");
            ps.setInt(1, id);
            ps.executeUpdate();
            OrderDetailsDAO.getInstance().deleteByOrder(con, id);
        } finally {
            ResourcesManager.closeResources(null, ps);
        }
    }
    public void deleteByShipper(Connection con, int id) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement("SELECT FROM ORDERS WHERE ShipperId=?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (!rs.next())
                return;
            ps = con.prepareStatement("DELETE FROM Orders WHERE ShipperId=?");
            ps.setInt(1, id);
            ps.executeUpdate();
            OrderDetailsDAO.getInstance().deleteByOrder(con, id);
        } finally {
            ResourcesManager.closeResources(null, ps);
        }
    }
    
    public void update(Connection con, Order order) throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("UPDATE Orders SET OrderDate=?, ShipperId=?, CustomerId=?, EmployeeId=? WHERE OrderId=?");
            ps.setDate(1, (Date) order.getOrderDate());
            ps.setInt(2, order.getShipper().getShipperId());
            ps.setInt(3, order.getCustomer().getCustomerId());
            ps.setInt(4, order.getEmployee().getEmployeeId());
            ps.setInt(5, order.getOrderId());
            ps.executeUpdate();
        } finally {
            ResourcesManager.closeResources(null, ps);
        }
    }

    
    public int create(Connection con, Order order) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        int id = -1;
        try {
            ps = con.prepareStatement("INSERT INTO Orders(OrderDate, ShipperId, CustomerId, EmployeeId) VALUES(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setDate(1, (Date) order.getOrderDate());
            
            Shipper shipper = ShippersDAO.getInstance().find(con, order.getShipper().getShipperId());
            Customer customer = CustomersDAO.getInstance().find(con, order.getCustomer().getCustomerId());
            Employee employee = EmployeeDAO.getInstance().find(con, order.getEmployee().getEmployeeId());
            if (shipper == null) {
                throw new SQLException("Shipper " + order.getShipper() + " doesn't exist in database.");
            }
            else if(customer == null){
                throw new SQLException("Customer " + order.getCustomer() + " doesn't exist in database.");
            }
            else if(employee == null){
                throw new SQLException("Employee " + order.getEmployee() + " doesn't exist in database.");
            }
            
            ps.setInt(2, shipper.getShipperId());
            ps.setInt(3, customer.getCustomerId());
            ps.setInt(4, employee.getEmployeeId());
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            rs.next();
            id = rs.getInt(1);
        } finally {
            ResourcesManager.closeResources(rs, ps);
        }
        return id;
    }
    
    
    public List<Order> findAll(Connection con) throws SQLException{
        PreparedStatement ps = null;
        ResultSet rs = null;
        List <Order> orders = new ArrayList<>();
        try{
            ps = con.prepareStatement("SELECT * FROM Orders");

            rs = ps.executeQuery();
            while (rs.next()) {
                Shipper shipper = ShippersDAO.getInstance().find(con, rs.getInt("ShipperId"));
                Customer customer = CustomersDAO.getInstance().find(con, rs.getInt("CustomerId"));
                Employee employee = EmployeeDAO.getInstance().find(con, rs.getInt("EmployeeId"));

                orders.add(new Order(rs.getInt("OrderId"), rs.getDate("OrderDate"), 
                        shipper, customer, employee));
            }
        }
        finally{
            ResourcesManager.closeResources(rs, ps);
        }
        return orders;
    }
    
}
