package com.app.quanlyquy.dulieu;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.app.quanlyquy.dactinh.SuKien;
import com.app.quanlyquy.dactinh.TaiKhoan;

import java.lang.reflect.Type;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class QuanLyDuLieu {
    private static final String PREF_NAME = "QUAN_LY_QUY_CLB_JSON_PREFS";
    private static final String KEY_TAI_KHOAN = "DANH_SACH_TAI_KHOAN_JSON";
    private static final String KEY_SU_KIEN = "DANH_SACH_SU_KIEN_JSON";

    private final SharedPreferences prefs;
    private final Gson gson;

    public QuanLyDuLieu(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
        khoiTaoMacDinhAdmin();
    }

    private void khoiTaoMacDinhAdmin() {
        List<TaiKhoan> dsTaiKhoan = layDanhSachTaiKhoan();
        if (dsTaiKhoan.isEmpty()) {
            dsTaiKhoan.add(new TaiKhoan("admin", "admin123", "ADMIN"));
            luuDanhSachTaiKhoan(dsTaiKhoan);
        }
    }

    public List<TaiKhoan> layDanhSachTaiKhoan() {
        String json = prefs.getString(KEY_TAI_KHOAN, null);
        if (json == null) return new ArrayList<>();
        Type type = new TypeToken<List<TaiKhoan>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public void luuDanhSachTaiKhoan(List<TaiKhoan> ds) {
        prefs.edit().putString(KEY_TAI_KHOAN, gson.toJson(ds)).apply();
    }

    public List<SuKien> layDanhSachSuKien() {
        String json = prefs.getString(KEY_SU_KIEN, null);
        if (json == null) return new ArrayList<>();
        Type type = new TypeToken<List<SuKien>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public void luuDanhSachSuKien(List<SuKien> ds) {
        prefs.edit().putString(KEY_SU_KIEN, gson.toJson(ds)).apply();
    }

    public List<SuKien> laySuKienTheoTaiKhoan(String tenTaiKhoan) {
        List<SuKien> tatCa = layDanhSachSuKien();
        List<SuKien> ketQua = new ArrayList<>();
        for (SuKien sk : tatCa) {
            if (sk.getTenTaiKhoanTao().equals(tenTaiKhoan)) {
                ketQua.add(sk);
            }
        }
        return ketQua;
    }

    // Thuật toán tự sinh mã ngẫu nhiên 4-10 ký tự, kiểm tra không trùng lặp toàn hệ thống
    public String taoMaSuKienNgauNhienDuyNhat() {
        String kyTuHopLe = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        List<SuKien> dsSuKien = layDanhSachSuKien();
        String maMoi;
        boolean isTrung;

        do {
            int doDai = 4 + random.nextInt(7); // Từ 4 đến 10 ký tự
            StringBuilder sb = new StringBuilder(doDai);
            for (int i = 0; i < doDai; i++) {
                sb.append(kyTuHopLe.charAt(random.nextInt(kyTuHopLe.length())));
            }
            maMoi = sb.toString();

            isTrung = false;
            for (SuKien sk : dsSuKien) {
                if (sk.getMaSuKien().equals(maMoi)) {
                    isTrung = true;
                    break;
                }
            }
        } while (isTrung);

        return maMoi;
    }

    public SuKien timSuKienTheoMa(String maSuKien) {
        for (SuKien sk : layDanhSachSuKien()) {
            if (sk.getMaSuKien().equalsIgnoreCase(maSuKien.trim())) {
                return sk;
            }
        }
        return null;
    }

    public void capNhatSuKien(SuKien suKienCapNhat) {
        List<SuKien> ds = layDanhSachSuKien();
        for (int i = 0; i < ds.size(); i++) {
            if (ds.get(i).getMaSuKien().equals(suKienCapNhat.getMaSuKien())) {
                ds.set(i, suKienCapNhat);
                break;
            }
        }
        luuDanhSachSuKien(ds);
    }
    public void capNhatTaiKhoan(TaiKhoan taiKhoanCapNhat) {
        List<TaiKhoan> ds = layDanhSachTaiKhoan();
        for (int i = 0; i < ds.size(); i++) {
            if (ds.get(i).getTenDangNhap().equals(taiKhoanCapNhat.getTenDangNhap())) {
                ds.set(i, taiKhoanCapNhat);
                break;
            }
        }
        luuDanhSachTaiKhoan(ds);
    }
}