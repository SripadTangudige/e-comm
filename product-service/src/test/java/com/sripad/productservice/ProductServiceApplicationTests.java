package com.sripad.productservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sripad.productservice.dto.ProductRequest;
import com.sripad.productservice.model.Product;
import com.sripad.productservice.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

	@Container
	public static MongoDBContainer mongoDBContainer
			= new MongoDBContainer("mongo:4.4.2");
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ProductRepository productRepository;

	static {
		mongoDBContainer.start();
	}

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dymDynamicPropertyRegistry) {
		dymDynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	@Test
	void testCreateProduct() throws Exception {
		ProductRequest productRequest = createProductRequest("MacBook Pro", "Apple laptop A flagship PC from Apple", 1200);
		String productRequestString = objectMapper.writeValueAsString(productRequest);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/product/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(productRequestString))
				.andExpect(status().isCreated());
		Assertions.assertEquals(1, productRepository.findAll().size());

	}

	@Test
	void testGetAllProducts() throws Exception {
		ProductRequest pR1 =  createProductRequest("iPhone 15","iPhone 15. A flag ship mobile from Apple", 1200);
		ProductRequest pR2 = createProductRequest("Samsung S24 Ultra", "Latest Samsung Flagship", 1399);
		Product p1 = mapToProduct(pR1);
		Product p2 = mapToProduct(pR2);
		productRepository.save(p1);
		productRepository.save(p1);
		mockMvc.perform(MockMvcRequestBuilders.get("/api/product/")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)));
	}

	private Product mapToProduct(ProductRequest productRequest) {
		return Product.builder()
				.name(productRequest.getName())
				.description(productRequest.getDescription())
				.price(productRequest.getPrice())
				.build();
	}

	private ProductRequest createProductRequest(String name, String description, long price ) {
		ProductRequest productRequest;
		productRequest = ProductRequest.builder()
				.name(name)
				.description(description)
				.price(BigDecimal.valueOf(price))
				.build();
		return productRequest;
	}
}
