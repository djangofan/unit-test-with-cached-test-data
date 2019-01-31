package com.test;

import org.testng.xml.XmlTest;

public interface ITestData {

    // for BeforeClass
    public void setupData(String[] events, XmlTest xmlTest);

    // for AfterClass
    public void persistData();

}
