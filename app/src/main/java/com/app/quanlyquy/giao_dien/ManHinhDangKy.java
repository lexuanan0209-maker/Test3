package com.app.quanlyquy.giao_dien;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.app.quanlyquy.R;
import com.app.quanlyquy.dactinh.TaiKhoan;
import com.app.quanlyquy.dulieu.QuanLyDuLieu;
import java.util.List;

public class ManHinhDangKy extends AppCompatActivity {
    private EditText etTen, etMatKhau;
    private QuanLyDuLieu quanLyDuLieu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.man_hinh_dang_ky);

        quanLyDuLieu = new QuanLyDuLieu(this);
        etTen = findViewById(R.id.etDangKyTen);
        etMatKhau = findViewById(R.id.etDangKyMatKhau);
        Button btnDangKy = findViewById(R.id.btnXacNhanDangKy);

        btnDangKy.setOnClickListener(v -> {
            String ten = etTen.getText().toString().trim();
            String mk = etMatKhau.getText().toString().trim();

            if (ten.isEmpty() || mk.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu!", Toast.LENGTH_SHORT).show();
                return;
            }

            List<TaiKhoan> ds = quanLyDuLieu.layDanhSachTaiKhoan();
            for (TaiKhoan tk : ds) {
                if (tk.getTenDangNhap().equals(ten)) {
                    Toast.makeText(this, "Tên đăng nhập đã tồn tại! Vui lòng chọn tên khác.", Toast.LENGTH_LONG).show();
                    return;
                }
            }

            ds.add(new TaiKhoan(ten, mk, "THU_QUY"));
            quanLyDuLieu.luuDanhSachTaiKhoan(ds);
            Toast.makeText(this, "Đăng ký tài khoản Thủ quỹ thành công!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}