package api.services;

import api.ApiClient;
import api.ApiEndpoints;
import io.restassured.common.mapper.TypeRef;
import model.api.request.RegisterRequest;
import model.UserAccount;

import java.util.List;

public class UserService {

    private final ApiClient apiClient;

    public UserService() {
        this.apiClient = new ApiClient(ApiEndpoints.baseUri);
    }

    public void sendRegisterRequest(RegisterRequest request) {
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
        List<UserAccount> users = this.apiClient
                .withQueryParam("MaNhom", "GP09")
                .withQueryParam("tuKhoa", username)
                .getAndDeserialize(ApiEndpoints.USER_SEARCH_ENDPOINT, new TypeRef<>() {});

        if (users == null || users.isEmpty()) {
            throw new RuntimeException("User not found with username: " + username);
        }
        return users.getFirst();
    }

}
