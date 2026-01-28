package testcases.account;

import api.services.UserService;
import base.BaseTest;
import helpers.actions.AuthActionHelper;
import model.api.request.RegisterRequestPayload;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AccountPage;
import pages.LoginPage;
import reports.ExtentReportManager;

import static helpers.providers.AccountInfoTestDataGenerator.generateRegisterRequestPayload;

public class TC30_UserTypeReadonlyTest extends BaseTest {

    private RegisterRequestPayload requestPayload;

    @BeforeMethod(alwaysRun = true)
    public void setupMethod() {
        // Generate payload and send POST request to create new user via API
        requestPayload = generateRegisterRequestPayload();

        // TEMP: Remove non-alphabetic characters in full name due to known backend bug to make sure account form is displayed for this test
        requestPayload.setHoTen(requestPayload.getHoTen().replaceAll("[^a-zA-Z]", ""));

        UserService userService = new UserService();
        userService.sendRegisterRequest(requestPayload);
    }

    @Test
    public void testUsernameIsReadonly() {
        // Login
        ExtentReportManager.info("Login with newly created user credentials");
        LoginPage loginPage = new LoginPage(getDriver());
        AuthActionHelper.login(loginPage, requestPayload.getTaiKhoan(), requestPayload.getMatKhau());

        // Attempt to select Admin option from UserType dropdown
        ExtentReportManager.info("Navigate select Admin option from UserType dropdown");
        AccountPage accountPage = new AccountPage(getDriver());
        accountPage.navigateToAccountPage();
        accountPage.waitForAccountFormDisplay();

        accountPage.attemptToChangeUserTypeToAdmin();

        // Verify User Type selected option remains unchanged
        String displayedUserType = accountPage.getUserType();
        Assert.assertEquals(requestPayload.getMaLoaiNguoiDung(), displayedUserType,
                "User type does not display original type. Expected = " + requestPayload.getMaLoaiNguoiDung() + ", Actual = " + displayedUserType);

        ExtentReportManager.pass("Username field is readonly");
    }
}