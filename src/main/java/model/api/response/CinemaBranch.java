package model.api.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Cinema branch data model from API response.
 * Represents a specific cinema location within a cinema system.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CinemaBranch {
    private String maCumRap;
    private String tenCumRap;
    private String diaChi;
}

