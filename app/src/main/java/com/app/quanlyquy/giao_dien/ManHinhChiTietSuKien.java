package com.app.quanlyquy.giao_dien;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton; // Nhớ import ImageButton
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.quanlyquy.R;
import com.app.quanlyquy.dactinh.SuKien;
import com.app.quanlyquy.dactinh.ThanhVien;
import com.app.quanlyquy.dulieu.QuanLyDuLieu;
import com.app.quanlyquy.giao_dien.bophukiens.BoThichNghiThanhVien;

import java.util.List;
import java.util.UUID;

public class ManHinhChiTietSuKien extends AppCompatActivity {
    private String maSuKien;
    private QuanLyDuLieu quanLyDuLieu;
    private SuKien suKien;
    private TextView tvTenSK, tvMaSK, tvSoTienQuy, tvTiLeDong;
    private EditText etTenThanhVienMoi;
    private Button btnSapXep;
    private RecyclerView rvThanhVien;
    private int kieuSapXep = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.man_hinh_chi_tiet_su_kien);

        maSuKien = getIntent().getStringExtra("MA_SU_KIEN");
        quanLyDuLieu = new QuanLyDuLieu(this);

        tvTenSK = findViewById(R.id.tvTenSuKienChiTiet);
        tvMaSK = findViewById(R.id.tvMaSuKienChiTiet);
        tvSoTienQuy = findViewById(R.id.tvSoTienQuyHienCo);
        tvTiLeDong = findViewById(R.id.tvTiLeDongQuy);
        etTenThanhVienMoi = findViewById(R.id.etTenThanhVienMoi);
        Button btnThemTV = findViewById(R.id.btnThemThanhVien);
        btnSapXep = findViewById(R.id.btnSapXepThanhVien);
        Button btnSaoChep = findViewById(R.id.btnSaoChepThanhVien);
        Button btnHoatDongChi = findViewById(R.id.btnChuyenHoatDongChi);
        Button btnXoaSuKien = findViewById(R.id.btnXoaSuKien);
        rvThanhVien = findViewById(R.id.rvDanhSachThanhVien);

        rvThanhVien.setLayoutManager(new LinearLayoutManager(this));

        btnThemTV.setOnClickListener(v -> themThanhVien());
        btnSapXep.setOnClickListener(v -> doiKieuSapXep());
        btnSaoChep.setOnClickListener(v -> startActivity(new Intent(this, ManHinhSaoChepThanhVien.class).putExtra("MA_SU_KIEN_NGUON", maSuKien)));
        btnHoatDongChi.setOnClickListener(v -> startActivity(new Intent(this, ManHinhHoatDongChi.class).putExtra("MA_SU_KIEN", maSuKien).putExtra("CHIDOC", false)));
        btnXoaSuKien.setOnClickListener(v -> xoaSuKien());

        // --- BỔ SUNG XỬ LÝ NÚT BACK (MŨI TÊN) Ở ĐÂY ---
        ImageButton btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        capNhatGiaoDien();
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void capNhatGiaoDien() {
        suKien = quanLyDuLieu.timSuKienTheoMa(maSuKien);
        if (suKien == null) { finish(); return; }

        tvTenSK.setText(suKien.getTenSuKien());
        tvMaSK.setText("Mã SK: " + suKien.getMaSuKien());
        tvSoTienQuy.setText(String.format("Quỹ hiện có: %,.0f VNĐ", suKien.tinhTongQuyHienTai()));

        List<ThanhVien> ds = suKien.getDanhSachThanhVien();
        int daDong = 0;
        for (ThanhVien tv : ds) if (tv.isDaDongTien()) daDong++;
        tvTiLeDong.setText("Đã đóng: " + daDong + "/" + ds.size());

        if (kieuSapXep == 1) ds.sort((a, b) -> Boolean.compare(b.isDaDongTien(), a.isDaDongTien()));
        else if (kieuSapXep == 2) ds.sort((a, b) -> Boolean.compare(a.isDaDongTien(), b.isDaDongTien()));
        else ds.sort((a, b) -> a.getTenThanhVien().compareToIgnoreCase(b.getTenThanhVien()));

        BoThichNghiThanhVien adapter = new BoThichNghiThanhVien(ds, true, new BoThichNghiThanhVien.OnThanhVienListener() {
            @Override
            public void onTichDongTien(ThanhVien tv, boolean isChecked) {
                tv.setDaDongTien(isChecked);
                quanLyDuLieu.capNhatSuKien(suKien);
                capNhatGiaoDien();
            }
            @Override
            public void onSuaThanhVien(ThanhVien tv) { hienDialogSua(tv); }
            @Override
            public void onXoaThanhVien(ThanhVien tv) {
                suKien.getDanhSachThanhVien().remove(tv);
                quanLyDuLieu.capNhatSuKien(suKien);
                capNhatGiaoDien();
            }
        });
        rvThanhVien.setAdapter(adapter);
    }

    private void themThanhVien() {
        String ten = etTenThanhVienMoi.getText().toString().trim();
        if (ten.isEmpty()) return;
        suKien.getDanhSachThanhVien().add(new ThanhVien(UUID.randomUUID().toString(), ten, false));
        quanLyDuLieu.capNhatSuKien(suKien);
        etTenThanhVienMoi.setText("");
        capNhatGiaoDien();
    }

    private void doiKieuSapXep() {
        kieuSapXep = (kieuSapXep + 1) % 3;
        btnSapXep.setText(kieuSapXep == 0 ? "Sắp xếp: A-Z" : (kieuSapXep == 1 ? "Đã đóng trước" : "Chưa đóng trước"));
        capNhatGiaoDien();
    }

    private void hienDialogSua(ThanhVien tv) {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Sửa tên thành viên");
        final EditText input = new EditText(this);
        input.setText(tv.getTenThanhVien());
        b.setView(input);
        b.setPositiveButton("Lưu", (d, w) -> {
            String t = input.getText().toString().trim();
            if (!t.isEmpty()) { tv.setTenThanhVien(t); quanLyDuLieu.capNhatSuKien(suKien); capNhatGiaoDien(); }
        });
        b.setNegativeButton("Hủy", null);
        b.show();
    }

    private void xoaSuKien() {
        new AlertDialog.Builder(this)
                .setTitle("Xóa sự kiện")
                .setMessage("Xóa sự kiện này sẽ làm trống mã và giải phóng hoàn toàn?")
                .setPositiveButton("Xóa", (d, w) -> {
                    List<SuKien> ds = quanLyDuLieu.layDanhSachSuKien();
                    ds.removeIf(sk -> sk.getMaSuKien().equals(maSuKien));
                    quanLyDuLieu.luuDanhSachSuKien(ds);
                    finish();
                }).setNegativeButton("Hủy", null).show();
    }
}