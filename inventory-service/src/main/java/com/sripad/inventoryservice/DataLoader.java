package com.sripad.inventoryservice;

import com.sripad.inventoryservice.model.Inventory;
import com.sripad.inventoryservice.respository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Override
    public void run(String... args) throws Exception {
        // Load sample data into the database
        inventoryRepository.save(Inventory.builder().skuCode("iphone-16").quantity(1000).build());
        inventoryRepository.save(Inventory.builder().skuCode("iphone-17").quantity(2500).build());
    }

//    @PreDestroy
//    public void cleanup() {
//        // Delete all data upon shutdown
//        System.out.println("Application is shutting down. Deleting all inventory records...");
//        inventoryRepository.deleteAll();
//    }
}
