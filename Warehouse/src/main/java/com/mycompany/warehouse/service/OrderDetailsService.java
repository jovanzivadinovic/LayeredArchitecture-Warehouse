/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.warehouse.service;

import com.mycompany.warehouse.dao.OrderDetailsDAO;
import com.mycompany.warehouse.data.OrderDetails;
import com.mycompany.warehouse.dao.ResourcesManager;
import com.mycompany.warehouse.exception.WarehouseException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zivad
 */
public class OrderDetailsService {
    private static final OrderDetailsService instance = new OrderDetailsService();
    
    
    public static OrderDetailsService getInstance(){
        return instance;
    }
    
    public void addNewOrderDetails(OrderDetails orderDetails) throws WarehouseException{
        Connection con = null;
        try{
            con = ResourcesManager.getConnection();
            con.setAutoCommit(false);
            OrderDetailsDAO.getInstance().create(con, orderDetails);
            con.commit();
        }
        catch(SQLException ex){
            ResourcesManager.rollbackTransactions(con);
            throw new WarehouseException("Failed to add new order details " + orderDetails, ex);
        }
        finally{
            ResourcesManager.closeConnection(con);
        }
    }
    
    public OrderDetails findOrderDetails(int id) throws WarehouseException{
        Connection con = null;
        try{
            con = ResourcesManager.getConnection();
            return OrderDetailsDAO.getInstance().find(con, id);
        }
        catch(SQLException ex){
            throw new WarehouseException("Failed to find order details with ID " + id, ex);
        }
        finally{
            ResourcesManager.closeConnection(con);
        }
    }
    
    
    public void deleteOrderDetails(int id) throws WarehouseException{
        Connection con = null;
        try {
            con = ResourcesManager.getConnection();
            con.setAutoCommit(false);

            OrderDetails orderDetails = OrderDetailsDAO.getInstance().find(con, id);
            if (orderDetails != null) {
                OrderDetailsDAO.getInstance().delete(con, id);
            }

            con.commit();
        } catch (SQLException ex) {
            ResourcesManager.rollbackTransactions(con);
            throw new WarehouseException("Failed to delete order details with ID " + id, ex);
        } finally {
            ResourcesManager.closeConnection(con);
        }
    }
    
    public void updateOrderDetails(OrderDetails orderDetails) throws WarehouseException {
        Connection con = null;
        try {
            con = ResourcesManager.getConnection();
            con.setAutoCommit(false);
            OrderDetailsDAO.getInstance().update(con, orderDetails);
            
            con.commit();
        } catch (SQLException ex) {
            ResourcesManager.rollbackTransactions(con);
            throw new WarehouseException("Failed to update order details " + orderDetails, ex);
        } finally {
            ResourcesManager.closeConnection(con);
        }
    }
    
    public List<OrderDetails> findAllOrderDetails() throws WarehouseException{
        Connection con = null;
        List <OrderDetails> orderdetails = new ArrayList<>();
        try{
            con = ResourcesManager.getConnection();
            con.setAutoCommit(false);
            orderdetails = OrderDetailsDAO.getInstance().findAll(con);
            return orderdetails;
        }
        catch(SQLException ex){
            throw new WarehouseException("Failed to find all order details", ex);
        }
        finally{
            ResourcesManager.closeConnection(con);
        }
    }
}
