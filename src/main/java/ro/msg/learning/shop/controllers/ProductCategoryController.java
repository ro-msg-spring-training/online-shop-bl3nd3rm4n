package ro.msg.learning.shop.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.services.ProductCategoryService;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/productCategory")
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    @GetMapping("/{productCategoryId}")
    public ResponseEntity<Object> findById(@PathVariable("productCategoryId") int productCategoryId) {
        return new ResponseEntity<>(productCategoryService.findById(productCategoryId), HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<Object> findAll() {
        return new ResponseEntity<>(productCategoryService.findAll(), HttpStatus.OK);
    }
}
