package ro.msg.learning.shop.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.services.PurchaseDetailService;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/purchaseDetail")
public class PurchaseDetailController {

    private final PurchaseDetailService purchaseDetailService;

    @GetMapping("/{purchaseDetailId}")
    public ResponseEntity<Object> findById(@PathVariable("purchaseDetailId") int purchaseDetailId) {
        return new ResponseEntity<>(purchaseDetailService.findById(purchaseDetailId), HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<Object> findAll() {
        return new ResponseEntity<>(purchaseDetailService.findAll(), HttpStatus.OK);
    }
}
