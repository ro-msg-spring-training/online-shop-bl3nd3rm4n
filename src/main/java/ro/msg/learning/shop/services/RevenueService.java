package ro.msg.learning.shop.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.models.Revenue;
import ro.msg.learning.shop.repositories.RevenueRepository;

import java.util.Collection;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class RevenueService {

    RevenueRepository revenueRepository;

    public Revenue findById(int revenueId) {
        return revenueRepository.findById(revenueId).orElseThrow(NoSuchElementException::new);
    }

    public Collection<Revenue> findAll() {
        return revenueRepository.findAll();
    }
}
