package helpers.providers;

import model.api.request.RegisterRequest;
import model.UserAccount;
import model.ui.RegisterInputs;
import net.datafaker.Faker;

import java.util.UUID;

/**
 * Generates random test data for authentication and account forms using Faker library.
 */
public class AuthTestDataGenerator {

    private static final Faker faker = new Faker();

    // ---- Generate valid register/account data ----
    public static RegisterInputs generateValidRegisterFormInputs() {
        UserAccount newUser = generateNewUserAccountInfo();
        RegisterInputs inputs = new RegisterInputs(
                newUser.getTaiKhoan(),
                newUser.getMatKhau(),
                newUser.getMatKhau(),  // confirm password matches password
                newUser.getHoTen(),
                newUser.getEmail()
        );
        return inputs;
    }

    public static RegisterRequest generateRegisterRequestPayload() {
        UserAccount newUser = generateNewUserAccountInfo();
        return RegisterRequest.builder()
                .taiKhoan(newUser.getTaiKhoan())
                .matKhau(newUser.getMatKhau())
                .hoTen(newUser.getHoTen())
                .email(newUser.getEmail())
                .soDt(newUser.getSoDt())
                .build();
    }

    public static String generateNewUniqueEmail() {
        String defaultDomain = "@example.com";
        return UUID.randomUUID() + defaultDomain;
    }

    // ---- Generate invalid auth data ----
    public static String generateInvalidShortPassword() {
        return faker.internet().password(1, 5);
    }

    public static String generateInvalidNameContainingNumbers() {
        return faker.name().firstName() + faker.number().digits(3);
    }

    // ---- Generate modified valid data based on current values ----
    public static String generateNewName(String currentName) {
        return currentName + faker.name().firstName();
    }

    public static String generateNewPhoneNumber(String currentPhoneNumber) {
        if (currentPhoneNumber.isEmpty())
            return faker.phoneNumber().cellPhone();
        else return currentPhoneNumber + faker.number().digits(2);
    }

    public static String generateNewPassword(String currentPassword) {
        return currentPassword + faker.number().digits(2);
    }

    // --- Private helper methods ----
    private static UserAccount generateNewUserAccountInfo() {
        String taiKhoan = UUID.randomUUID().toString();
        String hoTen = faker.name().fullName();
        String matKhau = faker.internet().password();
        String phoneNr = faker.phoneNumber().cellPhone();
        String defaultDomain = "@example.com";

        return UserAccount.builder()
                .taiKhoan(taiKhoan)
                .hoTen(hoTen)
                .email(taiKhoan + defaultDomain)
                .matKhau(matKhau)
                .soDt(phoneNr)
                .build();
    }
}
