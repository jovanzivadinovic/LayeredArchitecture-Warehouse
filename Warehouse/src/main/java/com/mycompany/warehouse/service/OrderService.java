/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.warehouse.service;

import com.mycompany.warehouse.dao.OrderDAO;
import com.mycompany.warehouse.dao.ResourcesManager;
import com.mycompany.warehouse.data.Order;
import com.mycompany.warehouse.exception.WarehouseException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zivad
 */
public class OrderService {
    private static final OrderService instance = new OrderService();
    
    
    public static OrderService getInstance(){
        return instance;
    }
    
    public void addNewOrder(Order order) throws WarehouseException{
        Connection con = null;
        try{
            con = ResourcesManager.getConnection();
            con.setAutoCommit(false);
            OrderDAO.getInstance().create(con, order);
            con.commit();
        }
        catch(SQLException ex){
            ResourcesManager.rollbackTransactions(con);
            throw new WarehouseException("Failed to add new order " + order, ex);
        }
        finally{
            ResourcesManager.closeConnection(con);
        }
    }
    
    
    public Order findOrder(int id) throws WarehouseException{
        Connection con = null;
        try{
            con = ResourcesManager.getConnection();
            return OrderDAO.getInstance().find(con, id);
        }
        catch(SQLException ex){
            throw new WarehouseException("Failed to find order with ID " + id, ex);
        }
        finally{
            ResourcesManager.closeConnection(con);
        }
    }
    
    public void deleteOrder(int id) throws WarehouseException{
        Connection con = null;
        try {
            con = ResourcesManager.getConnection();
            con.setAutoCommit(false);

            Order order = OrderDAO.getInstance().find(con, id);
            if (order != null) {
                OrderDAO.getInstance().delete(con, id);
            }

            con.commit();
        } catch (SQLException ex) {
            ResourcesManager.rollbackTransactions(con);
            throw new WarehouseException("Failed to delete order with ID " + id, ex);
        } finally {
            ResourcesManager.closeConnection(con);
        }
    }
    
    
    public void updateOrder(Order order) throws WarehouseException {
        Connection con = null;
        try {
            con = ResourcesManager.getConnection();
            con.setAutoCommit(false);
            OrderDAO.getInstance().update(con, order);
            
            con.commit();
        } catch (SQLException ex) {
            ResourcesManager.rollbackTransactions(con);
            throw new WarehouseException("Failed to update order " + order, ex);
        } finally {
            ResourcesManager.closeConnection(con);
        }
    }
    
    
    public List<Order> findAllOrders() throws WarehouseException{
        Connection con = null;
        List <Order> orders = new ArrayList<>();
        try{
            con = ResourcesManager.getConnection();
            con.setAutoCommit(false);
            orders = OrderDAO.getInstance().findAll(con);
            return orders;
        }
        catch(SQLException ex){
            throw new WarehouseException("Failed to find all orders", ex);
        }
        finally{
            ResourcesManager.closeConnection(con);
        }
    }
}
