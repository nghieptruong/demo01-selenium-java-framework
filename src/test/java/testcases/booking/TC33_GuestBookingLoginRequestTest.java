package testcases.booking;

import base.BaseTest;
import helpers.actions.BookingActionHelper;
import helpers.providers.BookingSampleProvider;
import helpers.verifications.BookingVerificationHelper;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.BookingPage;
import reports.ExtentReportManager;

import java.util.List;

public class TC33_GuestBookingLoginRequestTest extends BaseTest {

    @Test
    public void testGuestBookingIsBlocked() throws Exception {

        SoftAssert softAssert = new SoftAssert();
        BookingPage bookingPage = new BookingPage(getDriver());

        // Find a random showtime with available seats (from API data) and navigate to its booking page
        ExtentReportManager.info("Navigate to showtime booking page");
        BookingActionHelper.navigateToSampleShowtimePageWithAvailability(bookingPage);

        // Find random sample of available seats to book (randomly between 1 and 5 seats if not specified)
        ExtentReportManager.info("Select seats and confirm booking");
        List<String> seatsToBook = BookingSampleProvider.getSampleAvailableSeats(bookingPage);

        bookingPage.selectSeatsBySeatNumbers(seatsToBook);
        bookingPage.confirmAndCloseDialog();

        // Verify booking blocked for guest user - login required alert displayed with correct message, seats remain available after refresh
        ExtentReportManager.info("Verify booking is blocked for guest user");
        BookingVerificationHelper.verifyBookingBlockedForGuest(bookingPage, seatsToBook, getDriver(), softAssert);

        softAssert.assertAll();
    }
}