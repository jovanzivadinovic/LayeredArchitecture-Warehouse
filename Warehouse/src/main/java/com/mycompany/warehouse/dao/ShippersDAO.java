/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.warehouse.dao;

import com.mycompany.warehouse.data.Employee;
import com.mycompany.warehouse.data.Shipper;
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
public class ShippersDAO {
    private static ShippersDAO instance = new ShippersDAO();
    
    
    public static ShippersDAO getInstance(){
        return instance;
    }
    
    public Shipper find(Connection con, int id) throws SQLException{
        PreparedStatement ps = null;
        ResultSet rs = null;
        Shipper shipper = null;
        try {
            ps = con.prepareStatement("SELECT * FROM Shippers WHERE ShipperId=?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                shipper = new Shipper(rs.getInt("ShipperId"),
                        rs.getString("ShipperName"), rs.getString("Phone"));
            }
        } finally {
            ResourcesManager.closeResources(rs, ps);
        }
        return shipper;
    }
    
    public void delete(Connection con, int id) throws SQLException{
        PreparedStatement ps = null;
        try{
            ps = con.prepareStatement("DELETE FROM Shippers WHERE ShipperId=?");
            ps.setInt(1, id);
            ps.executeUpdate();
            
        }
        finally{
            ResourcesManager.closeResources(null, ps);
        }
    }
    
    public void update(Connection con, Shipper shipper) throws SQLException{
        PreparedStatement ps = null;
        try{
            ps = con.prepareStatement("UPDATE Shippers SET ShipperName=?, Phone=? WHERE ShipperId=?");
            ps.setString(1, shipper.getShipperName());
            ps.setString(2, shipper.getPhone());
            ps.setInt(3, (int)shipper.getShipperId());
            ps.executeUpdate();
        }
        finally{
            ResourcesManager.closeResources(null, ps);
        }
    }
    
    public void create(Connection con, Shipper shipper) throws SQLException{
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = con.prepareStatement("INSERT INTO Shippers(ShipperName, Phone) VALUES(?,?)");
            ps.setString(1, shipper.getShipperName());
            ps.setString(2, shipper.getPhone());
            ps.executeUpdate();
        }
        finally{
            ResourcesManager.closeResources(rs, ps);
        }
    }
    
    public List<Shipper> findAll(Connection con) throws SQLException{
                PreparedStatement ps = null;
                ResultSet rs = null;
                List <Shipper> shippers = new ArrayList<>();
                try{
                    ps = con.prepareStatement("SELECT * FROM Shippers");
                   
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        shippers.add(new Shipper(rs.getInt("ShipperId"), 
                    rs.getString("ShipperName"),rs.getString("Phone")));
                    }
                }
                finally{
                    ResourcesManager.closeResources(rs, ps);
                }
                return shippers;
    }
    
}
