package helpers.actions;

import helpers.providers.BookingSampleProvider;
import helpers.providers.RandomSampleProvider;
import model.api.response.ShowtimeBooking;
import model.ui.OrderEntry;
import model.ui.ShowtimeDetails;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pages.BookingPage;
import reports.ExtentReportManager;

import java.util.List;

/**
 * Helper class for common booking-related actions.
 * Provides reusable methods for booking flows used across component and E2E tests.
 */
public class BookingActionHelper {

    private static final Logger LOG = LogManager.getLogger(BookingActionHelper.class);

    /**
     * Navigates to a random showtime page that has available seats (at least 5 seats available if not specified).
     *
     * @param bookingPage The ShowtimePage instance
     * @throws Exception if no showtimes with available seats are found
     */
    public static void navigateToSampleShowtimePageWithAvailability(BookingPage bookingPage) throws Exception {
        ShowtimeBooking showtime = BookingSampleProvider.getShowtimeWithAvailableSeats();
        String showtimeId = showtime.getShowtimeId();

        LOG.info("Navigating to showtime page with ID: " + showtimeId);
        bookingPage.navigateToShowtimePage(showtimeId);
    }

    public static OrderEntry bookSeatsAndCollectOrderDetails(BookingPage bookingPage, List<String> seatsToBook) {

        ShowtimeDetails showtimeDetails = bookingPage.getShowtimeDetailsFromSummary();

        bookingPage.selectSeatsBySeatNumbers(seatsToBook);
        String totalPrice = bookingPage.getTotalPriceInSummary();
        String purchaseTimestamp = bookingPage.confirmBookingAndGetPurchaseTimestamp();

        OrderEntry newOrder = new OrderEntry();
        newOrder.setPurchaseDatetime(purchaseTimestamp)
                .setMovieName(showtimeDetails.getMovieName())
                .setCinemaBranchName(showtimeDetails.getCinemaBranchName())
                .setTheaterName(showtimeDetails.getTheaterName())
                .setPrice(Integer.parseInt(totalPrice))
                .setSeatNumbers(seatsToBook);

        ExtentReportManager.info("Complete order with details: " + newOrder);
        return newOrder;
    }

}
