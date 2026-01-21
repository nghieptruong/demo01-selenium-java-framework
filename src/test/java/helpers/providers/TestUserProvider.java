package helpers.providers;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.TestUser;
import model.TestUserType;
import model.ui.LoginInputs;

import java.io.InputStream;
import java.util.Map;

/**
 * Provides test user data loaded from test-users.json.
 * Uses Jackson to parse JSON into TestUser objects.
 * Allows retrieval of users by TestUserType.
 * Used for component and integration tests (E2E tests create users via API).
 */
public class TestUserProvider {

    private static final Map<String, TestUser> users;

    static {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream inputStream = TestUserProvider.class.getClassLoader().getResourceAsStream("test-users.json");

            users = mapper.readValue(
                    inputStream,
                    mapper.getTypeFactory().constructMapType(
                            Map.class,
                            String.class,
                            TestUser.class
                    )
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to load test users", e);
        }
    }

    public static TestUser getUser(TestUserType type) {
        TestUser user = users.get(type.name().toLowerCase());
        if (user == null) {
            throw new IllegalArgumentException("User not found: " + type);
        }
        return user;
    }

    public static LoginInputs getUserCredentials(TestUserType type) {
        TestUser user = getUser(type);
        return new LoginInputs(user.getUsername(), user.getPassword());
    }

}