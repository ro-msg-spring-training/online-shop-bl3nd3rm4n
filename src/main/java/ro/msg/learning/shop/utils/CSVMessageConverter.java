package ro.msg.learning.shop.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractGenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.controllers.StockDTOController;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

@Component
public class CSVMessageConverter extends AbstractGenericHttpMessageConverter {

    private static final char DELIMITER = ',';
    private static final char QUOTE = '"';
    private static final String LINE_SEP = "\n";

    @Override
    public List<MediaType> getSupportedMediaTypes(Class clazz) {
        return super.getSupportedMediaTypes(clazz);
    }

    public CSVMessageConverter() {
        super(new MediaType("text", "plain"));
    }

    @Override
    protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        if (object instanceof List) {
            toCsv((Class<?>) ((ParameterizedType) type).getActualTypeArguments()[0], (List) object, outputMessage.getBody());
        } else {
            toCsv((Class<?>) type, (List) Collections.singletonList(object), outputMessage.getBody());
        }
    }

    @Override
    public Object read(Type type, Class contextClass, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {
        if (StockDTOController.class.isAssignableFrom(contextClass)) {
            return readInternal(StockDTOController.MANAGED_CLASS, inputMessage);
        } else {
            throw new HttpMessageNotReadableException("", inputMessage);
        }
    }

    @Override
    protected Object readInternal(Class clazz, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {
        return fromCsv(clazz, inputMessage.getBody());
    }

    public <T> List<T> fromCsv(Class<T> clazz, InputStream inputStream) throws IOException {
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema schema = getSchema(clazz, csvMapper);

        ObjectReader reader = csvMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .readerFor(clazz)
                .with(schema);

        MappingIterator<T> iterator = reader.readValues(inputStream);
        return iterator.readAll();
    }


    public <T> void toCsv(Class<T> clazz, List<T> objectList, OutputStream outputStream) throws IOException {
        if (!objectList.isEmpty()) {
            CsvMapper mapper = new CsvMapper();
            CsvSchema schema = getSchema(clazz, mapper);

            String csv = mapper.writer(schema).writeValueAsString(objectList.remove(0));
            outputStream.write(csv.getBytes(StandardCharsets.UTF_8));

            schema = schema.withoutHeader();

            for (T object : objectList) {
                csv = mapper.writer(schema).writeValueAsString(object);
                outputStream.write(csv.getBytes(StandardCharsets.UTF_8));
            }
            outputStream.flush();
            outputStream.close();
        }
    }

    private <T> CsvSchema getSchema(Class<T> clazz, CsvMapper csvMapper) {
        return csvMapper.schemaFor(clazz)
                .withHeader()
                .withColumnSeparator(DELIMITER)
                .withQuoteChar(QUOTE)
                .withLineSeparator(LINE_SEP);
    }

}
