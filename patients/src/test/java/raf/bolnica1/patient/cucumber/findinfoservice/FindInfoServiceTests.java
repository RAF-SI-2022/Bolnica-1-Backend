package raf.bolnica1.patient.cucumber.findinfoservice;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.core.options.Constants.GLUE_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features/findinfoservice")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "raf.bolnica1.patient.cucumber.findinfoservice")
public class FindInfoServiceTests {
}
