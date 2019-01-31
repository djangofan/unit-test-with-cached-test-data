package com.test;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.xml.XmlTest;

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
        loadedRecords.add(new CachedCSVEvent("2016-11-16T06:43:19.769", "Location5", "Name5", "id5"));
        loadedRecords.add(new CachedCSVEvent("2016-11-16T06:43:19.769", "Location6", "Name6", "id6n"));
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