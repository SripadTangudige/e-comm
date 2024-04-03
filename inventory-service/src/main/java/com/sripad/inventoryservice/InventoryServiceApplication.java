package com.sripad.inventoryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

//	@Bean
//	CommandLineRunner initDatabase(InventoryRepository inventoryRepository) {
//		return args -> {
//			inventoryRepository.save(Inventory.builder().skuCode("iphone-16").quantity(1000).build());
//			inventoryRepository.save(Inventory.builder().skuCode("iphone-17").quantity(2500).build());
//			// Additional initialization code can go here
//		};
//	}

}
