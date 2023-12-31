/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.warehouse.rest;

import com.mycompany.warehouse.data.Employee;
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
import com.mycompany.warehouse.service.EmployeeService;

/**
 *
 * @author zivad
 */
@Path("employees")
public class EmployeeRest {
    private final EmployeeService employeeService = EmployeeService.getInstance();
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List <Employee> getAllEmployees() throws WarehouseException{
        return employeeService.findAllEmployees();
    }
    
    @GET
    @Path("/{EmployeeId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Employee getEmployeeById(@PathParam("EmployeeId") int employeeId) throws WarehouseException{
        return employeeService.findEmployee(employeeId);
    }
    
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addEmployee(Employee employee) throws WarehouseException{
            employeeService.addNewEmployee(employee);
            return Response.ok().build();
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateEmployee(Employee employee) throws WarehouseException {
            employeeService.updateEmployee(employee);
            return Response.ok().build();
    }
    
    
    @DELETE
    @Path("/{EmployeeId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteEmployee(@PathParam("EmployeeId") int employeeId) throws WarehouseException{
            employeeService.deleteEmployee(employeeId);
            return Response.ok().build();
    }
    
   
}
