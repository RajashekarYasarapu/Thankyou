package testingcode;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

import Configs.AppConfigs;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CandidateReferral {
	WebDriver driver;
	public int LinkNotificationQueueEmailId;
	public static int ReferralId;
	public static String HiringManager;
	public static String AssignedTo;
	public String username = AppConfigs.getUsername();
    public String password = AppConfigs.getPassword();

	//Function call for taking screenshot
	private void takeScreenshot(String screenshotName) {
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String screenshotPath = "C:\\Users\\ysatya\\Desktop\\CandidateReferral_Screenshots\\" +
                    screenshotName + "_" + timeStamp + ".png";
            org.apache.commons.io.FileUtils.copyFile(screenshotFile, new File(screenshotPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	//To Download Email templates generated in this referral process
    private static Map<String, Object> getChromePreferences() {
        String downloadPath = "C:\\Users\\ysatya\\Desktop\\EmailTemplates"; 
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", downloadPath);
        prefs.put("download.prompt_for_download", false);
        prefs.put("safebrowsing.enabled", true);
        return prefs;
    }
    
	@Given("User with devlink url and login credentials")
	public void user_with_devlink_url_and_login_credentials() throws AWTException, InterruptedException {
		System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
		ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("prefs", getChromePreferences());

		driver = new ChromeDriver(chromeOptions);
	    String Appurl = "https://" + username + ":" + password + AppConfigs.getDevLinkURL();
		driver.get(Appurl);
		driver.manage().window().maximize();
		driver.navigate().refresh();
		Thread.sleep(2000);

		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_MINUS);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_MINUS);

		Thread.sleep(1000);
		// driver.close();
	}

	@When("User clicks on Candidate Refrral button in devlink")
	public void user_clicks_on_candidate_refrral_button_in_devlink() throws InterruptedException {
		driver.findElement(By.className("candidatereferral-ajax")).click();
		Thread.sleep(2000);
	}

	@Then("User should fill in required fields on the form")
	public void user_should_fill_in_required_fields_on_the_form() throws InterruptedException {
		driver.findElement(By.id("EmployeeReferralCandidateDetails_FirstName")).sendKeys("Satya");
		driver.findElement(By.id("EmployeeReferralCandidateDetails_LastName")).sendKeys("Test User");
		driver.findElement(By.id("EmployeeReferralCandidateDetails_RelationShipToEmployee")).sendKeys("Former Colleague");
		driver.findElement(By.id("EmployeeReferralCandidateDetails_PostionReferredFor")).sendKeys("Senior Software Developer");

		WebElement dropdownElement = driver.findElement(By.id("EmployeeReferralDetails_ReferringToEntity"));
		Select dropdown = new Select(dropdownElement);
		dropdown.selectByVisibleText("United Online (India)");

		driver.findElement(By.id("EmployeeReferralCandidateDetails_PriorEmployer")).sendKeys("Wipro");
		driver.findElement(By.id("EmployeeReferralCandidateDetails_PriorTitle")).sendKeys("Software Developer");
		driver.findElement(By.id("EmployeeReferralCandidateDetails_Email")).sendKeys("tsatya@gmail.com");
		driver.findElement(By.id("EmployeeReferralCandidateDetails_MobilePhone")).sendKeys("7733221144");
		driver.findElement(By.id("EmployeeReferralDetails_ReferralDescription")).sendKeys("This referral is created for automation testing purpose.");
		Thread.sleep(2000);
	}

	@Then("User uploads candidats RESUME")
	public void user_uploads_candidats_resume() throws InterruptedException {
		WebElement UploadFile = driver.findElement(By.id("uploadResume"));
		String filepath = "C:\\Users\\ysatya\\Desktop\\sample.pdf";
		UploadFile.sendKeys(filepath);
	}

	@Then("User clicks on submit button")
	public void user_clicks_on_submit_button() throws InterruptedException, IOException {
		Thread.sleep(5000);
		driver.findElement(By.id("form-submit")).click();
		Thread.sleep(2000);

		//Take a Screenshot
		takeScreenshot("screenshot");
		Thread.sleep(1000);
		// Navigate to the second web page
		driver.findElement(By.linkText("My Referrals")).click();
		Thread.sleep(1000);

		// Take a screenshot 
		takeScreenshot("screenshot");
	}

	@Given("establish a connection with SSMS DB")
	public void establish_a_connection_with_ssms_db() throws InterruptedException, ClassNotFoundException {
		String jdbcurl = AppConfigs.getJDBCurl();
		// JDBC variables for opening, closing, and managing the connection
		java.sql.Connection connection = null;
		java.sql.Statement statement = null;
		ResultSet resultSet = null;

		try {
	        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			// Establish the connection
			System.out.println("Trying to connect with devsqls0012v1 server");
			connection = DriverManager.getConnection(jdbcurl);
			System.out.println("Connection Established sucessfully");
			statement = connection.createStatement();

			//Query == To get LinkNotificationQueueEmail Id
			String Query = "Select Top 1 LinkNotificationQueueEmailId from LinkNotificationQueueEmail Where FlowName = 'CandidateReferral' and FlowRecordId in\r\n"
					+ "(SELECT TOP 1 EmployeeReferralId FROM EmployeeReferral ORDER BY EmployeeReferralId DESC)";
			
			resultSet = statement.executeQuery(Query);
			if (resultSet.next()) {
				LinkNotificationQueueEmailId = resultSet.getInt("LinkNotificationQueueEmailId");
				System.out.println("LinkNotificationQueueEmailId is: " + LinkNotificationQueueEmailId);
			}
			//Query2 == To get CVandidate Referral Id	
		    String Query2 = "Select Top 1 EmployeeReferralId from EmployeeReferral where SubmittedByName = 'Satya Rajashekar Yasarapu'\r\n"
						+ "order by EmployeeReferralId desc";
				
			resultSet = statement.executeQuery(Query2);
			if(resultSet.next()){
				ReferralId = resultSet.getInt("EmployeeReferralId");
				System.out.println("Candidate Referral Id is: " +ReferralId);
			}
	}
		
		catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Close the connection in the finally block to ensure it's always closed
			try {
				if (connection != null && !connection.isClosed()) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		Thread.sleep(1000);
		//driver.close();
	}

	@Then("User should be able to see email template generated for this referral.")
	public void user_should_be_able_to_see_email_template_generated_for_this_referral()throws InterruptedException, AWTException {
		Thread.sleep(1000);
		  String newurl = "http://devlink.brileyfin.dev/Email/DownloadEmlFile?linknotificationqueueemailid="+LinkNotificationQueueEmailId; 
		  ((JavascriptExecutor) driver).executeScript("window.open();"); 
		  for(String windowHandle : driver.getWindowHandles()) {
		  driver.switchTo().window(windowHandle); 
		  } // Navigate to the specified URL
		  driver.navigate().to(newurl);
		  Thread.sleep(1000); 
		driver.quit();
		}
		
	//Tag 2 code continue's here
	@Given("User with devlink url aloong with login credentials")
	public void user_with_devlink_url_aloong_with_login_credentials() throws InterruptedException, AWTException {
		System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
		ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("prefs", getChromePreferences());

        String downloadPath = "C:\\Users\\ysatya\\Desktop\\EmailTemplates"; 
        chromeOptions.addArguments("download.default_directory=" + downloadPath);
        
		driver = new ChromeDriver(chromeOptions);
		String url = "https://" + username + ":" + password + "@devlink.devfbr.com/";
		driver.get(url);
		driver.manage().window().maximize();
		driver.navigate().refresh();
		Thread.sleep(2000);

		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_MINUS);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_MINUS);

		Thread.sleep(1000);
	}
	@When("User goes to My referral page of candidate referrals")
	public void user_goes_to_my_referral_page_of_candidate_referrals() throws InterruptedException {
		driver.findElement(By.className("candidatereferral-ajax")).click();
		Thread.sleep(2000);
		driver.findElement(By.linkText("My Referrals")).click();
		Thread.sleep(1000);
	}
	String searchid = "" +ReferralId;
	@Then("User clicks on Assign button of respective referral")
	public void user_clicks_on_assign_button_of_respective_referral() {
		driver.findElement(By.id("search")).sendKeys(searchid);
		driver.findElement(By.linkText("Assign")).click();
	}
	@Then("User assigns referral to HM and HR")
	public void user_assigns_referral_to_hm_and_hr() throws InterruptedException {
		driver.findElement(By.id("AssignedToUser")).sendKeys("Brittany Moody");
		Thread.sleep(3000);
		driver.findElement(By.xpath("//*[@id=\"AssignedToUser_typeahead\"]/div[2]/ul/li/a/div")).click();
		driver.findElement(By.id("HiringManager")).sendKeys("Praveen Rasaputhra");
		Thread.sleep(5000);
		driver.findElement(By.xpath("//*[@id=\"HiringManager_typeahead\"]/div[2]/ul/li/a/div")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("CurrentComments")).sendKeys("The referral is currently under review state");
	}
	@Then("User clicks on submit")
	public void user_clicks_on_submit() throws InterruptedException {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        // Scroll down by a specific pixel value (e.g., 500 pixels)
        jsExecutor.executeScript("window.scrollBy(0, 500);");
        Thread.sleep(1000);
        driver.findElement(By.id("submit-form")).click();
        Thread.sleep(1000);
        driver.findElement(By.linkText("My Referrals")).click();
        Thread.sleep(2000);
        driver.close();
	}                                                                                                                                              
	@Then("User gets respective HR and HM of this referral")
	public void user_gets_respective_hr_and_hm_of_this_referral() throws InterruptedException, ClassNotFoundException {
		String jdbcurl = AppConfigs.getJDBCurl();

		java.sql.Statement statement = null;
		ResultSet resultSet = null;
		
		// JDBC variables for opening, closing, and managing the connection
		java.sql.Connection connection = null;

		try {
	        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			// Establish the connection
			System.out.println("Trying to connect to devsqls0012v1 server");
			connection = DriverManager.getConnection(jdbcurl);
			System.out.println("Connection Established sucessfully");
			statement = connection.createStatement();
			
			//Query3 == To get HiringManager for this referral
			String Query3 ="SELECT CONCAT(FNAME, ' ', LNAME) AS Fullname FROM [User]\r\n"
					+ "WHERE Id IN (Select HiringManager from EmployeeReferral where EmployeeReferralId IN ("+ReferralId+"))";
			
			resultSet = statement.executeQuery(Query3);
			if(resultSet.next()){
				HiringManager = resultSet.getString("FullName");
				System.out.println("Hiring manager for this Candidate Referral is: " +HiringManager);
			}
			
			//Query4 == To get AssignedTo Manager for this referral
			String Query4 ="SELECT CONCAT(FNAME, ' ', LNAME) AS Fullname FROM [User]\r\n"
					+ "WHERE Id IN (Select AssignedTo from EmployeeReferral where EmployeeReferralId IN ("+ReferralId+"))";
			
			resultSet = statement.executeQuery(Query4);
			if(resultSet.next()){
				AssignedTo = resultSet.getString("FullName");
				System.out.println("AssignedTo manager for this Candidate Referral is: " +AssignedTo);
			}
	}
		
		catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Close the connection in the finally block to ensure it's always closed
			try {
				if (connection != null && !connection.isClosed()) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
