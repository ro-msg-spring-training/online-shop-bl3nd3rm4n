package ro.msg.learning.shop.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.dtos.StockDTO;
import ro.msg.learning.shop.services.StockDTOService;

import java.util.Collection;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping(value = "/stock")
public class StockDTOController {

    private final StockDTOService stockDTOService;
    public static final Class MANAGED_CLASS = StockDTO.class;

    @GetMapping(value = "/{locationId}", produces = "text/plain")
    public ResponseEntity<Collection<StockDTO>> findByLocationId(@PathVariable("locationId") int locationId) {
        return new ResponseEntity<>(stockDTOService.findByLocationId(locationId), HttpStatus.OK);
    }

    @GetMapping(produces = "text/plain")
    public ResponseEntity<Collection<StockDTO>> findAll() {
        return new ResponseEntity<>(stockDTOService.findAll(), HttpStatus.OK);
    }

    @PostMapping(consumes = "text/plain")
    public ResponseEntity<Collection<StockDTO>> convertCsvToStockDTOs(@RequestBody Collection<StockDTO> stockDTO) {
        return new ResponseEntity<>(stockDTO, HttpStatus.OK);
    }
}
