/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.warehouse.rest;

import com.mycompany.warehouse.data.Order;
import com.mycompany.warehouse.exception.WarehouseException;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.mycompany.warehouse.service.OrderService;

/**
 *
 * @author zivad
 */
@Path("orders")
public class OrderRest {
    private final OrderService orderService = OrderService.getInstance();
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List <Order> getAllOrders() throws WarehouseException{
        return orderService.findAllOrders();
    }
    
    @GET
    @Path("/{OrderId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Order getOrderById(@PathParam("OrderId") int orderId) throws WarehouseException{
        return orderService.findOrder(orderId);
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addOrder(Order order) throws WarehouseException{
            orderService.addNewOrder(order);
            return Response.ok().build();
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateOrder(Order order) throws WarehouseException {
            orderService.updateOrder(order);
            return Response.ok().build();
    }
    
    
    @DELETE
    @Path("/{OrderId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteOrder(@PathParam("OrderId") int orderId) throws WarehouseException{
            orderService.deleteOrder(orderId);
            return Response.ok().build();
    }
}
