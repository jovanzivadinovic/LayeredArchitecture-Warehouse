/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.warehouse.rest;

import com.mycompany.warehouse.data.Product;
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
import com.mycompany.warehouse.service.ProductService;

/**
 *
 * @author zivad
 */
@Path("products")
public class ProductRest {
    private final ProductService productService = ProductService.getInstance();
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List <Product> getAllProducts() throws WarehouseException{
        return productService.findAllProducts();
    }
    
    @GET
    @Path("/{ProductId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Product getProductById(@PathParam("ProductId") int productId) throws WarehouseException{
        return productService.findProduct(productId);
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addProduct(Product product) throws WarehouseException{
            productService.addNewProduct(product);
            return Response.ok().build();
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProduct(Product product) throws WarehouseException {
            productService.updateProduct(product);
            return Response.ok().build();
    }
    
    
    @DELETE
    @Path("/{ProductId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteProduct(@PathParam("ProductId") int productId) throws WarehouseException{
            productService.deleteProduct(productId);
            return Response.ok().build();
    }
    
}
