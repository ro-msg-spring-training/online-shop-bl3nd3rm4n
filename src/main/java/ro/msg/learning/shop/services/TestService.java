package ro.msg.learning.shop.services;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.models.*;
import ro.msg.learning.shop.repositories.*;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
@Profile("test")
public class TestService {
    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;
    private final LocationRepository locationRepository;
    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final SupplierRepository supplierRepository;
    private final StockRepository stockRepository;
    private final PurchaseRepository purchaseRepository;
    private final PurchaseDetailRepository purchaseDetailRepository;


    public void populate() {
        Address address = addressRepository.save(new Address(0, "address", "city", "county", "street"));
        Supplier supplier = supplierRepository.save(new Supplier(0, "supplier"));
        ProductCategory productCategory = productCategoryRepository.save(
                new ProductCategory(0, "productCategory", "productCategoryDescription"));
        Product product1 = productRepository.save(
                new Product(0, "product1", "", BigDecimal.valueOf(10), 1, "", productCategory, supplier));
        Product product2 = productRepository.save(
                new Product(0, "product2", "", BigDecimal.valueOf(20), 2, "", productCategory, supplier));
        Location location = locationRepository.save(new Location(0, "location", address));
        customerRepository.save(new Customer(0, "first", "last", "user", "pass", "email"));

        stockRepository.save(new Stock(0, location, product1, 100));
        stockRepository.save(new Stock(0, location, product2, 200));
    }

    public void deleteAll() {
        purchaseDetailRepository.deleteAll();
        purchaseRepository.deleteAll();
        stockRepository.deleteAll();
        locationRepository.deleteAll();
        addressRepository.deleteAll();
        customerRepository.deleteAll();
        productRepository.deleteAll();
        productCategoryRepository.deleteAll();
        supplierRepository.deleteAll();
    }
}
