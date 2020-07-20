package com.employeeconsumer.employeeconsumer;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@RestController
public class EmployeConsumerController {

	 
	 @Autowired
	 private RestTemplate restTemplate;
	
     @Autowired
   	 private LoadBalancerClient loadBalancer;
 		
	 @GetMapping("EmployeConsumerController/getEmployee")
	 public String getEmployee() throws RestClientException, IOException
	 {
		 
	   ResponseEntity<String> response=null;
		 
		try {
			
		        ServiceInstance instances=loadBalancer.choose("EMPLOYEEPRODUCER");
		        
				ServiceInstance serviceInstance=instances;
				
				String baseUrl=serviceInstance.getUri().toString();
				
				baseUrl=baseUrl+"/ProducerController/employee";
				
				response=restTemplate.exchange(baseUrl,HttpMethod.GET, getHeaders(),String.class);
		
		}
		catch(Exception e)
		{
			
			System.out.println(e);
		}
		
		return response.getBody();
		
		
		}	
	
		private static HttpEntity<?> getHeaders() throws IOException {
			HttpHeaders headers = new HttpHeaders();
			headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
			return new HttpEntity<>(headers);
		}
}
