package ro.msg.learning.shop.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.dtos.OrderInformationDTO;
import ro.msg.learning.shop.models.Customer;
import ro.msg.learning.shop.models.Purchase;
import ro.msg.learning.shop.models.PurchaseDetail;
import ro.msg.learning.shop.models.Stock;
import ro.msg.learning.shop.repositories.CustomerRepository;
import ro.msg.learning.shop.repositories.PurchaseDetailRepository;
import ro.msg.learning.shop.repositories.PurchaseRepository;
import ro.msg.learning.shop.repositories.StockRepository;
import ro.msg.learning.shop.strategies.LocationSelectionStrategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final PurchaseDetailRepository purchaseDetailRepository;
    private final StockRepository stockRepository;
    private final CustomerRepository customerRepository;
    private final LocationSelectionStrategy locationSelectionStrategy;

    public Purchase findById(int purchaseId) {
        return purchaseRepository.findById(purchaseId).orElseThrow(NoSuchElementException::new);
    }


    public Purchase create(OrderInformationDTO orderInformationDTO) throws NoSuchElementException, JsonProcessingException {
        Collection<Stock> stocksForOrder = locationSelectionStrategy.fulfillOrder(orderInformationDTO, stockRepository);

        List<Stock> updatedStocks = subtractStocks(stocksForOrder);

        Customer customer = customerRepository
                .findById(orderInformationDTO.getCustomerId())
                .orElseThrow(NoSuchElementException::new);

        Purchase purchase = purchaseRepository
                .save(new Purchase(0, customer, orderInformationDTO.getAddress(), orderInformationDTO.getCreatedAt()));

        purchaseDetailRepository
                .saveAll(stocksForOrder.stream()
                        .map(x -> new PurchaseDetail(0, purchase, x.getLocation(), x.getProduct(), x.getQuantity()))
                        .collect(Collectors.toList()));

        stockRepository.saveAll(updatedStocks);
        return purchase;
    }

    public List<Stock> subtractStocks(Collection<Stock> stocksToSubtract) {
        List<Stock> stocksToBeUpdated = new ArrayList<>();
        for (Stock stockToSubtract : stocksToSubtract) {
            Stock stock = stockRepository.findById(stockToSubtract.getId()).orElseThrow(NoSuchElementException::new);
            stock.setQuantity(stock.getQuantity() - stockToSubtract.getQuantity());
            stocksToBeUpdated.add(stock);
        }
        return stocksToBeUpdated;
    }

}