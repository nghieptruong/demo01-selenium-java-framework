package api.services;

import api.ApiClient;
import api.ApiEndpoints;
import io.restassured.common.mapper.TypeRef;
import model.api.request.RegisterRequestPayload;
import model.UserAccount;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.InvalidParameterException;
import java.util.List;

public class UserService {

    private final ApiClient apiClient;
    private static final Logger LOG = LogManager.getLogger(UserService.class);

    public UserService() {
        this.apiClient = new ApiClient(ApiEndpoints.baseUri);
    }

    public void sendRegisterRequest(RegisterRequestPayload request) {
        this.apiClient
                .withBody(request)
                .post(ApiEndpoints.USER_REGISTER_ENDPOINT)
                .then()
                .statusCode(200);
    }

    /**
     * Get user details by username.
     * The API returns an array with a single user object.
     *
     * @param username The username to search for
     * @return UserAccount object
     * @throws RuntimeException if user is not found
     */
    public UserAccount getUserDetails(String username) {
        if (username == null || username.isEmpty()) {
            throw new InvalidParameterException("Username is null or empty");
        }

        List<UserAccount> users = this.apiClient
                .withQueryParam("MaNhom", "GP09")
                .withQueryParam("tuKhoa", username)
                .getAndDeserialize(ApiEndpoints.USER_SEARCH_ENDPOINT, new TypeRef<>() {});

        if (users.size() > 1) {
            throw new RuntimeException("Found more than one user with username: " + username);
        }

        if (users == null || users.isEmpty()) {
            LOG.warn("No user found for username " + username);
            return null;
        }

        return users.getFirst();
    }

    public boolean isAccountExisting(String username) {
        UserAccount user = getUserDetails(username);
        if (user == null) {
            return false;
        }
        return true;
    }

}
