package testcases.authentication;

import base.BaseTest;
import config.ConfigManager;
import helpers.providers.AccountInfoTestDataGenerator;
import helpers.verifications.AuthVerificationHelper;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.LoginPage;
import reports.ExtentReportManager;

public class TC17_LoginWithInvalidCredentialsTest extends BaseTest {

    @Test(description = "Test Blocked Login with invalid credential: Invalid password")
    public void testInvalidPasswordBlocksLogin(){

        SoftAssert softAssert =  new SoftAssert();

        ExtentReportManager.info("Navigate to Login page");
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.navigateToLoginPage();

        ExtentReportManager.info("Attempt login with valid username and incorrect password");
        // Get login credentials of default test user
        String username = ConfigManager.getDefaultUserUsername();
        String password = ConfigManager.getDefaultUserPassword();

        // Generate incorrect password and attempt login
        String incorrectPassword = AccountInfoTestDataGenerator.generateNewPassword(password);
        loginPage.fillLoginFormThenSubmit(username, incorrectPassword);

        // Verify login failed: alert displayed + alert text + top bar user profile not shown
        ExtentReportManager.info("Verify unsuccessful login");
        AuthVerificationHelper.verifyInvalidCredentialsLoginError(loginPage, getDriver(), softAssert);

        softAssert.assertAll();
    }
}