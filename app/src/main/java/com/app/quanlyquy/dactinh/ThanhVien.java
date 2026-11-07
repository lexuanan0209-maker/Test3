package com.app.quanlyquy.dactinh;

public class ThanhVien {
    private final String maThanhVien;
    private String tenThanhVien;
    private boolean daDongTien;

    public ThanhVien(String maThanhVien, String tenThanhVien, boolean daDongTien) {
        this.maThanhVien = maThanhVien;
        this.tenThanhVien = tenThanhVien;
        this.daDongTien = daDongTien;
    }

    public String getMaThanhVien() { return maThanhVien; }
    public String getTenThanhVien() { return tenThanhVien; }
    public void setTenThanhVien(String tenThanhVien) { this.tenThanhVien = tenThanhVien; }
    public boolean isDaDongTien() { return daDongTien; }
    public void setDaDongTien(boolean daDongTien) { this.daDongTien = daDongTien; }
}