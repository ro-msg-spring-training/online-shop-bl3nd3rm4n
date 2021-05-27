package ro.msg.learning.shop.utils;

import ro.msg.learning.shop.dtos.ProductDTO;
import ro.msg.learning.shop.models.Product;
import ro.msg.learning.shop.models.ProductCategory;

public class DTOMapper {
    public static ProductDTO map(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setWeight(product.getWeight());
        productDTO.setImageURL(product.getImageURL());
        productDTO.setProductCategoryId(product.getProductCategory().getId());
        productDTO.setProductCategoryName(product.getProductCategory().getName());
        productDTO.setProductCategoryDescription(product.getProductCategory().getDescription());
        productDTO.setSupplier(product.getSupplier());
        return productDTO;
    }

    public static Product map(ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setWeight(productDTO.getWeight());
        product.setImageURL(productDTO.getImageURL());
        product.setProductCategory(
                new ProductCategory(productDTO.getProductCategoryId(), productDTO.getProductCategoryName(),
                        productDTO.getProductCategoryDescription()));
        product.setSupplier(productDTO.getSupplier());
        return product;
    }
}
