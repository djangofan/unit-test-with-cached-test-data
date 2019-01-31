package com.test;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TestDataSetup {

    private static final Path SHARED_CACHE = Paths.get("target/test-events.csv");
    private static final Path DEFAULT_CACHE = Paths.get("target/test-classes/test-events.csv");

    private List<CachedCSVEvent> loadedRecords;
    private StatefulBeanToCsv<CachedCSVEvent> beanToCsv;

    TestDataSetup() {
        if (loadedRecords == null) {
            try {
                loadedRecords = loadCache(SHARED_CACHE);
            } catch (IOException e) {
                loadedRecords = new ArrayList<>();
            }
            if (loadedRecords.size() == 0) {
                try {
                    loadedRecords = loadCache(DEFAULT_CACHE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<CachedCSVEvent> getCache() {
        return loadedRecords;
    }

    public List<CachedCSVEvent> loadCache(Path from) throws IOException {
        try (
                Reader reader = Files.newBufferedReader(from);
                CSVReader csvReader = new CSVReaderBuilder(reader)
                        .withSkipLines(1)
                        .build()
        ) {
            loadedRecords = new ArrayList<>();
            List<String[]> rawRecords = csvReader.readAll();
            for (String[] record : rawRecords) {
                CachedCSVEvent row = new CachedCSVEvent(record[0], record[1], record[2], record[3]);
                loadedRecords.add(row);
            }
        }
        return loadedRecords;
    }

    public void persistCache(List<CachedCSVEvent> loadedRecords) {
        try (
                Writer writer = Files.newBufferedWriter(SHARED_CACHE);
        ) {
            ColumnPositionMappingStrategy<CachedCSVEvent> beanStrategy = new ColumnPositionMappingStrategy<>();
            beanStrategy.setType(CachedCSVEvent.class);
            beanStrategy.setColumnMapping(new String[]{"name", "location", "id", "created"});

            beanToCsv = new StatefulBeanToCsvBuilder(writer)
                    .withMappingStrategy(beanStrategy)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .build();
            beanToCsv.write(loadedRecords);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvRequiredFieldEmptyException e) {
            e.printStackTrace();
        } catch (CsvDataTypeMismatchException e) {
            e.printStackTrace();
        }
    }

}
