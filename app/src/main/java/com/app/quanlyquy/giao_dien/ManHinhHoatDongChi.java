package com.app.quanlyquy.giao_dien;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton; // Nhớ import ImageButton
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.app.quanlyquy.R;
import com.app.quanlyquy.dactinh.HoatDongChi;
import com.app.quanlyquy.dactinh.SuKien;
import com.app.quanlyquy.dulieu.QuanLyDuLieu;
import com.app.quanlyquy.giao_dien.bophukiens.BoThichNghiHoatDongChi;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class ManHinhHoatDongChi extends AppCompatActivity {
    private String maSuKien;
    private boolean isChiDoc;
    private QuanLyDuLieu quanLyDuLieu;
    private SuKien suKien;
    private TextView tvQuy;
    private RecyclerView rvChi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.man_hinh_hoat_dong_chi);

        maSuKien = getIntent().getStringExtra("MA_SU_KIEN");
        isChiDoc = getIntent().getBooleanExtra("CHIDOC", false);
        quanLyDuLieu = new QuanLyDuLieu(this);

        tvQuy = findViewById(R.id.tvQuyHienTaiChi);
        Button btnThem = findViewById(R.id.btnThemHoatDongChi);
        rvChi = findViewById(R.id.rvDanhSachHoatDongChi);
        rvChi.setLayoutManager(new LinearLayoutManager(this));

        if (isChiDoc) btnThem.setVisibility(View.GONE);
        btnThem.setOnClickListener(v -> hienDialog(null));

        // --- XỬ LÝ NÚT BACK (MŨI TÊN QUAY LẠI) ---
        ImageButton btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        capNhat();
    }

    @SuppressLint("DefaultLocale")
    private void capNhat() {
        suKien = quanLyDuLieu.timSuKienTheoMa(maSuKien);
        if (suKien == null) return;
        tvQuy.setText(String.format("Quỹ hiện có: %,.0f VNĐ", suKien.tinhTongQuyHienTai()));
        rvChi.setAdapter(new BoThichNghiHoatDongChi(suKien.getDanhSachHoatDongChi(), !isChiDoc, this::hienDialog));
    }

    private void hienDialog(HoatDongChi chiCu) {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        View v = LayoutInflater.from(this).inflate(R.layout.dialog_them_sua_chi, null);
        b.setView(v);

        EditText etNguoi = v.findViewById(R.id.etTenNguoiChi);
        EditText etTien = v.findViewById(R.id.etSoTienChi);
        EditText etNoiDung = v.findViewById(R.id.etNoiDungChi);

        if (chiCu != null) {
            etNguoi.setText(chiCu.getTenNguoiChi());
            etTien.setText(String.valueOf((long) chiCu.getSoTienChi()));
            etNoiDung.setText(chiCu.getNoiDungChi());
        }

        b.setPositiveButton("Lưu", (d, w) -> {
            String nguoi = etNguoi.getText().toString().trim();
            String t = etTien.getText().toString().trim();
            String nd = etNoiDung.getText().toString().trim();

            if (nguoi.isEmpty() || t.isEmpty() || nd.isEmpty()) return;
            double tien = Double.parseDouble(t);

            if (chiCu == null) {
                String thoiGian = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                suKien.getDanhSachHoatDongChi().add(new HoatDongChi(UUID.randomUUID().toString(), nguoi, tien, nd, thoiGian));
            } else {
                chiCu.setTenNguoiChi(nguoi);
                chiCu.setSoTienChi(tien);
                chiCu.setNoiDungChi(nd);
            }
            quanLyDuLieu.capNhatSuKien(suKien);
            capNhat();
        });
        b.setNegativeButton("Hủy", null).show();
    }
}