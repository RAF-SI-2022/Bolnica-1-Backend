package raf.bolnica1.infirmary.integration.hospitalization;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.core.options.Constants.GLUE_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features/hospitalization-integration")
@ConfigurationParameter(key=GLUE_PROPERTY_NAME,value="raf.bolnica1.infirmary.integration.hospitalization")
public class HospitalizationIntegrationTest {
}
