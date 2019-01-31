
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

public class TestReadCSVToBeans {

    private static final String SAMPLE_CSV_FILE_PATH = "target/test-classes/events.csv";

    @Test
    public void testCSVReadToBeans() {
        try (
                Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
        ) {
            ColumnPositionMappingStrategy strategy = new ColumnPositionMappingStrategy();
            strategy.setType(MyEvent.class);
            String[] memberFieldsToBindTo = {"name", "email", "phoneNo", "country"};
            strategy.setColumnMapping(memberFieldsToBindTo);

            CsvToBean<MyEvent> csvToBean = new CsvToBeanBuilder(reader)
                    .withMappingStrategy(strategy)
                    .withSkipLines(1)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            Iterator<MyEvent> myUserIterator = csvToBean.iterator();

            while (myUserIterator.hasNext()) {
                MyEvent myEvent = myUserIterator.next();
                System.out.println("Name : " + myEvent.getName());
                System.out.println("Email : " + myEvent.getEmail());
                System.out.println("PhoneNo : " + myEvent.getPhoneNo());
                System.out.println("Country : " + myEvent.getCountry());
                System.out.println("---------------------------");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}