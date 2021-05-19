package ro.msg.learning.shop.strategies;

import ro.msg.learning.shop.dtos.OrderInformationDTO;
import ro.msg.learning.shop.models.Stock;
import ro.msg.learning.shop.repositories.StockRepository;

import java.util.Collection;
import java.util.NoSuchElementException;

public interface LocationSelectionStrategy {

    Collection<Stock> fulfillOrder(OrderInformationDTO orderInformationDTO, StockRepository stockRepository)
            throws NoSuchElementException;

}
