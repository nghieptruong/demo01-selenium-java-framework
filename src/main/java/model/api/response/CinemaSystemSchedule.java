package model.api.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * Showtimes organized by cinema system from API response.
 * Contains hierarchical showtime data: Cinema System → Branches → Movies → Showtimes
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CinemaSystemSchedule {

    private String maHeThongRap;
    private String tenHeThongRap;
    private List<CinemaBranchShowtimes> lstCumRap;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CinemaBranchShowtimes {
        private String maCumRap;
        private String tenCumRap;
        private List<MovieShowtimes> danhSachPhim;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MovieShowtimes {
        private String maPhim;
        private String tenPhim;
        private List<ShowtimeDetails> lstLichChieuTheoPhim;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ShowtimeDetails {
        private String maLichChieu;
    }

}

