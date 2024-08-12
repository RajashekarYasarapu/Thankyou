package testingcode;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import Configs.AppConfigs;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

public class TagCreationTestWithExcel {
    WebDriver driver;
    public void createTag(String FirstName, String LastName, String Relationship, String Posistion, String PriorEmployer, String PriorTitle, String EmailAddress, 
    		String PhoneNumber, String Description) throws InterruptedException {
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
	    driver.findElement(By.className("candidatereferral-ajax")).click();    
	    Thread.sleep(2000);
		String Resumefilepath = "C:\\Users\\ysatya\\Desktop\\sample.pdf";

        // Find elements using various locators
        WebElement FirstNameInput = driver.findElement(By.id("EmployeeReferralCandidateDetails_FirstName"));
        WebElement LastNameInput = driver.findElement(By.id("EmployeeReferralCandidateDetails_LastName"));
        WebElement RelationshipInput = driver.findElement(By.id("EmployeeReferralCandidateDetails_RelationShipToEmployee"));
        WebElement PosistionInput = driver.findElement(By.id("EmployeeReferralCandidateDetails_PostionReferredFor"));
        WebElement PriorEmployerInput = driver.findElement(By.id("EmployeeReferralCandidateDetails_PriorEmployer"));
        WebElement PriorTitleInput = driver.findElement(By.id("EmployeeReferralCandidateDetails_PriorTitle"));
        WebElement EmailAddressInput = driver.findElement(By.id("EmployeeReferralCandidateDetails_Email"));
        WebElement PhoneNumberInput = driver.findElement(By.id("EmployeeReferralCandidateDetails_MobilePhone"));
        WebElement DescriptionInput = driver.findElement(By.id("EmployeeReferralDetails_ReferralDescription"));
		WebElement UploadFile = driver.findElement(By.id("uploadResume"));
		WebElement SubmitButton = driver.findElement(By.id("form-submit"));
		
		// Perform actions on the elements
        FirstNameInput.sendKeys(FirstName);
        LastNameInput.sendKeys(LastName);
        RelationshipInput.sendKeys(Relationship);
        PosistionInput.sendKeys(Posistion);
        PriorEmployerInput.sendKeys(PriorEmployer);
        PriorTitleInput.sendKeys(PriorTitle);
        EmailAddressInput.sendKeys(EmailAddress);
        PhoneNumberInput.sendKeys(PhoneNumber);
        DescriptionInput.sendKeys(Description);
		UploadFile.sendKeys(Resumefilepath);
        SubmitButton.click();
    }

    public void runTestsFromExcel(String excelFilePath) throws InterruptedException {
        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(excelFilePath))) {
            Sheet sheet = workbook.getSheetAt(0); // Assuming data is in the first sheet

            Iterator<Row> iterator = sheet.iterator();
            iterator.next(); // Skip header row

            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                String FirstName = currentRow.getCell(0).getStringCellValue();
                String LastName = currentRow.getCell(1).getStringCellValue();
                String Relationship = currentRow.getCell(2).getStringCellValue();
                String Posistion = currentRow.getCell(3).getStringCellValue();
                String PriorEmployer = currentRow.getCell(5).getStringCellValue();
                String PriorTitle = currentRow.getCell(6).getStringCellValue();
                String EmailAddress = currentRow.getCell(7).getStringCellValue();
                String PhoneNumber = String.valueOf((long) currentRow.getCell(8).getNumericCellValue());
                String Description = currentRow.getCell(9).getStringCellValue();
                // Execute the test using data from Excel
                createTag(FirstName, LastName, Relationship, Posistion, PriorEmployer, PriorTitle, EmailAddress, PhoneNumber, Description);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        TagCreationTestWithExcel test = new TagCreationTestWithExcel();
        test.runTestsFromExcel("C:\\Users\\ysatya\\Desktop\\Test.xlsx");
    }
}
