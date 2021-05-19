package ro.msg.learning.shop.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.models.Product;
import ro.msg.learning.shop.repositories.ProductRepository;

import java.util.Collection;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class ProductService {

    ProductRepository productRepository;

    public Product findById(int productId) {
        return productRepository.findById(productId).orElseThrow(NoSuchElementException::new);
    }

    public Collection<Product> findAll() {
        return productRepository.findAll();
    }
}
