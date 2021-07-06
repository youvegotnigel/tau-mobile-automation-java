package chapter_7_page_object_model.src.test.java.runner;

import chapter_7_page_object_model.src.test.java.tests.TestBase;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/resources/features/CreateTaskExampleTable.feature",
         glue = {"steps"},
         plugin = {"pretty", "html:target/cucumber-html-report.html"})
public class TestRunner extends TestBase {

}
