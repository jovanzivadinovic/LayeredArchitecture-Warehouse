/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.warehouse.service;

import com.mycompany.warehouse.dao.OrderDAO;
import com.mycompany.warehouse.dao.ResourcesManager;
import com.mycompany.warehouse.dao.ShippersDAO;
import com.mycompany.warehouse.data.Shipper;
import com.mycompany.warehouse.exception.WarehouseException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zivad
 */
public class ShipperService {
    private static final ShipperService instance = new ShipperService();
    
    
    
    public static ShipperService getInstance(){
        return instance;
    }
    
    public void addNewShipper(Shipper shipper) throws WarehouseException{
        Connection con = null;
        try{
            con = ResourcesManager.getConnection();
            con.setAutoCommit(false);
            ShippersDAO.getInstance().create(con, shipper);
            con.commit();
        }
        catch(SQLException ex){
            ResourcesManager.rollbackTransactions(con);
            throw new WarehouseException("Failed to add new shipper " + shipper, ex);
        }
        finally{
            ResourcesManager.closeConnection(con);
        }
    }
    
    public Shipper findShipper(int id) throws WarehouseException{
        Connection con = null;
        try{
            con = ResourcesManager.getConnection();
            return ShippersDAO.getInstance().find(con, id);
        }
        catch(SQLException ex){
            throw new WarehouseException("Failed to find shipper with ID " + id, ex);
        }
        finally{
            ResourcesManager.closeConnection(con);
        }
    }
    
    public void deleteShipper(int id) throws WarehouseException{
        Connection con = null;
        try {
            con = ResourcesManager.getConnection();
            con.setAutoCommit(false);

            Shipper shipper = ShippersDAO.getInstance().find(con, id);
            if (shipper != null) {
                ShippersDAO.getInstance().delete(con, id);
                OrderDAO.getInstance().deleteByShipper(con, id);
            }

            con.commit();
        } catch (SQLException ex) {
            ResourcesManager.rollbackTransactions(con);
            throw new WarehouseException("Failed to delete shipper with ID " + id, ex);
        } finally {
            ResourcesManager.closeConnection(con);
        }
    }
    
    public void updateShipper(Shipper shipper) throws WarehouseException {
        Connection con = null;
        try {
            con = ResourcesManager.getConnection();
            con.setAutoCommit(false);
            ShippersDAO.getInstance().update(con, shipper);
            
            con.commit();
        } catch (SQLException ex) {
            ResourcesManager.rollbackTransactions(con);
            throw new WarehouseException("Failed to update shipper " + shipper, ex);
        } finally {
            ResourcesManager.closeConnection(con);
        }
    }
    
    public List<Shipper> findAllShippers() throws WarehouseException{
        Connection con = null;
        List <Shipper> shippers = new ArrayList<>();
        try{
            con = ResourcesManager.getConnection();
            con.setAutoCommit(false);
            shippers = ShippersDAO.getInstance().findAll(con);
            return shippers;
        }
        catch(SQLException ex){
            throw new WarehouseException("Failed to find all shippers", ex);
        }
        finally{
            ResourcesManager.closeConnection(con);
        }
    }
}
