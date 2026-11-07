package com.app.quanlyquy.giao_dien;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.app.quanlyquy.R;
import com.app.quanlyquy.dactinh.SuKien;
import com.app.quanlyquy.dulieu.QuanLyDuLieu;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ManHinhTaoSuKien extends AppCompatActivity {
    private EditText etTenSuKien, etSoTienCanThu;
    private QuanLyDuLieu quanLyDuLieu;
    private String tenTaiKhoan, thoiGianHienTai;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.man_hinh_tao_su_kien);

        tenTaiKhoan = getIntent().getStringExtra("TEN_TAI_KHOAN");
        quanLyDuLieu = new QuanLyDuLieu(this);

        etTenSuKien = findViewById(R.id.etTenSuKienMoi);
        etSoTienCanThu = findViewById(R.id.etSoTienCanThuMoi);
        TextView tvThoiGian = findViewById(R.id.tvThoiGianTaoTuDong);
        Button btnTao = findViewById(R.id.btnXacNhanTaoSuKien);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        thoiGianHienTai = sdf.format(new Date());
        tvThoiGian.setText("Thời gian tạo: " + thoiGianHienTai);

        btnTao.setOnClickListener(v -> {
            String tenSK = etTenSuKien.getText().toString().trim();
            String strSoTien = etSoTienCanThu.getText().toString().trim();

            if (tenSK.isEmpty() || strSoTien.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ tên sự kiện và số tiền cần thu!", Toast.LENGTH_SHORT).show();
                return;
            }

            double soTien;
            try {
                soTien = Double.parseDouble(strSoTien);
                if (soTien <= 0) throw new Exception();
            } catch (Exception e) {
                Toast.makeText(this, "Số tiền cần thu phải là ký tự số hợp lệ!", Toast.LENGTH_SHORT).show();
                return;
            }

            String maRandom = quanLyDuLieu.taoMaSuKienNgauNhienDuyNhat();
            SuKien skMoi = new SuKien(maRandom, tenSK, soTien, thoiGianHienTai, tenTaiKhoan);

            List<SuKien> ds = quanLyDuLieu.layDanhSachSuKien();
            ds.add(skMoi);
            quanLyDuLieu.luuDanhSachSuKien(ds);

            Toast.makeText(this, "Tạo thành công! Mã sự kiện: " + maRandom, Toast.LENGTH_LONG).show();
            finish();
        });
    }
}