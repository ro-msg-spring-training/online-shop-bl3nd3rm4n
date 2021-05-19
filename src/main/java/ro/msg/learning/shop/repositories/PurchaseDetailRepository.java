package ro.msg.learning.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ro.msg.learning.shop.models.PurchaseDetail;


@Repository
public interface PurchaseDetailRepository
        extends JpaRepository<PurchaseDetail, Integer>, JpaSpecificationExecutor<PurchaseDetail> {

}