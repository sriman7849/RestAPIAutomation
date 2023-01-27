package Options;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions
        (
                features = "src/feature",
                glue = {"stepdefination"},
                plugin = "json:target/jsonReport/Cucumber-report"/*Path folder for reporting in json format*/
               // plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"}
                //tags = {"@DeletePlace"} /*Can be provided to run single scenario using tags*/
        )
public class TestRunner {

}
