package assessment;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class LoginPageValidation {

    public static WebDriver driver;

    @Parameters({"url", "browser"})
    @BeforeMethod
    public static WebDriver setupDriver(String url, String browser) {
        driver = getDriver(browser);
        driver.get(url);
        return driver;
    }
    public static WebDriver getDriver(String browser){
        if ( browser.equalsIgnoreCase("ie")) {
            System.setProperty("webdriver.ie.driver", "src/main/resources/Browser_Drivers/IEDriverServer.exe");
            driver = new InternetExplorerDriver();
        } else if (browser.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", "src/main/resources/Browser_Drivers/chromedriver");
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            System.setProperty("webdriver.gecko.driver", "src/main/resources/Browser_Drivers/geckodriver");
            driver = new FirefoxDriver();
        }
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        return driver;
    }

    @Parameters({"browser"})
    @Test
    public void validationErrorMessage( String browser){
        if (browser.equalsIgnoreCase("ie")){
            String ieWaringMessage = driver.findElement(By.className("text-danger")).getText();
            if (driver.findElement(By.className("text-danger")).isDisplayed()){
                Assert.assertEquals(ieWaringMessage,"We specifically support recent versions of Chrome and Firefox.  You might want to consider upgrading your browser for an optimal experience.\u0000");
                System.out.println("warning Message:"+ieWaringMessage);
            }
        }
        if (browser.equalsIgnoreCase("chrome")){
            driver.findElement(By.id("name")).sendKeys("john");
            driver.findElement(By.id("password")).sendKeys("pass123");
            driver.findElement(By.xpath("//button[@class='btn btn-primary btn-block']")).click();

            String errorMessage = driver.findElement(By.xpath("//div[@id='dialogDesc-2570']")).getText();
            Assert.assertEquals(errorMessage,"Invalid Username Or Password");
            System.out.println("the error message is:"+errorMessage);
        }
    }

    //NOTE: during automation of logIn errorMessage on chrome browser, unexpected test is shown. Therefor test has failed

    @AfterMethod(alwaysRun = true)
    public void cleanUp(){
        driver.quit();
    }

}
