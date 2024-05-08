package com.shopAminute.shopAminute;

import com.shopAminute.shopAminute.dataManagers.ProductRepo;
import com.shopAminute.shopAminute.handlers.ProductController;
import com.shopAminute.shopAminute.objects.Product;
import com.shopAminute.shopAminute.сoordinators.ProdService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductRepo productRepository;

    @Mock
    private ProdService prodService;

    @Mock
    private Model model;

    @InjectMocks
    private ProductController productController;

    @Test
    void getAllProduct() {
        when(prodService.getAllProduct()).thenReturn(Collections.emptyList());

        String viewName = productController.getAllProduct(model);

        verify(model).addAttribute("products", Collections.emptyList());
        assertEquals("/product/product", viewName);
    }

    @Test
    void ShouldAddProductToModelAndReturnView() {
        int productId = 1;
        Product product = new Product();
        when(prodService.getProductId(productId)).thenReturn(product);

        String viewName = productController.infoProduct(productId, model);

        verify(model).addAttribute("product", product);
        assertEquals("/product/infoProduct", viewName);
    }

    @Test
    void ShouldHandleSearchAndReturnView() {
        String search = "test";
        String ot = "100";
        String Do = "500";
        String price = "sorted_by_ascending_price";
        String contract = "furniture";

        List<Product> productList = Collections.singletonList(new Product());
        when(productRepository.findByTitleAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 1))
                .thenReturn(productList);

        String viewName = productController.productSearch(search, ot, Do, price, contract, model);

        verify(model).addAttribute("search_product", productList);
        assertEquals("/product/product", viewName);
    }

    @Test
    void ShouldReturnSortedProducts() {
        String search = "test";
        String ot = "100";
        String Do = "500";
        String price = "sorted_by_descending_price";
        String contract = "lamp";

        List<Product> productList = Collections.singletonList(new Product());
        when(productRepository.findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 3))
                .thenReturn(productList);

        String viewName = productController.productSearch(search, ot, Do, price, contract, model);

        verify(model).addAttribute("search_product", productList);
        assertEquals("/product/product", viewName);
    }

    @Test
    void ShouldReturnFilteredProducts() {
        String search = "test";
        String ot = "100";
        String Do = "500";
        String price = "";
        String contract = "";

        List<Product> productList = Collections.singletonList(new Product());
        when(productRepository.findByTitleAndPriceGreaterThanEqualAndPriceLessThanEqual(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do)))
                .thenReturn(productList);

        String viewName = productController.productSearch(search, ot, Do, price, contract, model);

        verify(model).addAttribute("search_product", productList);
        assertEquals("/product/product", viewName);
    }

    @Test
    void ShouldHandleErrorGracefully() {
        String search = "test";
        String ot = "not_a_number";
        String Do = "500";
        String price = "sorted_by_ascending_price";
        String contract = "furniture";

        assertThrows(NumberFormatException.class, () -> {
            productController.productSearch(search, ot, Do, price, contract, model);
        });
    }



}