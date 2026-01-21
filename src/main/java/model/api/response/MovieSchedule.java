package model.api.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Showtimes organized by movie from API response.
 * Contains hierarchical showtime data: Movie → Cinema Systems → Branches → Showtimes
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieSchedule {

    private List<CinemaSystemShowtimes> heThongRapChieu;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CinemaSystemShowtimes {
        private String maHeThongRap;
        private String tenHeThongRap;
        private List<CinemaBranchShowtimes> cumRapChieu;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CinemaBranchShowtimes {
        private String maCumRap;
        private String tenCumRap;
        private List<ShowtimeDetails> lichChieuPhim;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ShowtimeDetails {
        private String maLichChieu;
        private String maRap;
        private String tenRap;
        private String thoiLuong;
        private String ngayChieuGioChieu;
    }

    /**
     * Extract all showtime IDs from the nested structure.
     *
     * @return List of showtime IDs across all cinema systems and branches
     */
    public Set<String> getShowtimeIds() {
        return this.heThongRapChieu.stream()
                .flatMap(cinemaSys -> cinemaSys.getCumRapChieu().stream())
                .flatMap(branch -> branch.getLichChieuPhim().stream())
                .map(ShowtimeDetails::getMaLichChieu)
                .collect(Collectors.toSet());
    }

    /**
     * Get all cinema branches as a map of branch ID to branch name.
     *
     * @return Map where key is branch ID and value is branch name
     */
    public Map<String, String> getCinemaBranchIdToNameMap() {
        Map<String, String> cinemaBranches = new HashMap<>();
        for (CinemaSystemShowtimes cinemaSys : heThongRapChieu) {
            for (CinemaBranchShowtimes branch : cinemaSys.getCumRapChieu()) {
                String branchId = branch.getMaCumRap().trim();
                String branchName = branch.getTenCumRap().trim();
                cinemaBranches.put(branchId, branchName);
            }
        }
        return cinemaBranches;
    }

}
