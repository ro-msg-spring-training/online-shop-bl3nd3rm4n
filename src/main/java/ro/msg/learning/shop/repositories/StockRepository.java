package ro.msg.learning.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ro.msg.learning.shop.models.Stock;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer>, JpaSpecificationExecutor<Stock> {

    List<Stock> findByProductIdAndQuantityGreaterThanEqual(int productId, int quantity);

    List<Stock> findByProductId(int productId);

    Stock findFirstByProductIdAndQuantityGreaterThanEqualOrderByQuantityDesc(int productId, int quantity);

    Stock findFirstByProductIdAndQuantityGreaterThanEqualAndLocationId(int productId, int locationId, int quantity);


}