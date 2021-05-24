package ro.msg.learning.shop.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
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
    public ResponseEntity<String> findById(int purchaseId) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(purchaseService.findById(purchaseId));
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(json);
    }

    @PostMapping("/new")
    public ResponseEntity<String> create(@RequestBody OrderInformationDTO orderInformationDTO) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(purchaseService.create(orderInformationDTO));
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(json);
    }
}
