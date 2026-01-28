package testcases.booking;

import base.BaseTest;
import config.ConfigManager;
import helpers.actions.AuthActionHelper;
import helpers.actions.BookingActionHelper;
import helpers.providers.BookingSampleProvider;
import helpers.verifications.BookingVerificationHelper;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.BookingPage;
import pages.LoginPage;
import reports.ExtentReportManager;

import java.util.List;

public class TC32_UserSeatSelectionAndConfirmationTest extends BaseTest {

    @BeforeMethod(alwaysRun = true)
    public void setUpMethod() {
        String username = ConfigManager.getDefaultUserUsername();
        String password = ConfigManager.getDefaultUserPassword();

        LoginPage loginPage = new LoginPage(getDriver());
        AuthActionHelper.login(loginPage, username, password);
    }

    @Test
    public void testValidBookingLoggedinUser() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        BookingPage bookingPage = new BookingPage(getDriver());

        // Find a random showtime with available seats (from API data) and navigate to its booking page
        ExtentReportManager.info("Navigate to showtime booking page");
        BookingActionHelper.navigateToSampleShowtimePageWithAvailability(bookingPage);

        // Find random sample of available seats to book (randomly between 1 and 5 seats if not specified)
        ExtentReportManager.info("Select seats and confirm booking");
        List<String> seatsToBook = BookingSampleProvider.getSampleAvailableSeats(bookingPage);

        bookingPage.selectSeatsBySeatNumbers(seatsToBook);
        bookingPage.clickBookTicketsButton();

        // Verify booking success - success alert displayed with correct message, seats no longer available after refresh
        ExtentReportManager.info("Verify booking success");
        BookingVerificationHelper.verifyBookingSuccess(bookingPage, seatsToBook, getDriver(), softAssert);

        softAssert.assertAll();
    }
}