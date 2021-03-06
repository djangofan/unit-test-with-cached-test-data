package com.test;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.bean.*;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class TestDataSetup {

    private static final Path SHARED_CACHE = Paths.get("target/test-events.csv");
    private static final Path DEFAULT_CACHE = Paths.get("target/test-classes/test-events.csv");

    private List<CachedCSVEvent> loadedRecords;
    private StatefulBeanToCsv<CachedCSVEvent> beanToCsv;

    private ColumnPositionMappingStrategy<CachedCSVEvent> beanStrategy = new ColumnPositionMappingStrategy<>();

    TestDataSetup() {
        beanStrategy.setType(CachedCSVEvent.class);
        beanStrategy.setColumnMapping(new String[]{"name", "location", "id", "created"});
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
                CSVReader csvReader = new CSVReaderBuilder(reader).build()
        ) {
            //HeaderColumnNameMappingStrategy<CachedCSVEvent> castleStrategy = new HeaderColumnNameMappingStrategy<>();
            //castleStrategy.setType(CachedCSVEvent.class);
            //CsvToBean csvToBean = new CsvToBeanBuilder(reader)
            //        .withType(CachedCSVEvent.class)
            //        .withMappingStrategy(castleStrategy)
            //        .withIgnoreLeadingWhiteSpace(true)
            //        .build();
            loadedRecords = new ArrayList<>();
            List<String[]> rawRecords = csvReader.readAll();
            for (String[] record : rawRecords) {
                Instant timeStamp = Instant.now().minus(Period.ofDays(2));
                try {
                    timeStamp = Instant.parse(record[3]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                CachedCSVEvent row = new CachedCSVEvent(record[0], record[1], record[2], timeStamp.toString());
                Duration between = Duration.between(timeStamp, Instant.now());
                if (between.getSeconds() < Duration.ofDays(1).getSeconds()) {
                    loadedRecords.add(row);
                }
            }
        }
        return loadedRecords;
    }

    public void persistCache(List<CachedCSVEvent> loadedRecords) {
        try (
                Writer writer = Files.newBufferedWriter(SHARED_CACHE);
        ) {
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
