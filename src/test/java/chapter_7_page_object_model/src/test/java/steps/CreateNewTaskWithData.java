package chapter_7_page_object_model.src.test.java.steps;

import chapter_7_page_object_model.src.main.java.pageobjects.CreateTaskPage;
import chapter_7_page_object_model.src.main.java.pageobjects.TasksListPage;
import chapter_7_page_object_model.src.test.java.tests.TestBase;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import java.net.MalformedURLException;

public class CreateNewTaskWithData extends TestBase {

    CreateTaskPage createTaskPage;
    TasksListPage tasksListPage;

    @Given("Click add new Task")
    public void clickAddNewTask() throws MalformedURLException {
        Android_setUp();
        tasksListPage = new TasksListPage(driver);
        createTaskPage = new CreateTaskPage(driver);
        tasksListPage.clickAddTaskBtn();
    }

    @Given("Enter {string} and {string}")
    public void enterAnd(String taskName, String taskDesc) {
        createTaskPage.enterTaskName(taskName);
        createTaskPage.enterTaskDesc(taskDesc);
    }

    @Then("Task Added Successfully")
    public void taskAddedSuccessfully() {
        driver.hideKeyboard();
        tearDown();
    }

}
