package com.bank.client.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class ClientController {
	Logger log = LoggerFactory.getLogger(ClientController.class);

	@Autowired
	private DiscoveryClient discoveryClient;
	
    
	@GetMapping("/accounts/{id}")
    public Account getAccountById(@PathVariable int id) {
		log.info("getAccountById called by client");

		RestTemplate restTemplate = new RestTemplate();
        ServiceInstance serviceInstance = getServiceInstance();
    
        
        String serviceURI = String.format("%s/accounts/%s", serviceInstance.getUri().toString(), id);
        ResponseEntity<Account> restExchange = restTemplate.exchange(serviceURI, HttpMethod.GET, null, Account.class);
        return restExchange.getBody();
    }
	
	@GetMapping("/accounts")
    public List<Account>  getAccounts() {
		log.info("getAccounts called by client");

		RestTemplate restTemplate = new RestTemplate();
        ServiceInstance serviceInstance = getServiceInstance();
    
        String serviceURI = String.format("%s/accounts", serviceInstance.getUri().toString());
        ResponseEntity<List<Account>> restExchange = restTemplate.exchange(serviceURI, HttpMethod.GET, null, new ParameterizedTypeReference<List<Account>>(){});
        
        return restExchange.getBody();
    }
	
	
	@PostMapping("/accounts")
    public Account createAccount(@RequestBody Account account) {
		log.info("createAccount called by client");

		RestTemplate restTemplate = new RestTemplate();
        ServiceInstance serviceInstance = getServiceInstance();
    
        String serviceURI = String.format("%s/accounts", serviceInstance.getUri().toString());
        ResponseEntity<Account> restExchange = restTemplate.postForEntity(serviceURI, account, Account.class);
        
        return restExchange.getBody();
    }
    
	@PutMapping("/accounts/{id}")
   	public void updateAccount(@PathVariable("id") Long id, @RequestBody Account account) {
		log.info("updateAccount called by client");

    	RestTemplate restTemplate = new RestTemplate();
        ServiceInstance serviceInstance = getServiceInstance();

        Account updatedAccount = new Account(account.getType(), account.getBalance(), account.getCustomer());
		log.info("updated account created");

        String serviceURI = String.format("%s/accounts/%s", serviceInstance.getUri().toString(), id);
        restTemplate.put(serviceURI, updatedAccount);
    }

    @DeleteMapping("/accounts/{id}")
    public void deleteAccount(@PathVariable(value = "id") Long id){
		log.info("deleteAccount called by client");

    	RestTemplate restTemplate = new RestTemplate();
        ServiceInstance serviceInstance = getServiceInstance();
    
        String serviceURI = String.format("%s/accounts/%s", serviceInstance.getUri().toString(), id);
        restTemplate.delete(serviceURI);
    }
	
    // Employee REST calls
    
    @GetMapping("/employees/{id}")
    public Employee getEmployeeById(@PathVariable int id) {
		log.info("getEmployeesById called by client");

		RestTemplate restTemplate = new RestTemplate();
        ServiceInstance serviceInstance = getServiceInstance();
    
        
        String serviceURI = String.format("%s/employees/%s", serviceInstance.getUri().toString(), id);
        ResponseEntity<Employee> restExchange = restTemplate.exchange(serviceURI, HttpMethod.GET, null, Employee.class);
        return restExchange.getBody();
    }
	
	@GetMapping("/employees")
    public List<Employee>  getEmployees() {
		log.info("getEmployees called by client");

		RestTemplate restTemplate = new RestTemplate();
        ServiceInstance serviceInstance = getServiceInstance();
    
        String serviceURI = String.format("%s/employees", serviceInstance.getUri().toString());
        ResponseEntity<List<Employee>> restExchange = restTemplate.exchange(serviceURI, HttpMethod.GET, null, new ParameterizedTypeReference<List<Employee>>(){});
        
        return restExchange.getBody();
    }
	
	@PutMapping("/employees/{id}")
   	public Employee updateEmployee(@PathVariable("id") Long id, @RequestBody Employee employee) {
		log.info("updateEmployee called by client");

    	RestTemplate restTemplate = new RestTemplate();
        ServiceInstance serviceInstance = getServiceInstance();

        Employee updatedEmployee = new Employee(employee.getName(), employee.getTitle(), employee.getSalary());
        
        String serviceURI = String.format("%s/employees/%s", serviceInstance.getUri().toString(), id);
        restTemplate.put(serviceURI, updatedEmployee);
        updatedEmployee.setId(id);
        return updatedEmployee;
    }
	
	@PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee employee) {
		log.info("createEmployee called by client");

		RestTemplate restTemplate = new RestTemplate();
        ServiceInstance serviceInstance = getServiceInstance();
    
        String serviceURI = String.format("%s/employees", serviceInstance.getUri().toString());
        ResponseEntity<Employee> restExchange = restTemplate.postForEntity(serviceURI, employee, Employee.class);
        
        return restExchange.getBody();
    }
    
    

    @DeleteMapping("/employees/{id}")
    public void deleteEmployee(@PathVariable(value = "id") Long id){
		log.info("deleteEmployee called by client");

    	RestTemplate restTemplate = new RestTemplate();
        ServiceInstance serviceInstance = getServiceInstance();
    
        String serviceURI = String.format("%s/employees/%s", serviceInstance.getUri().toString(), id);
        restTemplate.delete(serviceURI);
    }
	
    
    public ServiceInstance getServiceInstance() {
    	RestTemplate restTemplate = new RestTemplate();
		List<ServiceInstance> serviceInstances = discoveryClient.getInstances("accounts-app");
		
        if (serviceInstances.size() == 0) {
        	return null;
        }
        return serviceInstances.get(0);
    }
}
