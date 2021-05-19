package ro.msg.learning.shop.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.models.Supplier;
import ro.msg.learning.shop.repositories.SupplierRepository;

import java.util.Collection;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class SupplierService {

    SupplierRepository supplierRepository;

    public Supplier findById(int supplierId) {
        return supplierRepository.findById(supplierId).orElseThrow(NoSuchElementException::new);
    }

    public Collection<Supplier> findAll() {
        return supplierRepository.findAll();
    }
}
