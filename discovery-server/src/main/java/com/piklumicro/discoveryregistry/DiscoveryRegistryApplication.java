package com.piklumicro.discoveryregistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class DiscoveryRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiscoveryRegistryApplication.class, args);
	}

}
