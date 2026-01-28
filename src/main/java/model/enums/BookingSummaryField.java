package model.enums;

public enum BookingSummaryField {
    CINEMA_BRANCH_NAME("Cụm Rạp:"),
    CINEMA_ADDRESS("Địa chỉ:"),
    THEATER_NAME("Rạp:"),
    SHOWING_DATETIME("Ngày giờ chiếu:"),
    MOVIE_NAME("Tên Phim:"),
    SEAT_NUMBERS("Chọn: "),
    PRICE("VND");

    private final String label;

    BookingSummaryField(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}