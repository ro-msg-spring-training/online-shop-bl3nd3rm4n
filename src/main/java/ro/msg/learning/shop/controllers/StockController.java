package ro.msg.learning.shop.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.models.Stock;
import ro.msg.learning.shop.services.StockService;

import java.util.Collection;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/stock")
public class StockController {

    private final StockService stockService;

    @GetMapping("/{stockId}")
    public ResponseEntity<Stock> findById(@PathVariable("stockId") int stockId) {
        return new ResponseEntity<>(stockService.findById(stockId), HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<Collection<Stock>> findAll() {
        return new ResponseEntity<>(stockService.findAll(), HttpStatus.OK);
    }
}
