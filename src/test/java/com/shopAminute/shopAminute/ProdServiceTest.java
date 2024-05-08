package com.shopAminute.shopAminute;

import com.shopAminute.shopAminute.dataManagers.ProductRepo;
import com.shopAminute.shopAminute.objects.Category;
import com.shopAminute.shopAminute.objects.Product;
import com.shopAminute.shopAminute.сoordinators.ProdService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;


import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProdServiceTest {

    @MockBean
    private ProductRepo productRepo;

    @Autowired
    private ProdService prodService;

    @Test
    public void thenProductRepoFindAll() {
        prodService.getAllProduct();
        verify(productRepo, times(1)).findAll();
    }

    @Test
    public void thenProductRepoFindById() {
        int id = 1;
        prodService.getProductId(id);
        verify(productRepo, times(1)).findById(id);
    }

    @Test
    public void thenProductRepo() {
        Product product = new Product();
        Category category = new Category();
        prodService.saveProduct(product, category);
        verify(productRepo, times(1)).save(product);
    }

    @Test
    public void thenProductRepoSave() {
        int id = 1;
        Product product = new Product();
        prodService.updateProduct(id, product);
        verify(productRepo, times(1)).save(product);
    }

    @Test
    public void thenProductRepoDelete() {
        int id = 1;
        prodService.deleteProduct(id);
        verify(productRepo, times(1)).deleteById(id);
    }
}