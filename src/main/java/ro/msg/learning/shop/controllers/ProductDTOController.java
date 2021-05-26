package ro.msg.learning.shop.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.dtos.ProductDTO;
import ro.msg.learning.shop.services.ProductDTOService;

import java.util.Collection;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/product")
public class ProductDTOController {

    private final ProductDTOService productDTOService;

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTO> findById(@PathVariable("productId") int productId) {
        return new ResponseEntity<>(productDTOService.findById(productId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Collection<ProductDTO>> findAll() {
        return new ResponseEntity<>(productDTOService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> create(@RequestBody ProductDTO productDTO) {
        return new ResponseEntity<>(productDTOService.create(productDTO), HttpStatus.OK);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDTO> update(@PathVariable("productId") int productId, @RequestBody ProductDTO productDTO) {
        return new ResponseEntity<>(productDTOService.update(productId, productDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ProductDTO> delete(@PathVariable("productId") int productId) {
        return new ResponseEntity<>(productDTOService.delete(productId), HttpStatus.OK);
    }
}
