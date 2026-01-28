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

public class TC04_RegisterWithExistingUsernameTest extends BaseTest {

    @DataProvider(name = "existingUsernameScenarios")
    public Object[][] existingUsernameScenarios() {

        String existingUsername = ConfigManager.getDefaultUserUsername();

        return new Object[][]{
                {existingUsername, "Existing Username"},
                {existingUsername.toUpperCase(), "Existing Username in different casing"},
        };
    }

    @Test(dataProvider = "existingUsernameScenarios", description = "Test Blocked Registration With Existing Username Regardless Of Casing")
    public void testRegisterBlockedWithExistingUsername(String username, String scenario) {

        ExtentReportManager.info("Test Register with " + scenario);
        SoftAssert softAssert = new SoftAssert();
        RegisterPage registerPage = new RegisterPage(getDriver());

        ExtentReportManager.info("Navigate to Register page");
        registerPage.navigateToRegisterPage();

        ExtentReportManager.info("Submit Register form with all valid inputs but " + scenario);
        // Generate valid form inputs as base for use in tests
        RegisterDataUI formInputs = generateValidRegisterFormInputs();
        // Set existing username in form inputs
        formInputs.setUsername(username);
        // Fill form and submit
        registerPage.fillRegisterFormThenSubmit(formInputs);

        ExtentReportManager.info("Verify form validation alert for existing username");
        String expectedMsg = MessagesProvider.getRegisterExistingUsernameError();
        AuthVerificationHelper.verifyRegisterFormErrorAlert(registerPage, expectedMsg, getDriver(), softAssert);

        softAssert.assertAll();
    }
}