package testcases.e2e;

import base.BaseTest;
import helpers.AuthTestDataGenerator;
import model.RegisterRequest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.AccountPage;
import pages.HomePage;
import pages.LoginPage;
import pages.RegisterPage;
import reports.ExtentReportManager;

import static helpers.AssertionHelper.verifySoftEquals;
import static helpers.AuthVerificationHelper.*;

/**
 * E2E Test: Complete Authentication and Account management Flow
 * Tests the entire user journey from registration, login, account update to logout.
 */
public class AuthAndAccountE2ETest extends BaseTest {

    private RegisterPage registerPage;
    private LoginPage loginPage;
    private HomePage homePage;
    private AccountPage accountPage;

    @BeforeMethod
    public void setupMethod() {
        registerPage = new RegisterPage(getDriver());
        loginPage = new LoginPage(getDriver());
        homePage = new HomePage(getDriver());
        accountPage = new AccountPage(getDriver());
    }

    @Test(groups = {"e2e", "auth", "account", "critical"})
    public void testCompleteAuthFlow() {
        SoftAssert softAssert = new SoftAssert();

        // ============================================
        // Step 1: Register new account
        // ============================================
        ExtentReportManager.info("Navigate to Register page and create new account");
        registerPage.navigateToRegisterPage();

        RegisterRequest registerRequest = AuthTestDataGenerator.generateValidRegisterData();
        registerPage.fillAndSubmitRegisterForm(
                registerRequest.getUsername(),
                registerRequest.getPassword(),
                registerRequest.getConfirmPassword(),
                registerRequest.getFullName(),
                registerRequest.getEmail()
        );
        verifyRegisterSuccessMsg(registerPage, getDriver(), softAssert);

        // ============================================
        // Step 2: Login with newly registered account
        // ============================================
        ExtentReportManager.info("Navigate to Login page and login with new credentials");
        loginPage.navigateToLoginPage();
        loginPage.fillLoginFormAndSubmit(registerRequest.getUsername(), registerRequest.getPassword());

        verifyLoginSuccess(loginPage, getDriver(), softAssert);
        verifyUserButtonDisplaysCorrectName(loginPage, registerRequest.getFullName(), getDriver(), softAssert);

        // ============================================
        // Step 3: Navigate to Account page to verify account information
        // ============================================
        ExtentReportManager.info("Navigate to Account page to verify account information");
        accountPage.navigateToAccountPage();
        String displayedUsername = accountPage.getUsername();
        verifySoftEquals(displayedUsername, registerRequest.getUsername(),
                "Account page displays correct Username after registration", getDriver(), softAssert);

        String displayedFullName = accountPage.getFullName();
        verifySoftEquals(displayedFullName, registerRequest.getFullName(),
                "Account page displays correct Full Name after registration", getDriver(), softAssert);

        String displayedEmail = accountPage.getEmail();
        verifySoftEquals(displayedEmail, registerRequest.getEmail(),
                "Account page displays correct Email after registration", getDriver(), softAssert);

        String displayedPassword = accountPage.getPassword();
        verifySoftEquals(displayedPassword, registerRequest.getPassword(),
                "Account page displays correct Password after registration", getDriver(), softAssert);

        // ============================================
        // Step 4: Update Profile information (full name)
        // ============================================
        ExtentReportManager.info("Navigate to Account page and change Full Name");
        String newName = AuthTestDataGenerator.generateNewName(registerRequest.getFullName());
        accountPage.changeName(newName);
        accountPage.saveChanges();

        verifyAccountUpdateSuccessMsg(accountPage, getDriver(), softAssert);
        verifyAccountFullNameUpdated(accountPage, newName, getDriver(), softAssert);

        // ============================================
        // Step 5: Log out of the account
        // ============================================
        ExtentReportManager.info("Log out of the account");
        accountPage.topBarNavigation.logout();
        verifyLogoutSuccess(accountPage, getDriver(), softAssert);

        // ============================================
        // Step 6: Log in again to verify updated user profile name in top bar
        // ============================================
        ExtentReportManager.info("Log back in to verify user profile name is updated in top bar");
        loginPage.navigateToLoginPage();
        loginPage.fillLoginFormAndSubmit(registerRequest.getUsername(), registerRequest.getPassword());

        verifyLoginSuccess(loginPage, getDriver(), softAssert);
        verifyUserButtonDisplaysCorrectName(loginPage, newName, getDriver(), softAssert);

        softAssert.assertAll();
    }
}

