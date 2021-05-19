package ro.msg.learning.shop.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.models.Location;
import ro.msg.learning.shop.repositories.LocationRepository;

import java.util.Collection;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class LocationService {

    LocationRepository locationRepository;

    public Location findById(int locationId) {
        return locationRepository.findById(locationId).orElseThrow(NoSuchElementException::new);
    }

    public Collection<Location> findAll() {
        return locationRepository.findAll();
    }
}
