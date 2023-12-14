/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.warehouse.dao;

import com.mycompany.warehouse.data.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zivad
 */
public class CustomersDAO {
    private static final CustomersDAO instance = new CustomersDAO();

    
    
    
    
    public static CustomersDAO getInstance() {
        return instance;
    }
    
    public Customer find(Connection con, int id) throws SQLException{
        PreparedStatement ps = null;
        ResultSet rs = null;
        Customer customer = null;
        try {
            ps = con.prepareStatement("SELECT * FROM Customers WHERE CustomerId=?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                customer = new Customer(rs.getInt("CustomerId"), rs.getString("CustomerName"), rs.getString("ContactPerson"),
                        rs.getString("Address"), rs.getString("City"),rs.getString("PostCode"),rs.getString("Country"));
            }
        } finally {
            ResourcesManager.closeResources(rs, ps);
        }
        return customer;
    }
    
    public void delete(Connection con, int id) throws SQLException{
        PreparedStatement ps = null;
        try{
            ps = con.prepareStatement("DELETE FROM Customers WHERE CustomerId=?");
            ps.setInt(1, id);
            ps.executeUpdate();
            
        }
        finally{
            ResourcesManager.closeResources(null, ps);
        }
    }
    
    public void update(Connection con, Customer customer) throws SQLException{
        PreparedStatement ps = null;
        try{
            ps = con.prepareStatement("UPDATE Customers SET CustomerName=?, ContactPerson=?, Address=?, City=?, PostCode=?, Country=? WHERE CustomerId=?");
            ps.setString(1, customer.getCustomerName());
            ps.setString(2, customer.getContactPerson());
            ps.setString(3, customer.getAddress());
            ps.setString(4, customer.getCity());
            ps.setString(5, customer.getPostCode());
            ps.setString(6, customer.getCountry());
            ps.setInt(7, customer.getCustomerId());
            ps.executeUpdate();
        }
        finally{
            ResourcesManager.closeResources(null, ps);
        }
    }
    
    public void create(Connection con, Customer customer) throws SQLException{
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = con.prepareStatement("INSERT INTO Customers(CustomerName, ContactPerson, Address, City, PostCode, Country) VALUES(?,?,?,?,?,?)");
            ps.setString(1, customer.getCustomerName());
            ps.setString(2, customer.getContactPerson());
            ps.setString(3, customer.getAddress());
            ps.setString(4, customer.getCity());
            ps.setString(5, customer.getPostCode());
            ps.setString(6, customer.getCountry());
            ps.executeUpdate();
        }
        finally{
            ResourcesManager.closeResources(rs, ps);
        }
    }
    
    public List<Customer> findAll(Connection con) throws SQLException{
                PreparedStatement ps = null;
                ResultSet rs = null;
                List <Customer> customers = new ArrayList<>();
                try{
                    ps = con.prepareStatement("SELECT * FROM Customers");
                   
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        customers.add(new Customer(rs.getInt("CustomerId"), rs.getString("CustomerName"), rs.getString("ContactPerson"),
                        rs.getString("Address"), rs.getString("City"),rs.getString("PostCode"),rs.getString("Country")));
                    }
                }
                finally{
                    ResourcesManager.closeResources(rs, ps);
                }
                return customers;
    }
    
    
    
    
    
    
    
    
}
