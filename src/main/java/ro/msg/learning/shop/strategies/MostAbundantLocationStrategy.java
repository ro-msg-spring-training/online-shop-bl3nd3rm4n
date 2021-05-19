package ro.msg.learning.shop.strategies;

import ro.msg.learning.shop.dtos.OrderInformationDTO;
import ro.msg.learning.shop.models.Stock;
import ro.msg.learning.shop.repositories.StockRepository;

import java.util.*;

public class MostAbundantLocationStrategy implements LocationSelectionStrategy {

    @Override
    public Collection<Stock> fulfillOrder(OrderInformationDTO orderInformationDTO, StockRepository stockRepository)
            throws NoSuchElementException {

        List<Stock> stocksForOrder = new ArrayList<>();
        for (Map.Entry<Integer, Integer> productQuantityPair : orderInformationDTO.getProductIdQuantityMap().entrySet()) {

            Stock stockForProduct = stockRepository
                    .findFirstByProductIdAndQuantityGreaterThanEqualOrderByQuantityDesc(productQuantityPair.getKey(),
                            productQuantityPair.getValue());

            if (stockForProduct != null) {
                stocksForOrder
                        .add(new Stock(stockForProduct.getId(), stockForProduct.getLocation(), stockForProduct.getProduct(),
                                productQuantityPair.getValue()));
            } else {
                break;
            }
        }

        if (stocksForOrder.size() == orderInformationDTO.getProductIdQuantityMap().size()) {
            return stocksForOrder;
        } else {
            throw new NoSuchElementException("At least one of the items is not available for the given quantity.");
        }
    }
}
