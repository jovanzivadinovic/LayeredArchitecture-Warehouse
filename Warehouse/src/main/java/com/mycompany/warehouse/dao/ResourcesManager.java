/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.warehouse.dao;

import com.mycompany.warehouse.exception.WarehouseException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author zivad
 */
public class ResourcesManager {
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost/warehouse2?user=root&password=&useSSL=false");
        return con;
    }

    public static void closeResources(ResultSet resultSet, PreparedStatement preparedStatement) throws SQLException {
        if (resultSet != null) {
            resultSet.close();
        }
        if (preparedStatement != null) {
            preparedStatement.close();
        }
    }

    public static void closeConnection(Connection con) throws WarehouseException {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                throw new WarehouseException("Failed to close database connection.", ex);
            }
        }
    }

    public static void rollbackTransactions(Connection con) throws WarehouseException {
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                throw new WarehouseException("Failed to rollback database transactions.", ex);
            }
        }
    }
    
}
