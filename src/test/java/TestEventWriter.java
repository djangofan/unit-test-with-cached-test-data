
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TestEventWriter {

    private static final String STRING_ARRAY_SAMPLE = "./string-array-sample.csv";

    @Test
    public void testCSVRead() {
        try (
                Writer writer = Files.newBufferedWriter(Paths.get(STRING_ARRAY_SAMPLE));
        ) {
            StatefulBeanToCsv<MyEvent> beanToCsv = new StatefulBeanToCsvBuilder(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .build();

            List<MyEvent> myEvents = new ArrayList<>();
            myEvents.add(new MyEvent("Sundar Pichai ♥", "sundar.pichai@gmail.com", "+1-1111111111", "India"));
            myEvents.add(new MyEvent("Satya Nadella", "satya.nadella@outlook.com", "+1-1111111112", "India"));

            beanToCsv.write(myEvents);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvRequiredFieldEmptyException e) {
            e.printStackTrace();
        } catch (CsvDataTypeMismatchException e) {
            e.printStackTrace();
        }

    }

}