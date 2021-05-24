package ro.msg.learning.shop.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StockDTO {

    private int id;
    private int locationId;
    private String locationName;
    private int productId;
    private String productName;
    private int supplierId;
    private String supplierName;
    private int quantity;

}