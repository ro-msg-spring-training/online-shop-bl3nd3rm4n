package ro.msg.learning.shop.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.services.RevenueService;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/revenue")
public class RevenueController {

    private final RevenueService revenueService;

    @GetMapping("/{revenueId}")
    public ResponseEntity<Object> findById(@PathVariable("revenueId") int revenueId) {
        return new ResponseEntity<>(revenueService.findById(revenueId), HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<Object> findAll() {
        return new ResponseEntity<>(revenueService.findAll(), HttpStatus.OK);
    }
}
