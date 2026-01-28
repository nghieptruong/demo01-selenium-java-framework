package model.enums;

public enum RegisterField {
    USERNAME("taiKhoan"),
    PASSWORD("matKhau"),
    CONFIRM_PASSWORD("confirmPassWord"),
    FULL_NAME("hoTen"),
    EMAIL("email");

    private final String fieldId;

    RegisterField(String fieldId) {
        this.fieldId = fieldId;
    }

    public String getFieldId() {
        return fieldId;
    }

}