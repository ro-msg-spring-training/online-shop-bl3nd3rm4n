package ro.msg.learning.shop.services;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.repositories.CustomerRepository;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor




public class CustomerDetailService implements UserDetailsService {
    CustomerRepository customerRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return customerRepository.findByUsername(username).orElseThrow(NoSuchElementException::new);

    }
}
