/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.warehouse.service;

import com.mycompany.warehouse.dao.OrderDetailsDAO;
import com.mycompany.warehouse.dao.ProductsDAO;
import com.mycompany.warehouse.dao.ResourcesManager;
import com.mycompany.warehouse.data.Product;
import com.mycompany.warehouse.exception.WarehouseException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zivad
 */
public class ProductService {
    private static final ProductService instance = new ProductService();
    
    
    
    public static ProductService getInstance(){
        return instance;
    }
    
    public void addNewProduct(Product product) throws WarehouseException{
        Connection con = null;
        try{
            con = ResourcesManager.getConnection();
            con.setAutoCommit(false);
            ProductsDAO.getInstance().create(con, product);
            con.commit();
        }
        catch(SQLException ex){
            ResourcesManager.rollbackTransactions(con);
            throw new WarehouseException("Failed to add new product " + product, ex);
        }
        finally{
            ResourcesManager.closeConnection(con);
        }
    }
    
    public Product findProduct(int id) throws WarehouseException{
        Connection con = null;
        try{
            con = ResourcesManager.getConnection();
            return ProductsDAO.getInstance().find(con, id);
        }
        catch(SQLException ex){
            throw new WarehouseException("Failed to find product with ID " + id, ex);
        }
        finally{
            ResourcesManager.closeConnection(con);
        }
    }
    
    public void deleteProduct(int id) throws WarehouseException{
        Connection con = null;
        try {
            con = ResourcesManager.getConnection();
            con.setAutoCommit(false);

            Product product = ProductsDAO.getInstance().find(con, id);
            if (product != null) {
                ProductsDAO.getInstance().delete(con, id);
                OrderDetailsDAO.getInstance().deleteByProduct(con, id);
            }

            con.commit();
        } catch (SQLException ex) {
            ResourcesManager.rollbackTransactions(con);
            throw new WarehouseException("Failed to delete product with ID " + id, ex);
        } finally {
            ResourcesManager.closeConnection(con);
        }
    }
    
    public void updateProduct(Product product) throws WarehouseException {
        Connection con = null;
        try {
            con = ResourcesManager.getConnection();
            con.setAutoCommit(false);
            ProductsDAO.getInstance().update(con, product);
            
            con.commit();
        } catch (SQLException ex) {
            ResourcesManager.rollbackTransactions(con);
            throw new WarehouseException("Failed to update product " + product, ex);
        } finally {
            ResourcesManager.closeConnection(con);
        }
    }
    
    public List<Product> findAllProducts() throws WarehouseException{
        Connection con = null;
        List <Product> products = new ArrayList<>();
        try{
            con = ResourcesManager.getConnection();
            con.setAutoCommit(false);
            products = ProductsDAO.getInstance().findAll(con);
            return products;
        }
        catch(SQLException ex){
            throw new WarehouseException("Failed to find all products", ex);
        }
        finally{
            ResourcesManager.closeConnection(con);
        }
    }
}
