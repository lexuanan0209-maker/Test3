package com.app.quanlyquy.giao_dien;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.app.quanlyquy.R;
import com.app.quanlyquy.dactinh.SuKien;
import com.app.quanlyquy.dulieu.QuanLyDuLieu;

public class ManHinhThietKhach extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.man_hinh_thiet_khach);

        QuanLyDuLieu ql = new QuanLyDuLieu(this);
        EditText etMa = findViewById(R.id.etMaSuKienTraCuu);

        findViewById(R.id.btnXacNhanTraCuu).setOnClickListener(v -> {
            String ma = etMa.getText().toString().trim();
            SuKien sk = ql.timSuKienTheoMa(ma);
            if (sk != null) {
                startActivity(new Intent(this, ManHinhHoatDongChi.class)
                        .putExtra("MA_SU_KIEN", sk.getMaSuKien())
                        .putExtra("CHIDOC", true));
            } else {
                Toast.makeText(this, "Mã sự kiện không tồn tại!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}