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
        Address address1 = addressRepository.save(new Address(1, "Romania", "Sibiu", "Sibiu", "Gorjului 4"));
        Address address2 = addressRepository.save(new Address(2, "Romania", "Constanta", "Constanta", "Nucilor 1"));
        Supplier supplier = supplierRepository.save(new Supplier(1, "supplier"));
        ProductCategory productCategory = productCategoryRepository.save(
                new ProductCategory(1, "productCategory", "productCategoryDescription"));
        Product product1 = productRepository.save(
                new Product(1, "product1", "", BigDecimal.valueOf(10), 1, "", productCategory, supplier));
        Product product2 = productRepository.save(
                new Product(2, "product2", "", BigDecimal.valueOf(20), 2, "", productCategory, supplier));
        Location location1 = locationRepository.save(new Location(1, "location1", address1));
        Location location2 = locationRepository.save(new Location(2, "location2", address2));
        customerRepository.save(new Customer(1, "first", "last", "user", "pass", "email"));
        customerRepository.save(new Customer(2, "tester", "test", "test", "test", "test"));

        stockRepository.save(new Stock(1, location1, product1, 100));
        stockRepository.save(new Stock(2, location2, product1, 100));
        stockRepository.save(new Stock(3, location2, product2, 200));
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
