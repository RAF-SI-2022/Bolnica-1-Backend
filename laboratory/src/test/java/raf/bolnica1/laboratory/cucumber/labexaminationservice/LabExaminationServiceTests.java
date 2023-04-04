package raf.bolnica1.laboratory.cucumber.labexaminationservice;

import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.ConfigurationParameter;

import static io.cucumber.core.options.Constants.GLUE_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features/labexaminationservice")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "raf.bolnica1.laboratory.cucumber.labexaminationservice")
public class LabExaminationServiceTests {

}
