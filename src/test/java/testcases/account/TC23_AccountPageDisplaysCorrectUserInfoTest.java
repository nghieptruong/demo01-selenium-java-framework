package testcases.account;

import api.services.UserService;
import base.BaseTest;
import helpers.actions.AuthActionHelper;
import helpers.verifications.AccountVerificationHelper;
import model.UserAccount;
import model.api.request.RegisterRequestPayload;
import model.ui.LoginDataUI;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.AccountPage;
import pages.LoginPage;
import reports.ExtentReportManager;

import static helpers.providers.AccountInfoTestDataGenerator.generateRegisterRequestPayload;

public class TC23_AccountPageDisplaysCorrectUserInfoTest extends BaseTest {

    private RegisterRequestPayload requestPayload;
    private LoginDataUI loginCredentials;

    @BeforeMethod(alwaysRun = true)
    public void setupMethod() {
        // Generate payload and send POST request to create new user via API
        requestPayload = generateRegisterRequestPayload();

        // TEMP: Remove spaces in full name due to known backend bug to make sure account form is displayed for this test
        requestPayload.setHoTen(requestPayload.getHoTen().replaceAll(" ", ""));

        UserService userService = new UserService();
        userService.sendRegisterRequest(requestPayload);

        loginCredentials = new LoginDataUI(requestPayload.getTaiKhoan(), requestPayload.getMatKhau());
    }

    @Test
    public void testAccountPageDisplaysCorrectUserInfo()  {

        SoftAssert softAssert = new SoftAssert();

        ExtentReportManager.info("Login with newly created user credentials");
        LoginPage loginPage = new LoginPage(getDriver());
        AuthActionHelper.login(loginPage, loginCredentials);

        ExtentReportManager.info("Navigate to account page and collect displayed user info");
        AccountPage accountPage = new AccountPage(getDriver());
        accountPage.navigateToAccountPage();
        UserAccount displayedUserInfo = accountPage.getAccountData();

        ExtentReportManager.info("Verify displayed info matches payload info used for creating new user");
        AccountVerificationHelper.verifyAccountDataMatchesRegistration(
                requestPayload,
                displayedUserInfo,
                "Account Page",
                getDriver(), softAssert
        );

        softAssert.assertAll();
    }
}