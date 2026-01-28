package testcases.registration;

import base.BaseTest;
import config.ConfigManager;
import helpers.providers.MessagesProvider;
import helpers.verifications.AuthVerificationHelper;
import model.ui.RegisterDataUI;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.RegisterPage;
import reports.ExtentReportManager;

import static helpers.providers.AccountInfoTestDataGenerator.generateValidRegisterFormInputs;

public class TC05_RegisterWithExistingEmailTest extends BaseTest {

    @DataProvider(name = "existingEmailScenarios")
    public Object[][] existingEmailScenarios() {
        String existingEmail = ConfigManager.getDefaultUserEmail();

        return new Object[][]{
                {existingEmail, "Existing Email"},
                {existingEmail.toUpperCase(), "Existing Email in different casing"},
        };
    }

    @Test(dataProvider = "existingEmailScenarios", description = "Test Blocked Registration With Existing Email Regardless Of Casing")
    public void testRegisterBlockedWithExistingEmail(String email, String scenario) {

        ExtentReportManager.info("Test Register with " + scenario);
        SoftAssert softAssert = new SoftAssert();
        RegisterPage registerPage = new RegisterPage(getDriver());

        ExtentReportManager.info("Navigate to Register page");
        registerPage.navigateToRegisterPage();

        ExtentReportManager.info("Submit Register form with all valid inputs but " + scenario);
        // Generate valid form inputs as base for use in tests
        RegisterDataUI formInputs = generateValidRegisterFormInputs();
        // Set existing username in form inputs
        formInputs.setEmail(email);
        // Fill and submit form
        registerPage.fillRegisterFormThenSubmit(formInputs);

        ExtentReportManager.info("Verify form validation alert for existing email");
        String expectedMsg = MessagesProvider.getRegisterExistingEmailError();
        AuthVerificationHelper.verifyRegisterFormErrorAlert(registerPage, expectedMsg, getDriver(), softAssert);

        softAssert.assertAll();
    }

}