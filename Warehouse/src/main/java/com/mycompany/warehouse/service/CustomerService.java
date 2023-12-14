/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.warehouse.service;

import com.mycompany.warehouse.dao.CustomersDAO;
import com.mycompany.warehouse.dao.OrderDAO;
import com.mycompany.warehouse.dao.ResourcesManager;
import com.mycompany.warehouse.data.Customer;
import com.mycompany.warehouse.exception.WarehouseException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zivad
 */
public class CustomerService {
    private static final CustomerService instance = new CustomerService();
    
    
    
    public static CustomerService getInstance(){
        return instance;
    }
    
    public void addNewCustomer(Customer customer) throws WarehouseException{
        Connection con = null;
        try{
            con = ResourcesManager.getConnection();
            con.setAutoCommit(false);
            CustomersDAO.getInstance().create(con, customer);
            con.commit();
            
            
        }
        catch(SQLException ex){
            ResourcesManager.rollbackTransactions(con);
            throw new WarehouseException("Failed to add new customer " + customer, ex);
        }
        finally{
            ResourcesManager.closeConnection(con);
        }
    }
    
    public Customer findCustomer(int id) throws WarehouseException{
        Connection con = null;
        Customer customer = null;
        try{
            con = ResourcesManager.getConnection();
            customer = CustomersDAO.getInstance().find(con, id);
        }
        catch(SQLException ex){
            throw new WarehouseException("Failed to find customer with ID " + id, ex);
        }
        finally{
            ResourcesManager.closeConnection(con);
        }
        return customer;
    }
    
    public void deleteCustomer(int id) throws WarehouseException{
        Connection con = null;
        try {
            con = ResourcesManager.getConnection();
            con.setAutoCommit(false);

            Customer customer = CustomersDAO.getInstance().find(con, id);
            if (customer != null) {
                CustomersDAO.getInstance().delete(con, id);
                OrderDAO.getInstance().deleteByCustomer(con, id);
            }

            con.commit();
        } catch (SQLException ex) {
            ResourcesManager.rollbackTransactions(con);
            throw new WarehouseException("Failed to delete customer with ID " + id, ex);
        } finally {
            ResourcesManager.closeConnection(con);
        }
    }
    
    
    public void updateCustomer(Customer customer) throws WarehouseException {
        Connection con = null;
        try {
            con = ResourcesManager.getConnection();
            con.setAutoCommit(false);

            CustomersDAO.getInstance().update(con, customer);

            con.commit();
        } catch (SQLException ex) {
            ResourcesManager.rollbackTransactions(con);
            throw new WarehouseException("Failed to update customer " + customer, ex);
        } finally {
            ResourcesManager.closeConnection(con);
        }
    }
    
    public List<Customer> findAllCustomers() throws WarehouseException{
        Connection con = null;
        List <Customer> customers = new ArrayList<>();
        try{
            con = ResourcesManager.getConnection();
            con.setAutoCommit(false);
            customers = CustomersDAO.getInstance().findAll(con);
            return customers;
        }
        catch(SQLException ex){
            throw new WarehouseException("Failed to find all customers", ex);
        }
        finally{
            ResourcesManager.closeConnection(con);
        }
    }
}
