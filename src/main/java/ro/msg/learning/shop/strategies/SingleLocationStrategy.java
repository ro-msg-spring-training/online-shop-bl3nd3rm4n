package ro.msg.learning.shop.strategies;

import ro.msg.learning.shop.dtos.OrderInformationDTO;
import ro.msg.learning.shop.models.Stock;
import ro.msg.learning.shop.repositories.StockRepository;

import java.util.*;

public class SingleLocationStrategy implements LocationSelectionStrategy {

    @Override
    public Collection<Stock> fulfillOrder(OrderInformationDTO orderInformationDTO, StockRepository stockRepository)
            throws NoSuchElementException {
        List<Stock> stocksForRarestProduct = orderInformationDTO.getProductIdQuantityMap().entrySet().stream()
                .map(x -> stockRepository.findByProductIdAndQuantityGreaterThanEqual(x.getKey(), x.getValue()))
                .min(Comparator.comparingInt(List::size)).orElse(new ArrayList<>());

        List<Stock> stocksForOrder = null;

        for (Stock stock : stocksForRarestProduct) {
            stocksForOrder = new ArrayList<>();
            for (Map.Entry<Integer, Integer> productQuantityPair :
                    orderInformationDTO.getProductIdQuantityMap().entrySet()) {

                Stock stockForProduct =
                        stockRepository.findFirstByProductIdAndQuantityGreaterThanEqualAndLocationId(
                                productQuantityPair.getKey(), productQuantityPair.getValue(),
                                stock.getLocation().getId());

                if (stockForProduct != null) {
                    stocksForOrder
                            .add(new Stock(stockForProduct.getId(), stockForProduct.getLocation(), stockForProduct.getProduct(),
                                    productQuantityPair.getValue()));
                } else {
                    break;
                }
            }
        }

        if (stocksForOrder != null && stocksForOrder.size() == orderInformationDTO.getProductIdQuantityMap().size()) {
            return stocksForOrder;
        } else {
            throw new NoSuchElementException("At least one of the items is not available for the given quantity.");
        }
    }
}
