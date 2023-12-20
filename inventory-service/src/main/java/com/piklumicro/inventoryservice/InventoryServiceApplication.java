package com.piklumicro.inventoryservice;

import com.piklumicro.inventoryservice.model.Inventory;
import com.piklumicro.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import org.springframework.context.annotation.Bean;

@SpringBootApplication


public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository){
		return args -> {
			Inventory inventory = new Inventory();
			inventory.setSkuCode("HP_VICTUS");
			inventory.setQuantity(100);

			Inventory inventory1 = new Inventory();
			inventory.setSkuCode("Dell_G15");
			inventory.setQuantity(10);


			inventoryRepository.save(inventory);
			inventoryRepository.save(inventory1);
		};
	}

}