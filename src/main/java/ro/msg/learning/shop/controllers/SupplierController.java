package ro.msg.learning.shop.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.services.SupplierService;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/supplier")
public class SupplierController {

    private final SupplierService supplierService;

    @GetMapping("/{supplierId}")
    public ResponseEntity<Object> findById(@PathVariable("supplierId") int supplierId) {
        return new ResponseEntity<>(supplierService.findById(supplierId), HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<Object> findAll() {
        return new ResponseEntity<>(supplierService.findAll(), HttpStatus.OK);
    }
}
