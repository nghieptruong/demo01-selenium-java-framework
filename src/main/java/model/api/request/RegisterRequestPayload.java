package model.api.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import model.UserAccount;

/**
 * API request model for user registration.
 * Contains user account data plus group identifier required by the API.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class RegisterRequestPayload extends UserAccount {
    @lombok.Builder.Default
    private String maNhom = "GP09";
}

