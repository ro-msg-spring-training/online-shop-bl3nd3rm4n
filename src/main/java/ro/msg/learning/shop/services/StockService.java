package ro.msg.learning.shop.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.models.Stock;
import ro.msg.learning.shop.repositories.StockRepository;

import java.util.Collection;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class StockService {

    StockRepository stockRepository;

    public Stock findById(int stockId) {
        return stockRepository.findById(stockId).orElseThrow(NoSuchElementException::new);
    }

    public Collection<Stock> findAll() {
        return stockRepository.findAll();
    }
}
