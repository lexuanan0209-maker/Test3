package com.app.quanlyquy.dactinh;

import java.util.ArrayList;
import java.util.List;

public class SuKien {
    private final String maSuKien;
    private final String tenSuKien;
    private final double soTienCanThu;
    private final String thoiGianTao;
    private final String tenTaiKhoanTao;
    private final List<ThanhVien> danhSachThanhVien;
    private final List<HoatDongChi> danhSachHoatDongChi;

    public SuKien(String maSuKien, String tenSuKien, double soTienCanThu, String thoiGianTao, String tenTaiKhoanTao) {
        this.maSuKien = maSuKien;
        this.tenSuKien = tenSuKien;
        this.soTienCanThu = soTienCanThu;
        this.thoiGianTao = thoiGianTao;
        this.tenTaiKhoanTao = tenTaiKhoanTao;
        this.danhSachThanhVien = new ArrayList<>();
        this.danhSachHoatDongChi = new ArrayList<>();
    }

    public String getMaSuKien() { return maSuKien; }
    public String getTenSuKien() { return tenSuKien; }
    public double getSoTienCanThu() { return soTienCanThu; }
    public String getThoiGianTao() { return thoiGianTao; }
    public String getTenTaiKhoanTao() { return tenTaiKhoanTao; }
    public List<ThanhVien> getDanhSachThanhVien() { return danhSachThanhVien; }
    public List<HoatDongChi> getDanhSachHoatDongChi() { return danhSachHoatDongChi; }

    public double tinhTongQuyHienTai() {
        double tongThu = 0;
        for (ThanhVien tv : danhSachThanhVien) {
            if (tv.isDaDongTien()) tongThu += soTienCanThu;
        }
        double tongChi = 0;
        for (HoatDongChi chi : danhSachHoatDongChi) {
            tongChi += chi.getSoTienChi();
        }
        return tongThu - tongChi;
    }
}