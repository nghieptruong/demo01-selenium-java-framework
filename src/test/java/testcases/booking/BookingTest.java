package testcases.booking;

import base.BaseTest;
import helpers.utils.PickRandomHelper;
import helpers.verifications.BookingVerificationHelper;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.LoginPage;
import pages.BookingPage;
import reports.ExtentReportManager;

import java.util.List;

import static helpers.actions.BookingActionHelper.*;

public class BookingTest extends BaseTest {

    BookingPage bookingPage;

    @Test(groups = {"integration", "booking", "smoke", "critical"})
    public void testValidBookingLoggedinUser() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        LoginPage loginPage = new LoginPage(getDriver());
        bookingPage = new BookingPage(getDriver());

        ExtentReportManager.info("Login as test user for booking test");
        loginAsBookingUser(loginPage);

        ExtentReportManager.info("Navigate to sample showtime page");
        navigateToAvailableShowtimePage(bookingPage);

        ExtentReportManager.info("Select sample available seats and confirm booking");
        List<String> seatsToBook = selectSampleSeats(bookingPage);  // Default to 2 seats if not specified
        bookingPage.clickBookTicketsButton();

        // Verify booking success - success alert displayed with correct message, seats no longer available after refresh
        ExtentReportManager.info("Verify booking success");
        BookingVerificationHelper.verifyBookingSuccess(bookingPage, seatsToBook, getDriver(), softAssert);

        softAssert.assertAll();
    }

    @Test(groups = {"component", "booking", "negative"})
    public void testInvalidBooking_NoSeatSelected() throws Exception {
        LoginPage loginPage = new LoginPage(getDriver());
        bookingPage = new BookingPage(getDriver());

        ExtentReportManager.info("Login as test user for booking test");
        loginAsBookingUser(loginPage);

        ExtentReportManager.info("Navigate to sample showtime page");
        navigateToAvailableShowtimePage(bookingPage);

        ExtentReportManager.info("Attempt to book without selecting any seats");
        bookingPage.clickBookTicketsButton();

        ExtentReportManager.info("Verify booking failure due to no seat selection");
        BookingVerificationHelper.verifyNoSeatSelectedError(bookingPage);
    }

    @Test(groups = {"component", "booking", "negative"})
    public void testUnauthenticatedBooking() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        bookingPage = new BookingPage(getDriver());

        ExtentReportManager.info("Navigate to sample showtime page without logging in");
        navigateToAvailableShowtimePage(bookingPage);

        ExtentReportManager.info("Select sample available seats and confirm booking");
        List<String> availableSeats = bookingPage.getAvailableSeatNumbers();
        List<String> seatsToBook = PickRandomHelper.getRandomSamplesFromList(availableSeats, 2);

        bookingPage.selectAvailableSeats(seatsToBook);
        bookingPage.clickBookTicketsButton();

        ExtentReportManager.info("Verify booking is blocked for guest user");
        BookingVerificationHelper.verifyBookingBlockedForGuest(bookingPage, seatsToBook, getDriver(), softAssert);

        softAssert.assertAll();
    }

}
