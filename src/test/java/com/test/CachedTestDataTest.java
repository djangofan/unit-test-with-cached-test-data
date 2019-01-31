package com.test;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.xml.XmlTest;

import java.util.List;
import java.util.Objects;

public class CachedTestDataTest implements ITestData {

    public List<CachedCSVEvent> loadedRecords;
    public Configuration testConfiguraton;

    @BeforeClass
    public void setupClass() {
        testConfiguraton = new Configuration();

        XmlTest xmlTest = null;
        String[] myEventArray = {"event1","event2"};
        setupData(myEventArray, xmlTest);
    }

    @Test
    public void testCachedTestData() {
        loadedRecords.add(new CachedCSVEvent("Sundar Pichai ♥", "sundar.pichai@gmail.com", "+1-1111111111", "Pakistan"));
        loadedRecords.add(new CachedCSVEvent("Satya Nadella", "satya.nadella@outlook.com", "+1-1111111112", "Pakistan"));
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