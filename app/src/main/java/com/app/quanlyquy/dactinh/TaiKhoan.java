package com.app.quanlyquy.dactinh;

public class TaiKhoan {
    private final String tenDangNhap;
    private String matKhau;
    private final String vaiTro; // "ADMIN" hoặc "THU_QUY"
    private boolean daKhoa;

    public TaiKhoan(String tenDangNhap, String matKhau, String vaiTro) {
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.vaiTro = vaiTro;
        this.daKhoa = false;
    }

    public String getTenDangNhap() { return tenDangNhap; }
    public String getMatKhau() { return matKhau; }
    public void setMatKhau(String matKhau) { this.matKhau = matKhau; }
    public String getVaiTro() { return vaiTro; }
    public boolean isDaKhoa() { return daKhoa; }
    public void setDaKhoa(boolean daKhoa) { this.daKhoa = daKhoa; }
}