package testingcode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.poi.sl.usermodel.PictureData;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFPictureShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.openqa.selenium.Dimension;

import io.cucumber.java.en.Given;

public class PowerPointPresentation {
    @Given("Create a new ppt using XSLF Slideshow")
    public void create_a_new_ppt_using_xslf_slideshow() throws IOException {
        // Create a new PowerPoint presentation
        XMLSlideShow ppt = new XMLSlideShow();

        // Specify the directory containing images
        String imagesDirectory = "C:\\Users\\ysatya\\Desktop\\ImportContacts_Screenshots";

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
                java.awt.Dimension pageSize = ppt.getPageSize();

                // Resize the picture to fit the slide
                //pic.setAnchor(new java.awt.Rectangle(0, 0, (int) pageSize.getWidth(), (int) pageSize.getHeight()));
                // Customize the position of the picture on the slide (adjust as needed)
                pic.setAnchor(new java.awt.Rectangle(0, 0, 820, 570));
                //pic.setAnchor(new java.awt.Rectangle(0, 0, 960, 720));

            }
        }

        // Specify the desired location for saving the PowerPoint presentation
        File outputFile = new File("C:\\Users\\ysatya\\Desktop\\TestingEvidence1.pptx");

        // Save the PowerPoint presentation to the specified location
        try (FileOutputStream out = new FileOutputStream(outputFile)) {
            ppt.write(out);
            System.out.println("Presentation created and saved successfully at: " + outputFile.getAbsolutePath());
        }
    }
}
