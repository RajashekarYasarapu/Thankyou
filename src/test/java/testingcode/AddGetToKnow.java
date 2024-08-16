package testingcode;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.poi.sl.usermodel.PictureData;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFPictureShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import Configs.AppConfigs;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class AddGetToKnow {
	WebDriver driver;
	public int ID;
	public String WhoShouldKnow = "Ruthvik Pashikanti";
	public String WhomToKnow = "Ravinder Davuda";
	public int sampleid;
	//Function call for taking screenshot
	private void takeScreenshot(String screenshotName) {
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String screenshotPath = "C:\\Users\\ysatya\\Desktop\\GetToKnow_Screenshots\\" +
                    screenshotName + "_" + timeStamp + ".png";
            org.apache.commons.io.FileUtils.copyFile(screenshotFile, new File(screenshotPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	@Given("User with Dev Link application url")
	public void user_with_dev_link_application_url() {
		 System.setProperty("webdriver.chrome.driver","src/test/resources/drivers/chromedriver.exe");
		 driver = new ChromeDriver();
	}
	@Then("User login in link app with valid credentials")
	public void user_login_in_link_app_with_valid_credentials() throws InterruptedException, AWTException {
		String username = AppConfigs.getUsername();
	    String password = AppConfigs.getPassword();
        String url = AppConfigs.getDevLinkURL();
	    String Appurl = "https://" + username + ":" + password + url;
		driver.get(Appurl);
		 driver.manage().window().maximize();
		 driver.navigate().refresh();
		 Thread.sleep(2000);
		 
		 Robot robot = new Robot();
		 robot.keyPress(KeyEvent.VK_CONTROL);
		 robot.keyPress(KeyEvent.VK_MINUS);
		 robot.keyRelease(KeyEvent.VK_CONTROL);
		 robot.keyRelease(KeyEvent.VK_MINUS);
	}
	@Then("User clicks on any user profile card")
	public void user_clicks_on_any_user_profile_card() throws InterruptedException {
		Thread.sleep(1500);
		driver.findElement(By.id("SearchTerms")).sendKeys(WhoShouldKnow);
		Thread.sleep(3500);
		driver.findElement(By.xpath("//*[@id=\"results-container\"]/div[3]/div[1]/div/div/div[1]")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("show-gettoknow-button")).click();
		Thread.sleep(1000);
	}
	@Then("User adds gtk interaction with new user")
	public void user_adds_gtk_interaction_with_new_user() throws InterruptedException {
		driver.findElement(By.name("gettoknowUser")).sendKeys(WhomToKnow);
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@id=\"getToKnowPanel\"]/div[1]/div[1]/div[2]/div/div[2]/ul/li")).click();
		Thread.sleep(1500);
		driver.findElement(By.xpath("//div[@class='br-txta-header']//input[@type='button'  and @data-id='0' and @value='Name & Title' and @data-name='Please meet ${name}, ${title}']")).click();
		Thread.sleep(2000);

		//Take a screenshot
		takeScreenshot("screenshot");
	}
	@Then("User clicks on Add button")
	public void user_clicks_on_add_button() throws InterruptedException {
		driver.findElement(By.className("action-link")).click();
		Thread.sleep(2000);
		//Take a screenshot
		takeScreenshot("screenshot");
	}
	@Then("Establish connection with devsql server")
	public void establish_connection_with_devsql_server() throws InterruptedException, ClassNotFoundException {
		    String jdbcurl = AppConfigs.getJDBCurl();
	        // JDBC variables for opening, closing, and managing the connection
	        java.sql.Connection connection = null;
	        java.sql.Statement statement = null;
	        ResultSet resultSet = null;

	        try {
	            // Establish the connection
	        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	        System.out.println("Trying to connect to devsqls0012v1 server");
	        // Establish the database connection with Windows authentication
	        connection = DriverManager.getConnection(jdbcurl);
	        System.out.println("Connection Established sucessfully");
	        statement = connection.createStatement();
	        
	        String Query = "Select LinkNotificationQueueEmailId from LinkNotificationQueueEmail where LinkNotificationQueueEmailId in \r\n"
	        		+ "(Select Top 1 LinkNotificationQueueEmailId from LinkNotificationQueueEmail where FlowName = 'GetToKnow' order by LinkNotificationQueueEmailId desc)";
	        
	        resultSet = statement.executeQuery(Query);
	        if(resultSet.next()) {
	        	 ID = resultSet.getInt("LinkNotificationQueueEmailId");
	        	System.out.println("LinkNotificationQueueEmailId is: " +ID);
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
	}
	
	@Then("User should be able to see email template genarated for this interaction")
	public void user_should_be_able_to_see_email_template_genarated_for_this_interaction() throws InterruptedException {
		driver.findElement(By.xpath("/html/body/div[4]/div[1]/button")).click();
		Thread.sleep(1000);
		driver.close();

		/*
		 * String newurl =
		 * "http://devlink.devfbr.com/Email/DownloadEmlFile?linknotificationqueueemailid="
		 * +ID; ((JavascriptExecutor) driver).executeScript("window.open();"); for
		 * (String windowHandle : driver.getWindowHandles()) {
		 * driver.switchTo().window(windowHandle); } // Navigate to the specified URL
		 * driver.navigate().to(newurl);
		 * 
		 * Thread.sleep(1000); String newtab = "chrome://downloads/";
		 * ((JavascriptExecutor) driver).executeScript("window.open();"); for (String
		 * windowHandle : driver.getWindowHandles()) {
		 * driver.switchTo().window(windowHandle); } // Navigate to the specified URL
		 * driver.navigate().to(newtab); Thread.sleep(5000);
		 * driver.findElement(By.xpath(
		 * "/html/body/downloads-manager//div[2]/iron-list/downloads-item//div[2]/div[2]/div[7]/span[2]/cr-button"
		 * )).click(); Alert alert = driver.switchTo().alert(); // For example, accept
		 * the alert (click the "Keep anyway" button) alert.accept();
		 * Thread.sleep(2000); driver.quit();
		 */
	}
	
	//Tag2 code continue's here
	@Given("User goes to Get to Know page")
	public void user_goes_to_get_to_know_page() throws InterruptedException, AWTException {
		 System.setProperty("webdriver.chrome.driver","src/test/resources/drivers/chromedriver.exe");
		 driver = new ChromeDriver();
         String username = AppConfigs.getUsername();
		 String password = AppConfigs.getPassword();
	     String url = AppConfigs.getDevLinkURL();
		 String Appurl = "https://" + username + ":" + password + url;
	     driver.get(Appurl);
		 driver.manage().window().maximize();
		 driver.navigate().refresh();
		 Thread.sleep(2000);
		 
		 Robot robot = new Robot();
		 robot.keyPress(KeyEvent.VK_CONTROL);
		 robot.keyPress(KeyEvent.VK_MINUS);
		 robot.keyRelease(KeyEvent.VK_CONTROL);
		 robot.keyRelease(KeyEvent.VK_MINUS);
	}
	
	@Then("User should impersonate as WhoShouldKnow")
	public void user_should_impersonate_as_who_should_know() throws InterruptedException {
	    driver.findElement(By.name("user_v1[query]")).clear();
	    driver.findElement(By.name("user_v1[query]")).sendKeys(WhoShouldKnow);
	    Thread.sleep(2000);
	    driver.findElement(By.xpath("//*[@id=\"form-user_v1\"]/div[2]/div[2]/ul/li/a")).click();
	 }
	@Then("Go to his Get To Know page")
	public void go_to_his_get_to_know_page() throws InterruptedException {
	    driver.findElement(By.className("newusers-ajax")).click();
	    Thread.sleep(2000);
	    takeScreenshot("screenshot");	    
		driver.findElement(By.xpath("//div[contains(@class, 'fa-handshake') and @data-usrname='"+WhomToKnow+"']")).click();
	    Thread.sleep(3000);
	    takeScreenshot("screenshot");	    
	    driver.findElement(By.cssSelector("div.confirmation-overlay div#gtkContactComplete div.has-columns label input#gtkContactOptionYes")).click();
	    Thread.sleep(1500);
	    driver.findElement(By.cssSelector("div.confirmation-overlay div#gtkContactComplete div.br-txta textarea#GetToKnowRemovalComments")).sendKeys("Interaction completed successfully");
	    Thread.sleep(1500);
	    takeScreenshot("screenshot");	    
	    driver.findElement(By.cssSelector("div.confirmation-overlay div#gtkContactComplete button.save")).click();
	    driver.quit();
	}

	//Tag3 Code continues from here
	@Given("Automate the process of creating ppt")
	public void automate_the_process_of_creating_ppt() throws FileNotFoundException, IOException {
        XMLSlideShow ppt = new XMLSlideShow();

        // Specify the directory containing images
        String imagesDirectory = "C:\\Users\\ysatya\\Desktop\\GetToKnow_Screenshots";

        // Get all files in the directory
        File[] imageFiles = new File(imagesDirectory).listFiles();

        if (imageFiles != null) {
            for (File imageFile : imageFiles) {
                // Create a new slide for each image
                XSLFSlide slide = ppt.createSlide();

                // Load the image file
                byte[] photo = IOUtils.toByteArray(new FileInputStream(imageFile));

                // Add the image to the presentation
                PictureData idx = ppt.addPicture(photo, PictureData.PictureType.PNG);
                XSLFPictureShape pic = slide.createPicture(idx);
                //java.awt.Dimension pageSize = ppt.getPageSize();

                // Resize the picture to fit the slide
                //pic.setAnchor(new java.awt.Rectangle(0, 0, (int) pageSize.getWidth(), (int) pageSize.getHeight()));
                // Customize the position of the picture on the slide (adjust as needed)
                pic.setAnchor(new java.awt.Rectangle(100, 100, 500, 400));
            }
        }

        // Specify the desired location for saving the PowerPoint presentation
        File outputFile = new File("C:\\Users\\ysatya\\Desktop\\Automation_PPT\\GetToKnow.pptx");

        // Save the PowerPoint presentation to the specified location
        try (FileOutputStream out = new FileOutputStream(outputFile)) {
            ppt.write(out);
            System.out.println("Presentation created and saved successfully at: " + outputFile.getAbsolutePath());
        }

	}
	@Then("Mail to BizappsHyd team.")
	public void mail_to_bizapps_hyd_team() {
    	final String username = AppConfigs.getOutlookUsername();
        final String password = AppConfigs.getOutlookPassword();

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.office365.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                        return new javax.mail.PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("ysatya@brileyfin.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("ysatya@brileyfin.com"));
            message.setSubject("Testing Evidence from QA");

            // Create the message body part
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Please, find test evidence of Get To Know functionality in Link");

            // Create the attachment body part
            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            String filePath = "C:\\Users\\ysatya\\Desktop\\Automation_PPT\\GetToKnow.pptx";
            DataSource source = new FileDataSource(filePath);
            attachmentBodyPart.setDataHandler(new DataHandler(source));
            attachmentBodyPart.setFileName("TestingEvidence_GetToKnow");
                      
            // Create a multipart message
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachmentBodyPart);
            // Set the content of the message
            message.setContent(multipart);

            // Send the message
            Transport.send(message);

            System.out.println("Email with attachment sent successfully!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

	}
}
