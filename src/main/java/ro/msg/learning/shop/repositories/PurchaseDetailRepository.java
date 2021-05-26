package ro.msg.learning.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ro.msg.learning.shop.models.Location;
import ro.msg.learning.shop.models.Purchase;
import ro.msg.learning.shop.models.PurchaseDetail;

import java.util.List;


@Repository
public interface PurchaseDetailRepository
        extends JpaRepository<PurchaseDetail, Integer>, JpaSpecificationExecutor<PurchaseDetail> {

    List<PurchaseDetail> findAllByPurchaseInAndShippedFrom(Iterable<Purchase> purchases, Location location);
}