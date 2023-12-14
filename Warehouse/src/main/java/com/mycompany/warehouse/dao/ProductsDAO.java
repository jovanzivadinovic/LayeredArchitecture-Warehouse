/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.warehouse.dao;

import com.mycompany.warehouse.data.Product;
import com.mycompany.warehouse.data.Supplier;
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
public class ProductsDAO {
    private static final ProductsDAO instance = new ProductsDAO();
    
    
    
    public static ProductsDAO getInstance(){
        return instance;
    }
    
    public Product find(Connection con, int id) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Product product = null;
        try {
            ps = con.prepareStatement("SELECT * FROM Products WHERE ProductId=?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Supplier supplier = SuppliersDAO.getInstance().find(con,rs.getInt("SupplierId"));
                product = new Product(rs.getInt("ProductId"), rs.getString("ProductName"), rs.getString("ProductCategory"), rs.getInt("PricePerUnit"), supplier);
            }
        } finally {
            ResourcesManager.closeResources(rs, ps);
        }
        return product;
    }
    
    public void delete(Connection con, int id) throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("DELETE FROM products WHERE ProductId=?");
            ps.setInt(1, id);
            ps.executeUpdate();
            
            
        } finally {
            ResourcesManager.closeResources(null, ps);
        }
    }
    public void deleteBySupplier(Connection con, int id) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement("SELECT FROM Products WHERE SupplierId=?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (!rs.next())
                return;
            ps = con.prepareStatement("DELETE FROM products WHERE SupplierId=?");
            ps.setInt(1, id);
            ps.executeUpdate();
            
            OrderDetailsDAO.getInstance().deleteByProduct(con, id);
        } finally {
            ResourcesManager.closeResources(null, ps);
        }
    }
    
    
    public void update(Connection con, Product product) throws SQLException {
    PreparedStatement ps = null;
    try {
        ps = con.prepareStatement("UPDATE Products SET ProductName=?, ProductCategory=?, PricePerUnit=?, SupplierId=? WHERE ProductId=?");
        ps.setString(1, product.getProductName());
        ps.setString(2, product.getProductCategory());
        ps.setInt(3, product.getPricePerUnit());
        ps.setInt(4, product.getSupplier().getSupplierId());
        ps.setInt(5, product.getProductId());
        ps.executeUpdate();
        } finally {
        ResourcesManager.closeResources(null, ps);
        }
    }
    
    public void create(Connection con, Product product) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        int id = -1;
        try {
            ps = con.prepareStatement("INSERT INTO Products(ProductName, ProductCategory, PricePerUnit, SupplierId) VALUES(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, product.getProductName());
            ps.setString(2, product.getProductCategory());
            ps.setInt(3, product.getPricePerUnit());
            Supplier supplier = SuppliersDAO.getInstance().find(con, product.getSupplier().getSupplierId());
            if (supplier == null) {
                throw new SQLException("Supplier " + product.getSupplier() + " doesn't exist in database.");
            }
            ps.setInt(4, supplier.getSupplierId());
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            rs.next();
            id = rs.getInt(1);
        } finally {
            ResourcesManager.closeResources(rs, ps);
        }
    }
    
    
    public List<Product> findAll(Connection con) throws SQLException{
        PreparedStatement ps = null;
        ResultSet rs = null;
        List <Product> products = new ArrayList<>();
        try{
            ps = con.prepareStatement("SELECT * FROM products");

            rs = ps.executeQuery();
            while (rs.next()) {
                Supplier supplier = SuppliersDAO.getInstance().find(con, rs.getInt("SupplierId"));
                products.add(new Product(rs.getInt("ProductId"), rs.getString("ProductName"), 
                        rs.getString("ProductCategory"), rs.getInt("PricePerUnit"), supplier));
            }
        }
        finally{
            ResourcesManager.closeResources(rs, ps);
        }
        return products;
    }
    
}
