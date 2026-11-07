package com.app.quanlyquy.giao_dien;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.app.quanlyquy.R;
import com.app.quanlyquy.dactinh.TaiKhoan;
import com.app.quanlyquy.dulieu.QuanLyDuLieu;

public class ManHinhDangNhap extends AppCompatActivity {
    private EditText etTenDangNhap, etMatKhau;
    private QuanLyDuLieu quanLyDuLieu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.man_hinh_dang_nhap);

        quanLyDuLieu = new QuanLyDuLieu(this);

        etTenDangNhap = findViewById(R.id.etTenDangNhap);
        etMatKhau = findViewById(R.id.etMatKhau);
        Button btnDangNhap = findViewById(R.id.btnDangNhap);
        Button btnDangKy = findViewById(R.id.btnDangKyThuQuy);
        Button btnKhach = findViewById(R.id.btnKhach);

        btnDangNhap.setOnClickListener(v -> thuHienDangNhap());
        btnDangKy.setOnClickListener(v -> startActivity(new Intent(this, ManHinhDangKy.class)));
        btnKhach.setOnClickListener(v -> startActivity(new Intent(this, ManHinhThietKhach.class)));
    }

    private void thuHienDangNhap() {
        String tenDN = etTenDangNhap.getText().toString().trim();
        String matKhau = etMatKhau.getText().toString().trim();

        if (tenDN.isEmpty() || matKhau.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin đăng nhập!", Toast.LENGTH_SHORT).show();
            return;
        }

        for (TaiKhoan tk : quanLyDuLieu.layDanhSachTaiKhoan()) {
            if (tk.getTenDangNhap().equals(tenDN) && tk.getMatKhau().equals(matKhau)) {
                if (tk.isDaKhoa()) {
                    Toast.makeText(this, "Tài khoản của bạn đã bị khóa bởi Admin!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ("ADMIN".equals(tk.getVaiTro())) {
                    startActivity(new Intent(this, ManHinhQuanTri.class));
                } else {
                    Intent intent = new Intent(this, ManHinhChinhThuQuy.class);
                    intent.putExtra("TEN_TAI_KHOAN", tk.getTenDangNhap());
                    startActivity(intent);
                }
                finish();
                return;
            }
        }
        Toast.makeText(this, "Tài khoản hoặc mật khẩu không chính xác!", Toast.LENGTH_SHORT).show();
    }
}