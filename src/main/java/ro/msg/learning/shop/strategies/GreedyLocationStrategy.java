package ro.msg.learning.shop.strategies;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ro.msg.learning.shop.dtos.OrderInformationDTO;
import ro.msg.learning.shop.models.Address;
import ro.msg.learning.shop.models.Location;
import ro.msg.learning.shop.models.Stock;
import ro.msg.learning.shop.repositories.StockRepository;

import java.util.*;
import java.util.stream.Collectors;

public class GreedyLocationStrategy implements LocationSelectionStrategy {

    private static final String KEY = "i6ffMeLGOAvpcuAhJbAhKCLYSJTDgqGB";
    private static final String URL = "https://www.mapquestapi.com/directions/v2/route";

    @Override
    public Collection<Stock> fulfillOrder(OrderInformationDTO orderInformationDTO, StockRepository stockRepository)
            throws NoSuchElementException, JsonProcessingException {

        List<Stock> stocksForOrder = new ArrayList<>();
        Map<Location, Double> locations = new HashMap<>();
        Map<Location, Map<Integer, Stock>> stocksByLocation = new HashMap<>();


        for (Map.Entry<Integer, Integer> productQuantityPair : orderInformationDTO.getProductIdQuantityMap().entrySet()) {
            List<Stock> stockForProduct = stockRepository.findByProductId(productQuantityPair.getKey());
            for (Stock stock : stockForProduct) {
                stocksByLocation.computeIfAbsent(stock.getLocation(), k -> new HashMap<>());
                stocksByLocation.get(stock.getLocation()).put(stock.getProduct().getId(), stock);
                locations.put(stock.getLocation(),
                        getDistance(orderInformationDTO.getAddress(), stock.getLocation().getAddress()));
            }
        }
        List<Location> sortedLocations = locations.entrySet().stream().filter(x -> x.getValue() > 0)
                .sorted(Map.Entry.comparingByValue()).map(Map.Entry::getKey)
                .collect(Collectors.toList());
        for (Map.Entry<Integer, Integer> productQuantityPair : orderInformationDTO.getProductIdQuantityMap().entrySet()) {
            int reservedTotal = 0;
            int needed = productQuantityPair.getValue();
            for (Location location : sortedLocations) {
                if (reservedTotal < needed) {
                    Stock availableStock = stocksByLocation.get(location).get(productQuantityPair.getKey());
                    if (availableStock != null && availableStock.getQuantity() > 0) {
                        int reserved = Math.min(needed - reservedTotal, availableStock.getQuantity());
                        stocksForOrder
                                .add(new Stock(availableStock.getId(), availableStock.getLocation(),
                                        availableStock.getProduct(), reserved));
                        reservedTotal = reservedTotal + reserved;
                    }
                }
                if (reservedTotal == needed) {
                    break;
                }
            }
            if (reservedTotal != needed) {
                throw new NoSuchElementException("At least one of the items is not available for the given quantity.");
            }
        }
        return stocksForOrder;
    }

    private Double getDistance(Address address1, Address address2) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("key", KEY);
        uriVariables.put("from", formatAddress(address1));
        uriVariables.put("to", formatAddress(address2));
        ResponseEntity<String> response = restTemplate.getForEntity(URL +
                        "?key={key}&from={from}&to={to}&outFormat=json&ambiguities" +
                        "=ignore&routeType=fastest&doReverseGeocode=false&enhancedNarrative=false&avoidTimedConditions=false",
                String.class, uriVariables);
        assert (response.getStatusCode() == (HttpStatus.OK));

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        JsonNode distance = root.path("route").path("distance");
        assert (distance != null);
        return distance.asDouble();
    }

    private String formatAddress(Address address) {
        StringBuilder stringBuilder = new StringBuilder();
        if (address.getCountry() != null) {
            stringBuilder.append(address.getCountry().replace(' ', '+'));
        }
        if (address.getCounty() != null) {
            stringBuilder.append('+');
            stringBuilder.append(address.getCounty().replace(' ', '+'));
        }
        if (address.getCity() != null) {
            stringBuilder.append('+');
            stringBuilder.append(address.getCity().replace(' ', '+'));
        }
        if (address.getStreetAddress() != null) {
            stringBuilder.append('+');
            stringBuilder.append(address.getStreetAddress().replace(' ', '+'));
        }
        return stringBuilder.toString();
    }

}

