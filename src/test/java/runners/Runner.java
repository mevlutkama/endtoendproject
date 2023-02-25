package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(

        features = "./src/test/resources/features",
        glue = "stepdefinitions",
        dryRun = true,
        tags = "@TC03_Api"
)

public class Runner {
}
