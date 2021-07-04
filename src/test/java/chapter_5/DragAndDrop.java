package chapter_5;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidTouchAction;
import io.appium.java_client.touch.offset.ElementOption;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class DragAndDrop {

    public AppiumDriver driver;
    public AndroidTouchAction actions;

    @BeforeTest
    public void setUp() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("automationName", "UiAutomator2");
        caps.setCapability("platformVersion", "9.0");
        caps.setCapability("deviceName", "Android Emulator");
        caps.setCapability("forceEspressoRebuild",true);
        caps.setCapability("app", System.getProperty("user.dir") + "/apps/ApiDemos.apk");
        driver = new AndroidDriver(new URL("http://localhost:4723/wd/hub"), caps);
    }

    @Test
    public void drag_drop_test() {
        AndroidElement views = (AndroidElement) driver.findElementByAccessibilityId("Views");

        //Tab
        actions = new AndroidTouchAction(driver);
        actions.tap(ElementOption.element(views)).perform();

        AndroidElement drag_n_drop = (AndroidElement) driver.findElementByAccessibilityId("Drag and Drop");

        //Tab
        actions.tap(ElementOption.element(drag_n_drop)).perform();

        AndroidElement drag = (AndroidElement) driver.findElement(By.id("drag_dot_1"));
        AndroidElement drop = (AndroidElement) driver.findElement(By.id("drag_dot_2"));
        AndroidElement text = (AndroidElement) driver.findElement(By.id("drag_text"));
        String expectedText = "Dot : io.appium.android.apis.view.DraggableDot{bb3b5a VFED..CL. ...P..I. 0,137-414,551 #7f09007b app:id/drag_dot_1}";

        //Drag and Drop
        actions.longPress(ElementOption.element(drag))
                .waitAction()
                .moveTo(ElementOption.element(drop))
                .release()
                .perform();

        //Assertion
        Assert.assertEquals(text.getText(),expectedText);
    }

    @AfterTest
    public void tearDown() {
        if (null != driver) {
            driver.quit();
        }
    }
}
