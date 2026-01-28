package testcases.authentication;

import base.BaseTest;
import helpers.actions.AuthActionHelper;
import helpers.verifications.AuthVerificationHelper;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.HomePage;
import pages.LoginPage;
import reports.ExtentReportManager;

public class TC21_LogoutTest extends BaseTest {

    @Test
    public void testSuccessfulLogout() {

        SoftAssert softAssert = new SoftAssert();
        LoginPage loginPage = new LoginPage(getDriver());
        HomePage homePage = new HomePage(getDriver());

        ExtentReportManager.info("Log in and navigate to Homepage if not redirected");
        AuthActionHelper.loginAsDefaultUser(loginPage);

        boolean homepageRedirected = loginPage.isRedirectedToHomepage();
        if (!homepageRedirected) {
            homePage.navigateToHomePage();
        }

        ExtentReportManager.info("Click Logout link and confirm logout");
        homePage.topBarNavigation.clickLogoutLinkAndConfirm();

        ExtentReportManager.info("Verify logout success");
        AuthVerificationHelper.verifyLogoutSuccess(homePage, getDriver(), softAssert);

        softAssert.assertAll();
    }
}