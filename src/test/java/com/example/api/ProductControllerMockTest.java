package com.example.api;

import java.util.Arrays;
import java.util.Map;

import com.example.api.controllers.ProductController;
import com.example.api.models.Product;
import com.example.api.services.ProductService;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ProductControllerMockTest {
    private static Product p1;
    private static Product p2;
    private static Product p3;
    private static Product p4;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeAll
    public static void init() {
        p1 = new Product(1, "Banana", 10);
        p2 = new Product(2, "Pineapple", 35);
        p3 = new Product(3, "Apple", 45);
        p4=new Product(8, "Tomato", 18);
    }

    @Test
    public void testGetProducts() {
        /**stub */
        when(productService.reads()).thenReturn(Arrays.asList(p1, p2, p3));
        Map<String, Object> products = productController.getProducts();
        ResponseEntity<?> response = ResponseEntity.ok(products);
        assertThat(products, is(response.getBody()));
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void testGetProduct() {
        /**stub */
        when(productService.read(1)).thenReturn(p1);
        Map<String, Object> product = productController.getProduct(1);
        ResponseEntity<?> response = ResponseEntity.ok(product);
        assertThat(product.get("product"), is(p1));
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void testCreateProduct() {
        /**stub */
        when(productService.read(8)).thenReturn(p4);
        Map<String, Object> product = productController.createProduct(p4);
        /** confirm if product was created */
        Map<String, Object> created = productController.getProduct(8);
        ResponseEntity<?> response = ResponseEntity.status(HttpStatus.CREATED).body(product);
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(product, is(response.getBody()));
        assertThat(created.get("product"), is(p4));
        verify(productService, times(1)).create(p4);
    }

}