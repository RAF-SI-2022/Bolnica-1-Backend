package raf.bolnica1.employees.controllerTest;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.core.options.Constants.GLUE_PROPERTY_NAME;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features/employee_controller")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "raf.bolnica1.employees.controllerTest")
public class EmployeeControllerTests {

}
