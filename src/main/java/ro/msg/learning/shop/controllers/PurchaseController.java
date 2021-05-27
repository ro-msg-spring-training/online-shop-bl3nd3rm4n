package ro.msg.learning.shop.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.dtos.OrderInformationDTO;
import ro.msg.learning.shop.services.PurchaseService;

@RestController
@CrossOrigin
@RequestMapping("/purchase")
@AllArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;

    @GetMapping
    public ResponseEntity<Object> findById(int purchaseId) {
        return new ResponseEntity<>(purchaseService.findById(purchaseId), HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<Object> create(@RequestBody OrderInformationDTO orderInformationDTO) {
        return new ResponseEntity<>(purchaseService.create(orderInformationDTO), HttpStatus.OK);
    }
}
