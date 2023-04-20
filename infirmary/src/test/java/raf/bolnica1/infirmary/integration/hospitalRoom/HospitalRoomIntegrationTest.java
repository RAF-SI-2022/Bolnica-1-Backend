package raf.bolnica1.infirmary.integration.hospitalRoom;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.core.options.Constants.GLUE_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features/hospitalRoom-integration")
@ConfigurationParameter(key=GLUE_PROPERTY_NAME,value="raf.bolnica1.infirmary.integration.hospitalRoom")
public class HospitalRoomIntegrationTest {
}
