package ro.msg.learning.shop;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import ro.msg.learning.shop.dtos.StockDTO;
import ro.msg.learning.shop.utils.CSVMessageConverter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CSVMessageConverterTests {
    CSVMessageConverter csvMessageConverter;
    String csv;
    List<StockDTO> stockDTOList;

    @BeforeAll
    void setUp() {
        csvMessageConverter = new CSVMessageConverter();
        csv = "id,locationId,locationName,productId,productName,quantity,supplierId,supplierName\n" +
                "1,1,location1,1,product1,100,1,supplier\n" +
                "2,2,location2,1,product1,100,1,supplier\n";
        stockDTOList = new ArrayList<>();
        stockDTOList.add(new StockDTO(1, 1, "location1", 1, "product1", 1, "supplier", 100));
        stockDTOList.add(new StockDTO(2, 2, "location2", 1, "product1", 1, "supplier", 100));
    }

    @Test
    void stockCSVDeserialization() throws IOException {
        assertThat(csvMessageConverter.fromCsv(StockDTO.class, new ByteArrayInputStream(csv.getBytes(StandardCharsets.UTF_8))))
                .isEqualTo(stockDTOList);
    }

    @Test
    void stockCSVSerialization() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        csvMessageConverter.toCsv(StockDTO.class, stockDTOList, byteArrayOutputStream);
        assertThat(byteArrayOutputStream).hasToString(csv);
    }

}