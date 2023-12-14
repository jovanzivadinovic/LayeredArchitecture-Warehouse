/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.warehouse.service;

import com.mycompany.warehouse.dao.ProductsDAO;
import com.mycompany.warehouse.dao.ResourcesManager;
import com.mycompany.warehouse.dao.SuppliersDAO;
import com.mycompany.warehouse.data.Supplier;
import com.mycompany.warehouse.exception.WarehouseException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zivad
 */
public class SupplierService {
    private static final SupplierService instance = new SupplierService();
    
    private SupplierService(){
        
    }
    
    public static SupplierService getInstance(){
        return instance;
    }
    
    public void addNewSupplier(Supplier supplier) throws WarehouseException{
        Connection con = null;
        try{
            con = ResourcesManager.getConnection();
            con.setAutoCommit(false);
            SuppliersDAO.getInstance().create(con, supplier);
            con.commit();
        }
        catch(SQLException ex){
            ResourcesManager.rollbackTransactions(con);
            throw new WarehouseException("Failed to add new supplier " + supplier, ex);
        }
        finally{
            ResourcesManager.closeConnection(con);
        }
    }
    
    public Supplier findSupplier(int id) throws WarehouseException{
        Connection con = null;
        try{
            con = ResourcesManager.getConnection();
            return SuppliersDAO.getInstance().find(con, id);
        }
        catch(SQLException ex){
            throw new WarehouseException("Failed to find supplier with ID " + id, ex);
        }
        finally{
            ResourcesManager.closeConnection(con);
        }
    }
    
    
    public void deleteSupplier(int id) throws WarehouseException{
        Connection con = null;
        try {
            con = ResourcesManager.getConnection();
            con.setAutoCommit(false);

            Supplier supplier = SuppliersDAO.getInstance().find(con, id);
            if (supplier != null) {
                SuppliersDAO.getInstance().delete(con, id);
                ProductsDAO.getInstance().deleteBySupplier(con, id);
            }

            con.commit();
        } catch (SQLException ex) {
            ResourcesManager.rollbackTransactions(con);
            throw new WarehouseException("Failed to delete supplier with ID " + id, ex);
        } finally {
            ResourcesManager.closeConnection(con);
        }
    }
    
    public void updateSupplier(Supplier supplier) throws WarehouseException {
        Connection con = null;
        try {
            con = ResourcesManager.getConnection();
            con.setAutoCommit(false);
            SuppliersDAO.getInstance().update(con, supplier);
            
            con.commit();
        } catch (SQLException ex) {
            ResourcesManager.rollbackTransactions(con);
            throw new WarehouseException("Failed to update supplier " + supplier, ex);
        } finally {
            ResourcesManager.closeConnection(con);
        }
    }
    
    public List<Supplier> findAllSuppliers() throws WarehouseException{
        Connection con = null;
        List <Supplier> suppliers = new ArrayList<>();
        try{
            con = ResourcesManager.getConnection();
            con.setAutoCommit(false);
            suppliers = SuppliersDAO.getInstance().findAll(con);
            return suppliers;
        }
        catch(SQLException ex){
            throw new WarehouseException("Failed to find all suppliers", ex);
        }
        finally{
            ResourcesManager.closeConnection(con);
        }
    }
}
