package ro.msg.learning.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ro.msg.learning.shop.models.ProductCategory;


@Repository
public interface ProductCategoryRepository
        extends JpaRepository<ProductCategory, Integer>, JpaSpecificationExecutor<ProductCategory> {

}