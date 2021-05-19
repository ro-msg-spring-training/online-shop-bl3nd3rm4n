package ro.msg.learning.shop.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.models.ProductCategory;
import ro.msg.learning.shop.repositories.ProductCategoryRepository;

import java.util.Collection;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class ProductCategoryService {

    ProductCategoryRepository productCategoryRepository;

    public ProductCategory findById(int productCategoryId) {
        return productCategoryRepository.findById(productCategoryId).orElseThrow(NoSuchElementException::new);
    }

    public Collection<ProductCategory> findAll() {
        return productCategoryRepository.findAll();
    }
}
