package ro.msg.learning.shop.utils;

import ro.msg.learning.shop.dtos.StockDTO;
import ro.msg.learning.shop.models.Stock;

public class StockDTOMapper {
    public static StockDTO mapToStockDTO(Stock stock) {
        StockDTO stockDTO = new StockDTO();
        stockDTO.setId(stock.getId());
        stockDTO.setLocationId(stock.getLocation().getId());
        stockDTO.setLocationName(stock.getLocation().getName());
        stockDTO.setProductId(stock.getProduct().getId());
        stockDTO.setProductName(stock.getProduct().getName());
        stockDTO.setSupplierId(stock.getProduct().getSupplier().getId());
        stockDTO.setSupplierName(stock.getProduct().getSupplier().getName());
        stockDTO.setQuantity(stock.getQuantity());
        return stockDTO;
    }
}
