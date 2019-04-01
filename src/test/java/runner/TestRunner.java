package runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
		features = {"src/test/java/Features"},    // src/test/java/Features
		glue = {"stepDefinition"},
		monochrome = true,
		format = { "pretty", "html:target/cucumber-reports", "json:target/cucumber_json.json", "junit:target/cucumber-results.xml"}
		//plugin = {"pretty", "html:target/cucumber-reports","json:target/cucumber_json.json", "junit:target/cucumber-results.xml"},
		//format={"pretty", "html:target/cucumber-reports"}
		)

public class TestRunner {
}
