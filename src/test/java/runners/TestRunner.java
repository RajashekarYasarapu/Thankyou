package runners;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    /* AddCandidateReferral, 
     * CreatePPT, 
     * DBConnection, 
     * SendEmail, 
     * GetToKnow, 
     * Download, 
     * ImportContacts */
    features = "src/test/resources/features/DBConnection.feature",
    glue = {"testingcode"},
    plugin = {"pretty", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"}
)
public class TestRunner {
    // This class can be emptyImportContactsImportContacts
}