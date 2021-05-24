package ro.msg.learning.shop.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.dtos.StockDTO;
import ro.msg.learning.shop.repositories.StockRepository;
import ro.msg.learning.shop.utils.StockDTOMapper;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StockDTOService {

    StockRepository stockRepository;

    public StockDTO findById(int stockId) {
        return StockDTOMapper.mapToStockDTO(stockRepository.findById(stockId).orElseThrow(NoSuchElementException::new));
    }

    public Collection<StockDTO> findByLocationId(int locationId) {
        return stockRepository.findByLocationId(locationId).stream().map(StockDTOMapper::mapToStockDTO)
                .collect(Collectors.toList());
    }

    public Collection<StockDTO> findAll() {
        return stockRepository.findAll().stream().map(StockDTOMapper::mapToStockDTO).collect(Collectors.toList());
    }
}
