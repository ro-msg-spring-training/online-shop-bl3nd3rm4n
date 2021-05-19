package ro.msg.learning.shop.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.models.PurchaseDetail;
import ro.msg.learning.shop.repositories.PurchaseDetailRepository;

import java.util.Collection;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class PurchaseDetailService {

    PurchaseDetailRepository purchaseDetailRepository;

    public PurchaseDetail findById(int purchaseDetailId) {
        return purchaseDetailRepository.findById(purchaseDetailId).orElseThrow(NoSuchElementException::new);
    }

    public Collection<PurchaseDetail> findAll() {
        return purchaseDetailRepository.findAll();
    }
}
