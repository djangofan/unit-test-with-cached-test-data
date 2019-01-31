package com.test;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.xml.XmlTest;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

public class TestDataTestSetup implements ITestData {

    public List<CachedCSVEvent> loadedRecords;
    public TestDataSetup testConfiguraton;

    @BeforeClass
    public void setupClass() {
        testConfiguraton = new TestDataSetup();

        XmlTest xmlTest = null;
        String[] myEventArray = {"event1","event2"};
        setupData(myEventArray, xmlTest);
    }

    @Test
    public void testCachedTestData() {
        loadedRecords.add(new CachedCSVEvent("Event5", "Location5", "StubId5", Instant.now().toString()));
        loadedRecords.add(new CachedCSVEvent("Event6", "Location6", "StubId6", Instant.now().toString()));
        Assert.assertTrue(true);
    }

    @AfterClass
    public void teardownClass() {
        persistData();
    }

    @Override
    public void setupData(String[] events, XmlTest xmlTest) {
        if (Objects.isNull(xmlTest)) {
            loadedRecords = testConfiguraton.getCache();
            for (CachedCSVEvent event : loadedRecords) {
                System.out.println(event.toString());
            }
        } else {
            // assume context is being ran from a testng xml file and do whatever
        }
    }

    @Override
    public void persistData() {
        testConfiguraton.persistCache(loadedRecords);
    }
}