package com.example.api;

import java.util.Arrays;
import java.util.Map;

import com.example.api.controllers.ProductController;
import com.example.api.models.Product;
import com.example.api.services.ProductService;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ProductControllerMockTest {
    Logger log = LoggerFactory.getLogger(ProductControllerMockTest.class);
    private static Product p1;
    private static Product p2;
    private static Product p3;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeAll
    public static void init() {
        p1 = new Product(1, "Banana", 10);
        p2 = new Product(2, "Pineapple", 35);
        p3 = new Product(3, "Apple", 45);
    }

    @Test
    public void testFindAll() {
        Mockito.when(productService.listAll()).thenReturn(Arrays.asList(p1, p2, p3));
        assertThat(productController.getProducts().size(), is(3));
        Mockito.verify(productService, Mockito.times(1)).listAll();
    }

    @Test
    public void testCreateProduct() {
        Map<String, Object> product = productController.createProduct(p2);
        ResponseEntity<?> response = ResponseEntity.status(HttpStatus.CREATED).body(product);
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        Mockito.verify(productService, Mockito.times(1)).create(p2);
    }

    @Test
    public void testGetProduct() {
        Mockito.when(productService.getProduct(1)).thenReturn(p1);
        Map<String, Object> product = productController.getProduct(1);
        ResponseEntity<?> response = ResponseEntity.ok(product);
        assertThat(product.get("product"), is(p1));
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }
}