package helpers.actions;

import model.enums.MovieDropdownField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pages.HomePage;

public class BrowsingActionHelper {

    private static final Logger LOG = LogManager.getLogger(BrowsingActionHelper.class);

    // Flows of actions to trigger specific missing filter alert
    /**
     * Trigger missing filter alert by selecting filters before the specified missing one.
     * Uses ordinal-based logic: movie (0) &lt; cinema (1) &lt; showtime (2).
     *
     * @param missingFilter The filter that should be missing (not selected)
     * @param movieTitle Movie title to select (if needed)
     * @param cinemaLocation Cinema location to select (if needed)
     */
    public static void triggerMissingFilterAlert(HomePage homePage, MovieDropdownField missingFilter, String movieTitle, String cinemaLocation) {
        switch (missingFilter) {
            case MOVIE:
                homePage.showtimeFilterDropdowns.clickApplyFilterBtn();
                break;
            case CINEMA:
                homePage.showtimeFilterDropdowns.selectMovieByMovieTitle(movieTitle);
                homePage.showtimeFilterDropdowns.clickApplyFilterBtn();
                break;
            case SHOWTIME:
                homePage.showtimeFilterDropdowns.selectMovieByMovieTitle(movieTitle);
                homePage.showtimeFilterDropdowns.selectCinemaBranchByName(cinemaLocation);
                homePage.showtimeFilterDropdowns.clickApplyFilterBtn();
                break;
            default:
                LOG.warn("Unknown missing filter: " + missingFilter);
        }
    }
}
