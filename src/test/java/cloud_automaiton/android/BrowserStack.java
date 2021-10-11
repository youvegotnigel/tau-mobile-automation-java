package cloud_automaiton.android;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class BrowserStack {

    //Secrets
    private static String USERNAME = "nigelmulholland_Kuw8hX";
    private static String ACCESSKEY = "wEzkdnqVrQ58yhAsFsz7";
    private static String APP_URL = "bs://509272076fef2bab0f82d3463e61ba72eea0dec3";

    public static AppiumDriver driver;
    public static final long WAIT = 30;

    //Locators
    By add_note_btn = By.id("com.jeffprod.todo:id/fab");

    By title_text_field = By.id("com.jeffprod.todo:id/editTextTitre");
    By note_text_field = By.id("com.jeffprod.todo:id/editTextNote");

    By tag_popup = By.id("com.jeffprod.todo:id/editTextTag");
    By priority_popup = By.id("com.jeffprod.todo:id/editTextPriority");

    By start_date = By.id("com.jeffprod.todo:id/buttonStartDate");
    By due_date = By.id("com.jeffprod.todo:id/buttonDeadline");

    By ok_btn = By.id("android:id/button1");
    By save_btn = By.id("com.jeffprod.todo:id/action_save");

    By medium_priority = By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.CheckedTextView[3]");
    By work_tag = By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout[1]/android.widget.ListView/android.widget.CheckedTextView[4]");

    By note_title_name = By.id("com.jeffprod.todo:id/textViewListView");
    By note_check_box = By.id("com.jeffprod.todo:id/checkBoxListView");

    @BeforeMethod
    public static void setup() throws MalformedURLException {
        String browserStackUrl = "@hub-cloud.browserstack.com/wd/hub";
        String BROWSERSTACK_REMOTE_URL = "https://" + USERNAME + ":" + ACCESSKEY + browserStackUrl;
        System.out.println("BROWSERSTACK REMOTE URL ::: " + BROWSERSTACK_REMOTE_URL);

        URL url = new URL(BROWSERSTACK_REMOTE_URL);

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("os_version", "11.0");
        capabilities.setCapability("device", "Samsung Galaxy S21 Ultra");
        capabilities.setCapability("app", APP_URL);
        capabilities.setCapability("browserstack.acceptInsecureCerts", "false");
        capabilities.setCapability("browserstack.debug", "true");
        capabilities.setCapability("browserstack.networkLogs", "true");

        driver = new AndroidDriver(url, capabilities);
    }

    @Test
    public void createNote(){

        //Click on Add button
        click(add_note_btn);

        //Set title
        sendText(title_text_field,"My Title");

        //Set Note
        sendText(note_text_field,"My Note");

        //Select a tag
        click(tag_popup);
        click(work_tag);
        click(ok_btn);

        //Select priority
        click(priority_popup);
        click(medium_priority);
        click(ok_btn);

        //Hide keyboard
        hideKeyboard();

        //Click Save
        click(save_btn);

        Assert.assertEquals("My Title", getText(note_title_name), "Added note not available");

        //check note
        setCheckBox(note_check_box);
        delay(1000);

        //note should be removed
        Assert.assertFalse(isDisplayed(note_title_name));
    }


    @AfterMethod
    public static void tearDown() {
        if (null != driver) {
            driver.quit();
        }
    }

    public void waitForVisibility(By by) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, WAIT);
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(by)));
        }catch (Exception e){
            System.out.println("Element not found ::: " + by);
        }
    }

    public void click(By by) {
        waitForVisibility(by);
        driver.findElement(by).click();
    }

    public void setCheckBox(By by) {
        waitForVisibility(by);
        driver.findElement(by).click();
    }

    public void sendText(By by, String text) {
        waitForVisibility(by);
        driver.findElement(by).sendKeys(text);
    }

    public String getText(By by) {
        waitForVisibility(by);
        return  driver.findElement(by).getText();
    }

    public boolean isDisplayed(By by){
        //waitForVisibility(by);
        try {
            return  driver.findElement(by).isDisplayed();
        }catch (NoSuchElementException e){
            System.out.println("Element not found ::: " + by);
            return false;
        }
    }

    public void delay(int n){
        try {
            Thread.sleep(n);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void hideKeyboard() {
       driver.hideKeyboard();
    }
}
