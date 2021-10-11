package cloud_automaiton.android;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class SauceLabs {

    //Secrets
    private static String USERNAME = "nigel-mulholland";
    private static String ACCESSKEY = "7a5c0e5b-10c6-4f84-908a-d0a75baf323a";
    private static String APP_URL = "b753594a-10f7-4bd8-a5e8-48bbafbad19c";

    public static AndroidDriver  driver;
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

        String sauceUrl = "@ondemand.us-west-1.saucelabs.com:443";
        String SAUCE_REMOTE_URL = "https://" + USERNAME + ":" + ACCESSKEY + sauceUrl +"/wd/hub";
        System.out.println(SAUCE_REMOTE_URL);

        URL url = new URL(SAUCE_REMOTE_URL);

//        DesiredCapabilities capabilities = new DesiredCapabilities();
//        capabilities.setCapability("app", "storage:"+APP_URL);
//        //capabilities.setCapability("appium:deviceName","Samsung Galaxy S9");
//        capabilities.setCapability("deviceName","Android Emulator");
//        capabilities.setCapability("appium:platformVersion","10");
//        capabilities.setCapability("appium:automationName", "UiAutomator2");

        //capabilities.setCapability("autoAcceptAlerts",true); //This is IOs only

        MutableCapabilities capabilities = new MutableCapabilities();
        capabilities.setCapability("deviceName", "Samsung Galaxy S9");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("platformVersion", "10");
        //capabilities.setCapability("app", "storage:filename=ToDo.apk");
        capabilities.setCapability("app", "storage:"+APP_URL);
        //capabilities.setCapability("appiumVersion", "1.21.0");
        capabilities.setCapability("appActivity", "com.jeffprod.todo.ActivityMain");

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
