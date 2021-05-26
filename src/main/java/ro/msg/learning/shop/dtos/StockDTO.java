package ro.msg.learning.shop.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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