package testingcode;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.poi.sl.usermodel.PictureData;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFPictureShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import Configs.AppConfigs;
import Configs.GTKconfig;
import io.cucumber.java.en.Given;

public class TemplateDownload {
	WebDriver driver;
    public String WhoShouldKnow = "Ruthvik Pashikanti";
    public String WhomToKnow = "Praveen Rasaputhra";
    @Given("Downloads template")
    public void downloads_template() throws IOException, InterruptedException, AWTException {
		System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
		ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("prefs", getChromePreferences());
		driver = new ChromeDriver(chromeOptions);

		String username = AppConfigs.getUsername();
		String password = AppConfigs.getPassword();
		String Appurl = "https://" + username + ":" + password + AppConfigs.getDevUserAdminURL();
		driver.get(Appurl);
		driver.manage().window().maximize();
		driver.navigate().refresh();
		Thread.sleep(2000);
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_MINUS);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_MINUS);
		
		driver.findElement(By.xpath("//*[@id=\"sidebar\"]/ul/li[4]/a")).click();
		Thread.sleep(2000);
		driver.findElement(By.linkText("Tags")).click();
		Thread.sleep(2000);
		driver.findElement(By.linkText("Add Tag")).click();
		Thread.sleep(1500);
		driver.findElement(By.id("Tag_IsBannerRequired")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("Tag_ForeGroundColour")).sendKeys("#4bacc6");
		
	}
    private static Map<String, Object> getChromePreferences() {
        String downloadPath = "C:\\Users\\ysatya\\Desktop\\EmailTemplates"; 
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", downloadPath);
        prefs.put("download.prompt_for_download", false);
        prefs.put("safebrowsing.enabled", true);
        return prefs;
    }
 }

