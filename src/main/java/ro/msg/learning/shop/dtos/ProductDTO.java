package ro.msg.learning.shop.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import ro.msg.learning.shop.models.Supplier;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ProductDTO {

    private int id;
    private String name;
    private String description;
    private BigDecimal price;
    private double weight;
    private String imageURL;
    private int productCategoryId;
    private String productCategoryName;
    private String productCategoryDescription;
    private Supplier supplier;

}