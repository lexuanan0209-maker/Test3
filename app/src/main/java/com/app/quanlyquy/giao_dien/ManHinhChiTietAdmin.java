package com.app.quanlyquy.giao_dien;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.app.quanlyquy.R;
import com.app.quanlyquy.dactinh.SuKien;
import com.app.quanlyquy.dulieu.QuanLyDuLieu;
import com.app.quanlyquy.giao_dien.bophukiens.BoThichNghiSuKien;
import java.util.List;

public class ManHinhChiTietAdmin extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.man_hinh_chi_tiet_admin);

        String tenThuQuy = getIntent().getStringExtra("TEN_TAI_KHOAN");
        QuanLyDuLieu quanLyDuLieu = new QuanLyDuLieu(this);

        TextView tvTieuDe = findViewById(R.id.tvTieuDeAdminSuKien);
        RecyclerView rvSuKien = findViewById(R.id.rvSuKienCuaThuQuy);
        rvSuKien.setLayoutManager(new LinearLayoutManager(this));

        tvTieuDe.setText("Sự kiện do thủ quỹ: " + tenThuQuy + " tạo");

        List<SuKien> ds = quanLyDuLieu.laySuKienTheoTaiKhoan(tenThuQuy);
        BoThichNghiSuKien adapter = new BoThichNghiSuKien(ds, suKien -> {});
        rvSuKien.setAdapter(adapter);

        // Xử lý sự kiện bấm nút mũi tên quay lại
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
    }
}