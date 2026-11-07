package com.app.quanlyquy.giao_dien;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.app.quanlyquy.R;
import com.app.quanlyquy.dactinh.SuKien;
import com.app.quanlyquy.dactinh.ThanhVien;
import com.app.quanlyquy.dulieu.QuanLyDuLieu;
import com.app.quanlyquy.giao_dien.bophukiens.BoThichNghiSuKien;
import java.util.List;
import java.util.UUID;

public class ManHinhSaoChepThanhVien extends AppCompatActivity {
    private String maSuKienNguon;
    private QuanLyDuLieu quanLyDuLieu;
    private SuKien suKienNguon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.man_hinh_sao_chep_thanh_vien);

        maSuKienNguon = getIntent().getStringExtra("MA_SU_KIEN_NGUON");
        quanLyDuLieu = new QuanLyDuLieu(this);
        suKienNguon = quanLyDuLieu.timSuKienTheoMa(maSuKienNguon);

        RecyclerView rvDich = findViewById(R.id.rvSuKienDich);
        rvDich.setLayoutManager(new LinearLayoutManager(this));
        findViewById(R.id.btnThoatSaoChep).setOnClickListener(v -> finish());

        List<SuKien> ds = quanLyDuLieu.laySuKienTheoTaiKhoan(suKienNguon.getTenTaiKhoanTao());
        ds.removeIf(sk -> sk.getMaSuKien().equals(maSuKienNguon));

        rvDich.setAdapter(new BoThichNghiSuKien(ds, suKienDich -> {
            List<ThanhVien> dsCu = suKienDich.getDanhSachThanhVien();
            if (dsCu.isEmpty()) {
                thucHienSaoChep(suKienDich, false);
            } else {
                new AlertDialog.Builder(this)
                        .setTitle("Cảnh báo sao chép")
                        .setMessage("Sự kiện đích đã có " + dsCu.size() + " thành viên. Chọn phương án xử lý:")
                        .setPositiveButton("Thay thế toàn bộ", (d, w) -> thucHienSaoChep(suKienDich, false))
                        .setNeutralButton("Giữ nguyên & Thêm tiếp", (d, w) -> thucHienSaoChep(suKienDich, true))
                        .setNegativeButton("Thoát", null)
                        .show();
            }
        }));
    }

    private void thucHienSaoChep(SuKien suKienDich, boolean giuNguyen) {
        if (!giuNguyen) suKienDich.getDanhSachThanhVien().clear();
        for (ThanhVien tv : suKienNguon.getDanhSachThanhVien()) {
            suKienDich.getDanhSachThanhVien().add(new ThanhVien(UUID.randomUUID().toString(), tv.getTenThanhVien(), false));
        }
        quanLyDuLieu.capNhatSuKien(suKienDich);
        Toast.makeText(this, "Sao chép thành công!", Toast.LENGTH_SHORT).show();
        finish();
    }
}