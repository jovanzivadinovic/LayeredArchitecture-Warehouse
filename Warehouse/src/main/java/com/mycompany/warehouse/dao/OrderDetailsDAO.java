/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.warehouse.dao;

import com.mycompany.warehouse.data.Order;
import com.mycompany.warehouse.data.OrderDetails;
import com.mycompany.warehouse.data.Product;
import java.sql.Connection;
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
public class OrderDetailsDAO {
    private static OrderDetailsDAO instance = new OrderDetailsDAO();
    
    
    public static OrderDetailsDAO getInstance(){
        return instance;
    }
    
    
    public OrderDetails find(Connection con, int id) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        OrderDetails orderDetails = null;
        try {
            ps = con.prepareStatement("SELECT * FROM OrderDetails WHERE OrderDetailsId=?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Order order = OrderDAO.getInstance().find(con,rs.getInt("OrderId"));
                Product product = ProductsDAO.getInstance().find(con, rs.getInt("ProductId"));
                orderDetails = new OrderDetails(rs.getInt("OrderDetailsId"),order,product,rs.getInt("Quantity"));
                
            }
        } finally {
            ResourcesManager.closeResources(rs, ps);
        }
        return orderDetails;
    }
    
    public void delete(Connection con, int id) throws SQLException {
        PreparedStatement ps = null;
        try {
            if (OrderDetailsDAO.getInstance().find(con, id).getOrder() != null) {
                    OrderDAO.getInstance().delete(con, OrderDetailsDAO.getInstance().find(con, id).getOrder().getOrderId());
            }
            
            ps = con.prepareStatement("DELETE FROM OrderDetails WHERE OrderDetailsId=?");
            ps.setInt(1, id);
             
            ps.executeUpdate();
        } finally {
            ResourcesManager.closeResources(null, ps);
        }
    }
    
    public void deleteByOrder(Connection con, int id) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement("SELECT FROM OrderDetails WHERE OrderId=?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (!rs.next())
                return;
            if (OrderDetailsDAO.getInstance().find(con, id).getOrder() != null) {
                    OrderDAO.getInstance().delete(con, OrderDetailsDAO.getInstance().find(con, id).getOrder().getOrderId());
            }
            
            ps = con.prepareStatement("DELETE FROM OrderDetails WHERE OrderId=?");
            ps.setInt(1, id);
             
            ps.executeUpdate();
        } finally {
            ResourcesManager.closeResources(null, ps);
        }
    }
    public void deleteByProduct(Connection con, int id) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement("SELECT FROM OrderDetails WHERE ProductId=?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (!rs.next())
                return;
            if (OrderDetailsDAO.getInstance().find(con, id).getOrder() != null) {
                    OrderDAO.getInstance().delete(con, OrderDetailsDAO.getInstance().find(con, id).getOrder().getOrderId());
            }
            
            ps = con.prepareStatement("DELETE FROM OrderDetails WHERE ProductId=?");
            ps.setInt(1, id);
             
            ps.executeUpdate();
        } finally {
            ResourcesManager.closeResources(null, ps);
        }
    }
    
    
    
    public void update(Connection con, OrderDetails orderDetails) throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("UPDATE OrderDetails SET OrderId=?, ProductId=?, Quantity=? WHERE OrderDetailsId=?");
            ps.setInt(1, orderDetails.getOrder().getOrderId());
            ps.setInt(2, orderDetails.getProduct().getProductId());
            ps.setInt(3, orderDetails.getQuantity());
            ps.setInt(4, orderDetails.getOrderDetailsId());
            
            ps.executeUpdate();
        } finally {
            ResourcesManager.closeResources(null, ps);
        }
    }
    
    
    
    public void create(Connection con, OrderDetails orderDetails) throws SQLException{
        PreparedStatement ps = null;
        ResultSet rs = null;
        int id = -1;
        try {
   
            ps = con.prepareStatement("INSERT INTO OrderDetails(OrderId, ProductId, Quantity) VALUES(?,?,?)", Statement.RETURN_GENERATED_KEYS);
            
            Order order = OrderDAO.getInstance().find(con, orderDetails.getOrder().getOrderId());
            if (order == null){
                int orderId = OrderDAO.getInstance().create(con, orderDetails.getOrder());
                order = OrderDAO.getInstance().find(con,orderId);
            }
            Product product = ProductsDAO.getInstance().find(con, orderDetails.getProduct().getProductId());
            if (order == null) {
                throw new SQLException("Order " + orderDetails.getOrder() + " doesn't exist in database.");
            }
            else if(product == null){
                throw new SQLException("Product " + orderDetails.getProduct() + " doesn't exist in database.");
            }
            
            ps.setInt(1, order.getOrderId());
            ps.setInt(2, product.getProductId());
            ps.setInt(3, orderDetails.getQuantity());
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            rs.next();
            id = rs.getInt(1);
        } finally {
            ResourcesManager.closeResources(rs, ps);
        }
    }
    
    
    public List<OrderDetails> findAll(Connection con) throws SQLException{
                PreparedStatement ps = null;
                ResultSet rs = null;
                List <OrderDetails> orderdetails = new ArrayList<>();
                try{
                    ps = con.prepareStatement("SELECT * FROM OrderDetails");
                   
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        Order order = OrderDAO.getInstance().find(con, rs.getInt("OrderId"));
                        Product product = ProductsDAO.getInstance().find(con, rs.getInt("ProductId"));
            
                        orderdetails.add(new OrderDetails(rs.getInt("OrderDetailsId"), order, product, rs.getInt("Quantity")));
                    }
                }
                finally{
                    ResourcesManager.closeResources(rs, ps);
                }
                return orderdetails;
    }
    
}
