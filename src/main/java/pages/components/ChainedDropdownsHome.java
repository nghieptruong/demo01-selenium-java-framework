package pages.components;

import base.BasePage;
import utils.DateTimeNormalizer;
import model.ui.MovieDropdownFields;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Page component for chained showtime filter dropdowns on home page.
 * Handles movie, cinema, and showtime selection with dynamic options.
 */
public class ChainedDropdownsHome extends BasePage {

    // ============================================
    // ---- Component Elements ----
    // ============================================

    // ---- Dropdowns ----
    @FindBy(css = "select[name='film']")
    private WebElement selMovie;
    @FindBy(css = "select[name='cinema']")
    private WebElement selCinemaLocation;
    @FindBy(css = "select[name='date']")
    private WebElement selShowtime;

    // ---- Buttons ----
    @FindBy(xpath = "//div[@id='homeTool']//button")
    private WebElement btnFindTickets;

    // ---- Alerts ----
    @FindBy(css = "div[role='dialog']")
    private WebElement alertMissingFilter;
    @FindBy (xpath = "//div[@role='dialog']//h2")
    private WebElement lblMissingFilterAlertText;
    @FindBy(xpath = "//div[@role='dialog']//button[text()='Đã hiểu']")
    private WebElement btnCloseAlert;

    // ---- Static Fields & Initialization ----
    // Map for FilterType to HTML Select Name Mapping
    private Map<MovieDropdownFields, String> filterSelectNameMap;

    /**
     * Initialize mapping for FilterType to HTML select name attributes.
     * This eliminates switch statements in getOptionLocator().
     */
    private void initializeFilterSelectNameMap() {
        // Map FilterType to HTML select name attribute
        filterSelectNameMap = new HashMap<>();
        filterSelectNameMap.put(MovieDropdownFields.MOVIE, "film");
        filterSelectNameMap.put(MovieDropdownFields.CINEMA, "cinema");
        filterSelectNameMap.put(MovieDropdownFields.SHOWTIME, "date");
    }

    // ============================================
    // ---- Constructor ----
    // ============================================
    public ChainedDropdownsHome(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        initializeFilterSelectNameMap();
    }

    // ============================================
    // ---- Public Methods  ----
    // ============================================

    // ---- Wait Methods ----
    public void waitForDropdownsToLoad() {
        waitForVisibilityOfElementLocated(selMovie);
        waitForMovieOptionsToLoad();
    }

    public void waitForMovieOptionsToLoad(){
        By movieOption = getOptionLocator(MovieDropdownFields.MOVIE);
        waitForNestedElementToBePresent(selMovie, movieOption);
    }

    // ---- Interaction Methods ----
    public void clickFindTickets() {
        LOG.info("Click Find Tickets button");
        click(btnFindTickets);
    }

    public void selectMovieByMovieTitle(String movieTitle) {
        selectDropdownOptionByVisibleText(selMovie, movieTitle);
    }

    public void selectCinemaBranchByName(String cinemaLocation) {
        selectDropdownOptionByVisibleText(selCinemaLocation, cinemaLocation);
    }

    public void selectShowtimeById(String showtimeId) {
        selectDropdownOptionByValue(selShowtime, showtimeId);
    }

    public void applyFiltersAndFindTickets(String movieTitle, String cinemaLocation, String showtimeId) {
        LOG.info("Apply filters: movie = " + movieTitle + ", " +
                "cinema = " +cinemaLocation + ", showtime = " + showtimeId + "and click find tickets");
        selectMovieByMovieTitle(movieTitle);
        selectCinemaBranchByName(cinemaLocation);
        selectShowtimeById(showtimeId);
        clickFindTickets();
    }

    // ---- Getters ----
//    public List<String> getMovieOptionsText() {
//        By bySelMovie = getSelectLocator(FilterType.movie);
//        By byOptionMovie = getOptionLocator(FilterType.movie);
//        return getAllOptionsText(bySelMovie, byOptionMovie);
//    }

    public Map<String, String> getMovieOptionIdToTitleMap() {
        Map<String, String> movieTitlesWithIds = new HashMap<>();
        By bySelMovie = getSelectLocator(MovieDropdownFields.MOVIE);
        By byOptionMovie = getOptionLocator(MovieDropdownFields.MOVIE);

        List<WebElement> movieOptions = waitForAllNestedElementsToBePresent(bySelMovie, byOptionMovie);

        for (WebElement option : movieOptions) {
            String title = getText(option);
            String id = getFieldValue(option);
            movieTitlesWithIds.put(id, title);
        }
        return movieTitlesWithIds;
    }

    public Map<String, String> getCinemaBranchOptionIdToNameMap() {
        Map<String, String> cinemaNamesWithIds = new HashMap<>();
        By bySelCinema = getSelectLocator(MovieDropdownFields.CINEMA);
        By byOptionCinema = getOptionLocator(MovieDropdownFields.CINEMA);

        try {
            waitForNestedElementToBePresent(selCinemaLocation, byOptionCinema);
        } catch (Exception e) {
            LOG.warn("No cinema options found in dropdown");
            return cinemaNamesWithIds; // Return empty map if no options found
        }

        List<WebElement> cinemaOptions = waitForAllNestedElementsToBePresent(bySelCinema, byOptionCinema);

        for (WebElement option : cinemaOptions) {
            String name = getText(option);
            String id = getFieldValue(option);
            cinemaNamesWithIds.put(id, name);
        }
        return cinemaNamesWithIds;
    }

    public Map<String, String> getShowtimeOptionIdToDateTimeMap() {
        Map<String, String> showtimeDatetimesWithIds = new HashMap<>();
        By bySelShowtime = getSelectLocator(MovieDropdownFields.SHOWTIME);
        By byOptionShowtime = getOptionLocator(MovieDropdownFields.SHOWTIME);

        try {
            waitForNestedElementToBePresent(selShowtime, byOptionShowtime);
        } catch (Exception e) {
            LOG.warn("No showtime options found in dropdown");  // consider logging currently selected movie/cinema for debugging
            return showtimeDatetimesWithIds;                    // Return empty map if no options found
        }

        List<WebElement> showtimeOptions = waitForAllNestedElementsToBePresent(bySelShowtime, byOptionShowtime);

        for (WebElement option : showtimeOptions) {
            String datetime = getText(option);
            String normalizedDatetime = DateTimeNormalizer.normalize(datetime);

            String id = getFieldValue(option);

            showtimeDatetimesWithIds.put(id, normalizedDatetime);
        }
        return showtimeDatetimesWithIds;
    }



//    public List<String> getCinemaLocationOptionsText() {
//        By bySelCinema = getSelectLocator(FilterType.cinema);
//        By byOptionCinema = getOptionLocator(FilterType.cinema);
//        return getAllOptionsText(bySelCinema, byOptionCinema);
//    }
//
//    public List<String> getShowtimeOptionsText() {
//        By bySelShowtime = getSelectLocator(FilterType.showtime);
//        By byOptionShowtime = getOptionLocator(FilterType.showtime);
//        return getAllOptionsText(bySelShowtime, byOptionShowtime);
//    }

    // ---- Missing Filter Alerts Methods ----
    public void triggerMissingMovieFilterAlert() {
        clickFindTickets();
    }

    public void triggerMissingCinemaLocationFilterAlert(String movieTitle) {
        selectMovieByMovieTitle(movieTitle);
        clickFindTickets();
    }

    public void triggerMissingShowtimeFilterAlert(String movieTitle, String cinemaLocation) {
        selectMovieByMovieTitle(movieTitle);
        selectCinemaBranchByName(cinemaLocation);
        clickFindTickets();
    }

    /**
     * Trigger missing filter alert by selecting filters before the specified missing one.
     * Uses ordinal-based logic: movie (0) &lt; cinema (1) &lt; showtime (2).
     *
     * @param missingFilter The filter that should be missing (not selected)
     * @param movieTitle Movie title to select (if needed)
     * @param cinemaLocation Cinema location to select (if needed)
     */
    public void triggerMissingFilterAlert(MovieDropdownFields missingFilter, String movieTitle, String cinemaLocation) {
        switch (missingFilter) {
            case MOVIE:
                triggerMissingMovieFilterAlert();
                break;
            case CINEMA:
                triggerMissingCinemaLocationFilterAlert(movieTitle);
                break;
            case SHOWTIME:
                triggerMissingShowtimeFilterAlert(movieTitle, cinemaLocation);
                break;
            default:
                LOG.warn("Unknown missing filter: " + missingFilter);
        }
    }

    public boolean isMissingFilterAlertVisible() {
        return isElementDisplayedShort(alertMissingFilter);
    }

    public String getMissingFilterAlertText() {
        return getText(lblMissingFilterAlertText);
    }

    // ============================================
    // ---- Private Helper Methods ----
    // ============================================
    /**
     * Get the locator for dropdown options based on filter type.
     * Uses Map lookup instead of switch for better maintainability.
     *
     * @param filterType The type of filter (movie, cinema, showtime)
     * @return By locator for the dropdown options
     */
    private By getOptionLocator(MovieDropdownFields filterType) {
        String selectName = filterSelectNameMap.get(filterType);
        if (selectName == null) {
            LOG.warn("Unknown filter type: " + filterType);
            return null;
        }
        String optionXPath = String.format("//select[@name='%s']//option[not(@disabled)]", selectName);
        return By.xpath(optionXPath);
    }

    private By getSelectLocator(MovieDropdownFields filterType) {
        String selectName = filterSelectNameMap.get(filterType);
        if (selectName == null) {
            LOG.warn("Unknown filter type: " + filterType);
            return null;
        }
        String selectXPath = String.format("//select[@name='%s']", selectName);
        return By.xpath(selectXPath);
    }
}
