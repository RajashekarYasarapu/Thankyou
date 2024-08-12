package testingcode;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import Configs.AppConfigs;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class Contacts {
	WebDriver driver;
	
	//Function call for taking screenshot
	private void takeScreenshot(String screenshotName) {
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String screenshotPath = "C:\\Users\\ysatya\\Desktop\\ImportContacts_Screenshots\\" +
                    screenshotName + "_" + timeStamp + ".png";
            org.apache.commons.io.FileUtils.copyFile(screenshotFile, new File(screenshotPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	@Given("Dev Link URL")
	public void dev_link_url() {
		 System.setProperty("webdriver.chrome.driver","src/test/resources/drivers/chromedriver.exe");
		 driver = new ChromeDriver();
	}
	@When("User login into dev link with correct credentials")
	public void user_login_into_dev_link_with_correct_credentials() throws InterruptedException, AWTException {
		String username = AppConfigs.getUsername();
	    String password = AppConfigs.getPassword();
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
	}
	@When("hovers the mouse on any profile card to import contact")
	public void hovers_the_mouse_on_any_profile_card_to_import_contact() throws InterruptedException {
		driver.findElement(By.id("SearchTerms")).sendKeys("Daniel Bonan");
		Thread.sleep(4500);
		WebElement hover = driver.findElement(By.xpath("//*[@id=\"results-container\"]/div[3]/div[1]/div/div/div[2]/label[1]"));

        Actions actions = new Actions(driver);
        actions.moveToElement(hover).pause(1000).perform();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//button[@class='add-to-contacts' and @title='Add Daniel to outlook contacts']")).click();
        Thread.sleep(2000);
        
		//Take a screenshot
        takeScreenshot("screenshot");
	}
	@When("clicks on Import option in user profile card")
	public void clicks_on_import_option_in_user_profile_card() throws InterruptedException {
		driver.findElement(By.id("SearchTerms")).clear();
		driver.findElement(By.id("SearchTerms")).sendKeys("Praveen Rasaputhra");
		Thread.sleep(5000);
		driver.findElement(By.xpath("//*[@id=\"results-container\"]/div[3]/div[1]/div/div/div[1]")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("importToOutlook")).click();
		Thread.sleep(2000);
		
		//Take a screenshot
        takeScreenshot("screenshot");
	}
	@When("Imports contacts bulkly from UpFront page")
	public void imports_contacts_bulkly_from_up_front_page() throws InterruptedException {
		   driver.findElement(By.id("up-front-view")).click();
			Thread.sleep(3000);
		    driver.findElement(By.className("add-to-outlook-selector")).click();
		    Thread.sleep(3000);
		    driver.findElement(By.xpath("//div[@data-id='uid_2741']")).click();    
		    Thread.sleep(1000);
		    driver.findElement(By.xpath("//div[@data-id='uid_8220']")).click();    
		    Thread.sleep(1000);
		    driver.findElement(By.xpath("//div[@data-id='uid_9176']")).click();    
		    Thread.sleep(1000);
		    driver.findElement(By.xpath("//div[@data-id='uid_8515']")).click();    
			Thread.sleep(2000);
			
			//Take a screenshot
	        takeScreenshot("screenshot");
	        
		    driver.findElement(By.className("export-outlook")).click();
			Thread.sleep(2000);
			
			//Take a screenshot
	        takeScreenshot("screenshot");
	}
	@Then("User quits browser and generates test report")
	public void user_quits_browser_and_generates_test_report() throws InterruptedException, FileNotFoundException, IOException {
		Thread.sleep(1000);
		driver.quit();
        XMLSlideShow ppt = new XMLSlideShow();

        // Specify the directory containing images
        String imagesDirectory = "C:\\Users\\ysatya\\Desktop\\ImportContacts_Screenshots";

        // Get all files in the directory
        File[] imageFiles = new File(imagesDirectory).listFiles();

        if (imageFiles != null) {
            for (File imageFile : imageFiles) {
                // Create a n0ew slide for  each image
                XSLFSlide slide = ppt.createSlide();

                // Load the image file
                byte[] photo = IOUtils.toByteArray(new FileInputStream(imageFile));

                // Add the image to the presentation
                PictureData idx = ppt.addPicture(photo, PictureData.PictureType.PNG);
                XSLFPictureShape pic = slide.createPicture(idx);
                java.awt.Dimension pageSize = ppt.getPageSize();

                // Resize the picture to fit the slide
                //pic.setAnchor(new java.awt.Rectangle(0, 0, (int) pageSize.getWidth(), (int) pageSize.getHeight()));
                // Customize the position of the picture on the slide (adjust as needed)
                pic.setAnchor(new java.awt.Rectangle(100, 100, 500, 400));
            }
        }

        // Specify the desired location for saving the PowerPoint presentation
        File outputFile = new File("C:\\Users\\ysatya\\Desktop\\Automation_PPT\\ImportContacts.pptx");

        // Save the PowerPoint presentation to the specified location
        try (FileOutputStream out = new FileOutputStream(outputFile)) {
            ppt.write(out);
            System.out.println("Presentation created and saved successfully at: " + outputFile.getAbsolutePath());
        }
	}
	
	@Then("Email the test evidence for RFC approvsl")
	public void email_the_test_evidence_for_rfc_approvsl() {
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
	            messageBodyPart.setText("Please, find test evidence of Import Contacts to Outlook functionality in Link");

	            // Create the attachment body part
	            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
	            String filePath = "C:\\Users\\ysatya\\Desktop\\Automation_PPT\\ImportContacts.pptx";
	            DataSource source = new FileDataSource(filePath);
	            attachmentBodyPart.setDataHandler(new DataHandler(source));
	            attachmentBodyPart.setFileName("TestingEvidence_ImportContacts.ppt");
	                      
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
