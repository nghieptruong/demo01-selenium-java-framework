package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Base user account data model.
 * Shared between API requests and UI form inputs.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserAccount {
    protected String taiKhoan;
    protected String hoTen;
    protected String email;
    protected String soDt;
    protected String matKhau;

    @lombok.Builder.Default
    protected String maLoaiNguoiDung = "KhachHang";
}