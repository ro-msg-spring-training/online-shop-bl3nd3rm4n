package ro.msg.learning.shop.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.msg.learning.shop.models.Address;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderInformationDTO implements Serializable {

    private int customerId;
    private Address address;
    private LocalDateTime createdAt;
    private Map<Integer, Integer> productIdQuantityMap;

}
