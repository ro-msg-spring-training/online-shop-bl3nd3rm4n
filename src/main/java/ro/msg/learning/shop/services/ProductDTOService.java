package ro.msg.learning.shop.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.dtos.ProductDTO;
import ro.msg.learning.shop.models.Stock;
import ro.msg.learning.shop.repositories.ProductRepository;
import ro.msg.learning.shop.repositories.StockRepository;
import ro.msg.learning.shop.utils.DTOMapper;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductDTOService {

    ProductRepository productRepository;
    StockRepository stockRepository;

    public ProductDTO findById(int productId) throws NoSuchElementException {
        return DTOMapper.map(productRepository.findById(productId).orElseThrow(NoSuchElementException::new));
    }

    public Collection<ProductDTO> findAll() {
        return productRepository.findAll().stream().map(DTOMapper::map).collect(Collectors.toList());
    }

    public ProductDTO delete(int productId) throws NoSuchElementException {
        ProductDTO productDTO = DTOMapper.map(productRepository.findById(productId).orElseThrow(NoSuchElementException::new));
        List<Stock> stocks = stockRepository.findByProductId(productId);
        for (Stock stock : stocks) {
            stock.setQuantity(0);
        }
        stockRepository.saveAll(stocks);
        return productDTO;
    }

    public ProductDTO create(ProductDTO productDTO) {
        return DTOMapper.map(productRepository.save(DTOMapper.map(productDTO)));
    }

    public ProductDTO update(int productId, ProductDTO productDTO) throws NoSuchElementException {
        DTOMapper.map(productRepository.findById(productId).orElseThrow(NoSuchElementException::new));
        productDTO.setId(productId);
        return DTOMapper.map(productRepository.save(DTOMapper.map(productDTO)));
    }
}
