package ro.msg.learning.shop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ro.msg.learning.shop.dtos.OrderInformationDTO;
import ro.msg.learning.shop.repositories.AddressRepository;

import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = ShopApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestPropertySource(locations = "classpath:application-test.properties")
class PurchaseIntegrationTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private AddressRepository addressRepository;

    @BeforeEach
    void setUp() throws Exception {
        mvc.perform(get("/test/populate"));
    }

    @AfterEach
    void tearDown() throws Exception {
        mvc.perform(get("/test/deleteAll"));
    }

    @Test
    void createNewPurchaseSuccessfully() throws Exception {
        HashMap<Integer, Integer> productIdQuantityMap = new HashMap<>();
        productIdQuantityMap.put(1, 10);
        productIdQuantityMap.put(2, 20);

        OrderInformationDTO orderDto =
                new OrderInformationDTO(1, addressRepository.findById(1).get(), null, productIdQuantityMap);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(orderDto);

        mvc.perform(post("/purchase/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.customer.id").value(1))
                .andExpect(jsonPath("$.customer.firstName").value("first"))
                .andExpect(jsonPath("$.customer.lastName").value("last"));
    }

    @Test
    void failToCreatePurchaseDueToInsufficientStock() throws Exception {
        HashMap<Integer, Integer> productIdQuantityMap = new HashMap<>();
        productIdQuantityMap.put(1, 100);
        productIdQuantityMap.put(1, 2000);

        OrderInformationDTO orderDto =
                new OrderInformationDTO(1, addressRepository.findById(1).get(), null, productIdQuantityMap);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(orderDto);

        mvc.perform(post("/purchase/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isNotFound());
    }
}