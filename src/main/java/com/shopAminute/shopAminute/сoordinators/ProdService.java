package com.shopAminute.shopAminute.сoordinators;

import com.shopAminute.shopAminute.objects.Category;
import com.shopAminute.shopAminute.objects.Product;
import com.shopAminute.shopAminute.dataManagers.ProductRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProdService {
    private final ProductRepo productRepo;

    public ProdService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }
    public List<Product> getAllProduct() {
        return productRepo.findAll();
    }

    public Product getProductId(int id) {
        Optional<Product> optionalProduct = productRepo.findById(id);
        return optionalProduct.orElse(null);
    }

    @Transactional
    public void saveProduct(Product product, Category category) {
        product.setCategory(category);
        productRepo.save(product);
    }

    @Transactional
    public void updateProduct(int id, Product product) {
        product.setId(id);
        productRepo.save(product);
    }
    @Transactional
    public void deleteProduct(int id) {
        productRepo.deleteById(id);
    }
}