package model.enums;

/**
 * Filter types for showtime selection dropdowns.
 */
public enum MovieDropdownField {
    MOVIE("film"),
    CINEMA("cinema"),
    SHOWTIME("date");

    private String label;

    MovieDropdownField(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
