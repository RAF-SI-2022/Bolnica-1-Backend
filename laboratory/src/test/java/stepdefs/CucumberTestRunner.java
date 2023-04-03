package stepdefs;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import raf.bolnica1.laboratory.services.lab.impl.LabWorkOrdersServiceImpl;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepdefs"}
)
@SpringBootTest
@ContextConfiguration(classes = {LabWorkOrdersServiceImpl.class})
public class CucumberTestRunner {
    // IT DON'T WORK
}
