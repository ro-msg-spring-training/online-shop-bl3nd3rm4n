package ro.msg.learning.shop.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.services.LocationService;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/location")
public class LocationController {

    private final LocationService locationService;

    @GetMapping("/{locationId}")
    public ResponseEntity<Object> findById(@PathVariable("locationId") int locationId) {
        return new ResponseEntity<>(locationService.findById(locationId), HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<Object> findAll() {
        return new ResponseEntity<>(locationService.findAll(), HttpStatus.OK);
    }
}
