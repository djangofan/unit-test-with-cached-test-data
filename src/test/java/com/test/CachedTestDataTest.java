package com.test;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class CachedTestDataTest {

    public List<CachedCSVEvent> loadedRecords;
    public Configuration testConfiguraton;

    @BeforeClass
    public void setupClass() {
        testConfiguraton = new Configuration();
        loadedRecords = testConfiguraton.getCache();
        for (CachedCSVEvent event : loadedRecords) {
            System.out.println(event.toString());
        }
    }

    @Test
    public void testCachedTestData() {
        loadedRecords.add(new CachedCSVEvent("Sundar Pichai ♥", "sundar.pichai@gmail.com", "+1-1111111111", "Pakistan"));
        loadedRecords.add(new CachedCSVEvent("Satya Nadella", "satya.nadella@outlook.com", "+1-1111111112", "Pakistan"));
        Assert.assertTrue(true);
    }

    @AfterClass
    public void teardownClass() {
        new Configuration().persistCache(loadedRecords);
    }

}