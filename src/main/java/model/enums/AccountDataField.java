package model.enums;

public enum AccountDataField {
    USERNAME("taiKhoan"),
    PASSWORD("matKhau"),
    FULL_NAME("hoTen"),
    EMAIL("email"),
    PHONE_NUMBER("soDt"),
    USER_TYPE("outlined-age-native-simple");

    private final String fieldId;

    AccountDataField(String fieldId) {
        this.fieldId = fieldId;
    }

    public String getFieldId() {
        return fieldId;
    }
}