package cloud_automaiton.ios;

import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

//Todo : Create tunnel via cmd : bin/sc -u nigel-mulholland -k 7a5c0e5b-10c6-4f84-908a-d0a75baf323a -r 'us-west' --tunnel-name nigel-tunnel
public class SauceLabs {

    //Secrets
    private static String USERNAME = "nigel-mulholland";
    private static String ACCESSKEY = "7a5c0e5b-10c6-4f84-908a-d0a75baf323a";
    private static String APP_URL = "8c51d92e-b0c4-429e-ba13-9fedc0fcf489";
    private static String APP_NAME = "DOCit_Dev-2.4.10-1088-b53b6e9.ipa";

    private static ThreadLocal<IOSDriver> iOSDriver = new ThreadLocal<IOSDriver>();
    public static final long WAIT = 30;

    //Locators
    By username_text_box = By.xpath("//*[@type = 'XCUIElementTypeTextField']");
    By password_text_box = By.xpath("//*[@type = 'XCUIElementTypeSecureTextField']");
    By login_btn = By.xpath("//*[@type = 'XCUIElementTypeButton' and @label = 'Login' and @name = 'Login']");

    @BeforeMethod
    public static void setup() throws MalformedURLException {

        String sauceUrl = "@ondemand.us-west-1.saucelabs.com:443";
        String SAUCE_REMOTE_URL = "https://" + USERNAME + ":" + ACCESSKEY + sauceUrl +"/wd/hub";
        System.out.println(SAUCE_REMOTE_URL);

        URL url = new URL(SAUCE_REMOTE_URL);

        MutableCapabilities capabilities = new MutableCapabilities();
        capabilities.setCapability("deviceName", "iPhone XR");
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("platformVersion", "15.0.1");
        //capabilities.setCapability("app", "storage:filename="+APP_NAME);
        capabilities.setCapability("app", "storage:"+APP_URL);
        capabilities.setCapability("autoAcceptAlerts", true);
        capabilities.setCapability("tunnelIdentifier", "nigel-tunnel");

        iOSDriver.set(new IOSDriver(url, capabilities));
    }

    @Test
    public void poc(){
        System.out.println("iOS App is open");
        //Login
        sendText(username_text_box,"tvcc.autosysadmin");
        sendText(password_text_box,"tested");
        click(login_btn);
        delay(10000);
        click(login_btn);
        delay(50000);
        Assert.assertTrue(getIosDriver().findElementByAccessibilityId("AS").isDisplayed());
    }

    @AfterMethod
    public void teardown(ITestResult result) {
        ((JavascriptExecutor)getIosDriver()).executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));
        getIosDriver().quit();
    }

    public  IOSDriver getIosDriver() {
        return iOSDriver.get();
    }

    public WebElement getWebElementByByAccessibilityId(String id){
        return (WebElement) getIosDriver().findElementByAccessibilityId(id);
    }

    public void waitForVisibility(By by) {
        IOSDriver driver = getIosDriver();
        try {
            WebDriverWait wait = new WebDriverWait(driver, WAIT);
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(by)));
        }catch (Exception e){
            System.out.println("Element not found ::: " + by);
        }
    }

    public void click(By by) {
        IOSDriver driver = getIosDriver();
        waitForVisibility(by);
        driver.findElement(by).click();
    }

    public void setCheckBox(By by) {
        IOSDriver driver = getIosDriver();
        waitForVisibility(by);
        driver.findElement(by).click();
    }

    public void sendText(By by, String text) {
        IOSDriver driver = getIosDriver();
        waitForVisibility(by);
        driver.findElement(by).sendKeys(text);
    }

    public String getText(By by) {
        IOSDriver driver = getIosDriver();
        waitForVisibility(by);
        return  driver.findElement(by).getText();
    }

    public boolean isDisplayed(By by){
        IOSDriver driver = getIosDriver();
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
        IOSDriver driver = getIosDriver();
        driver.hideKeyboard();
    }
}
