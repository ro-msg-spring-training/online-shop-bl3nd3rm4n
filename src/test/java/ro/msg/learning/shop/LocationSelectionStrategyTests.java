package ro.msg.learning.shop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import ro.msg.learning.shop.dtos.OrderInformationDTO;
import ro.msg.learning.shop.models.*;
import ro.msg.learning.shop.repositories.StockRepository;
import ro.msg.learning.shop.strategies.LocationSelectionStrategy;
import ro.msg.learning.shop.strategies.MostAbundantLocationStrategy;
import ro.msg.learning.shop.strategies.SingleLocationStrategy;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LocationSelectionStrategyTests {

    private final StockRepository stockRepository = Mockito.mock(StockRepository.class);

    Address address1 = new Address(1, "Romania", "Sibiu", "Sibiu", "Gorjului 4");
    Address address2 = new Address(2, "Romania", "Constanta", "Constanta", "Nucilor 1");
    Supplier supplier = new Supplier(1, "supplier");
    ProductCategory productCategory =
            new ProductCategory(1, "productCategory", "productCategoryDescription");
    Product product1 =
            new Product(1, "product1", "", BigDecimal.valueOf(10), 1, "", productCategory, supplier);
    Product product2 =
            new Product(2, "product2", "", BigDecimal.valueOf(20), 2, "", productCategory, supplier);
    Location location1 = new Location(1, "location1", address1);
    Location location2 = new Location(2, "location2", address2);

    List<Stock> stocks = Arrays.asList(
            new Stock(1, location1, product1, 200),
            new Stock(2, location2, product1, 100),
            new Stock(3, location2, product2, 200));


    @Test
    void mostAbundantLocationStrategy() throws JsonProcessingException {
        HashMap<Integer, Integer> productIdQuantityMap = new HashMap<>();
        productIdQuantityMap.put(1, 10);
        productIdQuantityMap.put(2, 20);

        OrderInformationDTO orderDto =
                new OrderInformationDTO(1, address1, null, productIdQuantityMap);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);

        when(stockRepository.findFirstByProductIdAndQuantityGreaterThanEqualOrderByQuantityDesc(anyInt(), anyInt()))
                .thenAnswer(i -> stocks.stream()
                        .filter(stock -> stock.getProduct().getId() == i.getArgument(0, Integer.class) &&
                                stock.getQuantity() >= i.getArgument(1, Integer.class))
                        .max(Comparator.comparing(Stock::getQuantity)).orElse(null));

        LocationSelectionStrategy locationSelectionStrategy = new MostAbundantLocationStrategy();
        Collection<Stock> stocksForOrder = locationSelectionStrategy.fulfillOrder(orderDto, stockRepository);
        assertTrue(stocksForOrder.stream().anyMatch(stock -> stock.getId() == 1 && stock.getQuantity() == 10));
        assertTrue(stocksForOrder.stream().anyMatch(stock -> stock.getId() == 3 && stock.getQuantity() == 20));
    }

    @Test
    void singleLocationStrategy() throws JsonProcessingException {
        HashMap<Integer, Integer> productIdQuantityMap = new HashMap<>();
        productIdQuantityMap.put(1, 10);
        productIdQuantityMap.put(2, 20);

        OrderInformationDTO orderDto =
                new OrderInformationDTO(1, address1, null, productIdQuantityMap);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);

        when(stockRepository.findByProductIdAndQuantityGreaterThanEqual(anyInt(), anyInt()))
                .thenAnswer(i -> stocks.stream()
                        .filter(stock -> stock.getProduct().getId() == i.getArgument(0, Integer.class) &&
                                stock.getQuantity() >= i.getArgument(1, Integer.class))
                        .collect(Collectors.toList()));

        when(stockRepository.findFirstByProductIdAndQuantityGreaterThanEqualAndLocationId(anyInt(), anyInt(), anyInt()))
                .thenAnswer(i -> stocks.stream()
                        .filter(stock -> stock.getProduct().getId() == i.getArgument(0, Integer.class) &&
                                stock.getQuantity() >= i.getArgument(1, Integer.class) &&
                                stock.getLocation().getId() == i.getArgument(2, Integer.class))
                        .findFirst().orElse(null));

        LocationSelectionStrategy locationSelectionStrategy = new SingleLocationStrategy();
        Collection<Stock> stocksForOrder = locationSelectionStrategy.fulfillOrder(orderDto, stockRepository);
        assertTrue(stocksForOrder.stream().anyMatch(stock -> stock.getId() == 2 && stock.getQuantity() == 10));
        assertTrue(stocksForOrder.stream().anyMatch(stock -> stock.getId() == 3 && stock.getQuantity() == 20));
    }

}