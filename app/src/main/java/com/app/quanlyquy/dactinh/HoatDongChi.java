package com.app.quanlyquy.dactinh;

public class HoatDongChi {
    private final String maChi;
    private String tenNguoiChi;
    private double soTienChi;
    private String noiDungChi;
    private final String thoiGianChi;

    public HoatDongChi(String maChi, String tenNguoiChi, double soTienChi, String noiDungChi, String thoiGianChi) {
        this.maChi = maChi;
        this.tenNguoiChi = tenNguoiChi;
        this.soTienChi = soTienChi;
        this.noiDungChi = noiDungChi;
        this.thoiGianChi = thoiGianChi;
    }

    public String getMaChi() { return maChi; }
    public String getTenNguoiChi() { return tenNguoiChi; }
    public void setTenNguoiChi(String tenNguoiChi) { this.tenNguoiChi = tenNguoiChi; }
    public double getSoTienChi() { return soTienChi; }
    public void setSoTienChi(double soTienChi) { this.soTienChi = soTienChi; }
    public String getNoiDungChi() { return noiDungChi; }
    public void setNoiDungChi(String noiDungChi) { this.noiDungChi = noiDungChi; }
    public String getThoiGianChi() { return thoiGianChi; }
}