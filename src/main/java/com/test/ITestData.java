package com.test;

import org.testng.xml.XmlTest;

public interface ITestData {

    // for BeforeClass
    public void setupData(Object[] events, XmlTest xmlTest);

    // for AfterClass
    public void persistData();

}
