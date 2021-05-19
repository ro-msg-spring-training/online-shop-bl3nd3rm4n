package ro.msg.learning.shop.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.models.Address;
import ro.msg.learning.shop.repositories.AddressRepository;

import java.util.Collection;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class AddressService {

    AddressRepository addressRepository;

    public Address findById(int addressId) {
        return addressRepository.findById(addressId).orElseThrow(NoSuchElementException::new);
    }

    public Collection<Address> findAll() {
        return addressRepository.findAll();
    }
}
