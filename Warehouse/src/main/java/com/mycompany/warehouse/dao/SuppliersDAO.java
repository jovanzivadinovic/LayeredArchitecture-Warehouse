/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.warehouse.dao;


import com.mycompany.warehouse.data.Employee;
import com.mycompany.warehouse.data.Supplier;
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
public class SuppliersDAO {
    private static SuppliersDAO instance = new SuppliersDAO();
    
    
    public static SuppliersDAO getInstance(){
        return instance;
    }
    
    public Supplier find(Connection con, int id) throws SQLException{
        PreparedStatement ps = null;
        ResultSet rs = null;
        Supplier supplier = null;
        try {
            ps = con.prepareStatement("SELECT * FROM Suppliers WHERE SupplierId=?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                supplier = new Supplier(rs.getInt("SupplierId"), rs.getString("SupplierName"), rs.getString("ContactPerson"),
                        rs.getString("Address"),rs.getString("City"),
                        rs.getString("PostCode"), rs.getString("Country"),rs.getString("Phone"));
            }
        } finally {
            ResourcesManager.closeResources(rs, ps);
        }
        return supplier;
    }
    
    public void delete(Connection con, int id) throws SQLException{
        PreparedStatement ps = null;
        try{
            ps = con.prepareStatement("DELETE FROM Suppliers WHERE SupplierId=?");
            ps.setInt(1, id);
            ps.executeUpdate();
            
        }
        finally{
            ResourcesManager.closeResources(null, ps);
        }
    }
    
    public void update(Connection con, Supplier supplier) throws SQLException{
        PreparedStatement ps = null;
        try{
            ps = con.prepareStatement("UPDATE Suppliers SET SupplierName=?, ContactPerson=?, Address=?, City=?, PostCode=?, Country=?, Phone=? WHERE SupplierId=?");
            ps.setString(1, supplier.getSupplierName());
            ps.setString(2, supplier.getContactPerson());
            ps.setString(3, supplier.getAddress());
            ps.setString(4, supplier.getCity());
            ps.setString(5, supplier.getPostCode());
            ps.setString(6, supplier.getCountry());
            ps.setString(7, supplier.getPhone());
            ps.setInt(8, supplier.getSupplierId());
            ps.executeUpdate();
        }
        finally{
            ResourcesManager.closeResources(null, ps);
        }
    }
    
    public void create(Connection con, Supplier supplier) throws SQLException{
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = con.prepareStatement("INSERT INTO suppliers(SupplierName, ContactPerson, Address, City, PostCode, Country, Phone) VALUES(?,?,?,?,?,?,?)");
            ps.setString(1, supplier.getSupplierName());
            ps.setString(2, supplier.getContactPerson());
            ps.setString(3, supplier.getAddress());
            ps.setString(4, supplier.getCity());
            ps.setString(5, supplier.getPostCode());
            ps.setString(6, supplier.getCountry());
            ps.setString(7, supplier.getPhone());
            ps.executeUpdate();
        }
        finally{
            ResourcesManager.closeResources(rs, ps);
        }
    }
    
    public List<Supplier> findAll(Connection con) throws SQLException{
        PreparedStatement ps = null;
        ResultSet rs = null;
        List <Supplier> suppliers = new ArrayList<>();
        try{
            ps = con.prepareStatement("SELECT * FROM suppliers");

            rs = ps.executeQuery();
            while (rs.next()) {
                suppliers.add(new Supplier(rs.getInt("SupplierId"), rs.getString("SupplierName"), 
                        rs.getString("ContactPerson"), rs.getString("Address"),
                        rs.getString("City"), rs.getString("PostCode"),rs.getString("Country"),
                        rs.getString("Phone")));
            }
        }
        finally{
            ResourcesManager.closeResources(rs, ps);
        }
        return suppliers;
    }

    
}
