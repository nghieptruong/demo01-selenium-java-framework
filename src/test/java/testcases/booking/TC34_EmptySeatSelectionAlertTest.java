package testcases.booking;

import base.BaseTest;
import config.ConfigManager;
import helpers.actions.AuthActionHelper;
import helpers.actions.BookingActionHelper;
import helpers.verifications.BookingVerificationHelper;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.BookingPage;
import pages.LoginPage;
import reports.ExtentReportManager;

public class TC34_EmptySeatSelectionAlertTest extends BaseTest {

    @BeforeMethod(alwaysRun = true)
    public void setUpMethod() {
        String username = ConfigManager.getDefaultUserUsername();
        String password = ConfigManager.getDefaultUserPassword();

        LoginPage loginPage = new LoginPage(getDriver());
        AuthActionHelper.login(loginPage, username, password);
    }

    @Test
    public void testBookingNoSeatSelectedError() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        BookingPage bookingPage = new BookingPage(getDriver());

        // Find a random showtime with available seats (from API data) and navigate to its booking page
        ExtentReportManager.info("Navigate to showtime booking page");
        BookingActionHelper.navigateToSampleShowtimePageWithAvailability(bookingPage);

        // click book ticket without choosing seats
        ExtentReportManager.info("Click button Book Ticket without choosing any seats");
        bookingPage.clickBookTicketsButton();

        // Verify error alert for empty seat selection
        ExtentReportManager.info("Verify empty seat selection alert displays with correct text");
        BookingVerificationHelper.verifyNoSeatSelectedDialog(bookingPage, getDriver(), softAssert);

        softAssert.assertAll();
    }
}