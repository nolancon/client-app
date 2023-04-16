package com.bank.client.controllers;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ClientController {
	
	@Autowired
	private DiscoveryClient discoveryClient;
	

	@GetMapping("/accounts/{id}")
    public Account getAccountById(@PathVariable int id) {
		RestTemplate restTemplate = new RestTemplate();
        ServiceInstance serviceInstance = getServiceInstance();
    
        
        String serviceURI = String.format("%s/accounts/%s", serviceInstance.getUri().toString(), id);
        ResponseEntity<Account> restExchange = restTemplate.exchange(serviceURI, HttpMethod.GET, null, Account.class);
        return restExchange.getBody();
    }
	
	@GetMapping("/accounts")
    public List<Account>  getAccounts() {
		RestTemplate restTemplate = new RestTemplate();
        ServiceInstance serviceInstance = getServiceInstance();
    
        String serviceURI = String.format("%s/accounts", serviceInstance.getUri().toString());
        ResponseEntity<List<Account>> restExchange = restTemplate.exchange(serviceURI, HttpMethod.GET, null, new ParameterizedTypeReference<List<Account>>(){});
        
        return restExchange.getBody();
    }
	
	
	@PostMapping("/accounts")
    public Account createAccount(@RequestBody Account account) {
		RestTemplate restTemplate = new RestTemplate();
        ServiceInstance serviceInstance = getServiceInstance();
    
        String serviceURI = String.format("%s/accounts", serviceInstance.getUri().toString());
        ResponseEntity<Account> restExchange = restTemplate.postForEntity(serviceURI, account, Account.class);
        
        return restExchange.getBody();
    }
    
    

    @DeleteMapping("/accounts/{id}")
    public void deleteAccount(@PathVariable(value = "id") Long id){
    	RestTemplate restTemplate = new RestTemplate();
        ServiceInstance serviceInstance = getServiceInstance();
    
        String serviceURI = String.format("%s/accounts/%s", serviceInstance.getUri().toString(), id);
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
