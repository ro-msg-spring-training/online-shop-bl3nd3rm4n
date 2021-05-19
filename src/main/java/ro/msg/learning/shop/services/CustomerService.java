package ro.msg.learning.shop.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.models.Customer;
import ro.msg.learning.shop.repositories.CustomerRepository;

import java.util.Collection;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class CustomerService {

    CustomerRepository customerRepository;

    public Customer findById(int customerId) {
        return customerRepository.findById(customerId).orElseThrow(NoSuchElementException::new);
    }

    public Collection<Customer> findAll() {
        return customerRepository.findAll();
    }
}
