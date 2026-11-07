package com.app.quanlyquy.giao_dien;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog; // Thêm import này cho hộp thoại
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.app.quanlyquy.R;
import com.app.quanlyquy.dactinh.SuKien;
import com.app.quanlyquy.dulieu.QuanLyDuLieu;
import com.app.quanlyquy.giao_dien.bophukiens.BoThichNghiSuKien;
import java.util.Collections;
import java.util.List;

public class ManHinhChinhThuQuy extends AppCompatActivity {
    private String tenTaiKhoan;
    private QuanLyDuLieu quanLyDuLieu;
    private RecyclerView rvSuKien;
    private TextView tvTrong;
    private EditText etTimKiem;
    private Spinner spSapXep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.man_hinh_chinh_thu_quy);

        tenTaiKhoan = getIntent().getStringExtra("TEN_TAI_KHOAN");
        quanLyDuLieu = new QuanLyDuLieu(this);

        rvSuKien = findViewById(R.id.rvSuKien);
        tvTrong = findViewById(R.id.tvKhongCoSuKien);
        etTimKiem = findViewById(R.id.etTimKiemSuKien);
        spSapXep = findViewById(R.id.spSapXepSuKien);
        FloatingActionButton fabThem = findViewById(R.id.fabTaoSuKien);

        rvSuKien.setLayoutManager(new LinearLayoutManager(this));

        fabThem.setOnClickListener(v -> {
            Intent intent = new Intent(this, ManHinhTaoSuKien.class);
            intent.putExtra("TEN_TAI_KHOAN", tenTaiKhoan);
            startActivity(intent);
        });

        // --- XỬ LÝ NÚT BACK TRÊN GIAO DIỆN (MŨI TÊN QUAY LẠI) ---
        ImageButton btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            // Thay vì finish() trực tiếp, gọi hàm hiển thị bảng lựa chọn
            btnBack.setOnClickListener(v -> hienThiDialogXacNhanThoat());
        }

        etTimKiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { capNhatDanhSach(); }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        spSapXep.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { capNhatDanhSach(); }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    // --- BẮT SỰ KIỆN NÚT BACK VẬT LÝ TRÊN ĐIỆN THOẠI ---
    @Override
    public void onBackPressed() {
        hienThiDialogXacNhanThoat();
    }

    // --- HÀM CHUNG HIỂN THỊ HỘP THOẠI 3 LỰA CHỌN ---
    private void hienThiDialogXacNhanThoat() {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận")
                .setMessage("Bạn muốn thực hiện thao tác gì?")
                .setPositiveButton("Thoát", (dialog, which) -> {
                    // Thoát hẳn ứng dụng
                    finishAffinity();
                    System.exit(0);
                })
                .setNegativeButton("Đăng xuất", (dialog, which) -> {
                    // Đăng xuất về màn hình đăng nhập, xóa sạch lịch sử activity cũ
                    Intent intent = new Intent(this, ManHinhDangNhap.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                })
                .setNeutralButton("Ở lại", (dialog, which) -> {
                    // Đóng dialog, giữ nguyên màn hình
                    dialog.dismiss();
                })
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        capNhatDanhSach();
    }

    private void capNhatDanhSach() {
        List<SuKien> dsHienTai = quanLyDuLieu.laySuKienTheoTaiKhoan(tenTaiKhoan);

        String tuKhoa = etTimKiem.getText().toString().trim().toLowerCase();
        if (!tuKhoa.isEmpty()) {
            dsHienTai.removeIf(sk -> !sk.getTenSuKien().toLowerCase().contains(tuKhoa) &&
                    !sk.getMaSuKien().toLowerCase().contains(tuKhoa));
        }

        int loaiSapXep = spSapXep.getSelectedItemPosition();
        if (loaiSapXep == 0) {
            dsHienTai.sort((a, b) -> a.getTenSuKien().compareToIgnoreCase(b.getTenSuKien()));
        } else if (loaiSapXep == 1) {
            dsHienTai.sort((a, b) -> b.getThoiGianTao().compareTo(a.getThoiGianTao()));
        } else if (loaiSapXep == 2) {
            dsHienTai.sort((a, b) -> Integer.compare(b.getDanhSachThanhVien().size(), a.getDanhSachThanhVien().size()));
        }

        if (dsHienTai.isEmpty()) {
            tvTrong.setVisibility(View.VISIBLE);
            tvTrong.setText("không có sự kiện nào được tạo");
            rvSuKien.setVisibility(View.GONE);
        } else {
            tvTrong.setVisibility(View.GONE);
            rvSuKien.setVisibility(View.VISIBLE);
            BoThichNghiSuKien adapter = new BoThichNghiSuKien(dsHienTai, suKien -> {
                Intent intent = new Intent(this, ManHinhChiTietSuKien.class);
                intent.putExtra("MA_SU_KIEN", suKien.getMaSuKien());
                startActivity(intent);
            });
            rvSuKien.setAdapter(adapter);
        }
    }
}