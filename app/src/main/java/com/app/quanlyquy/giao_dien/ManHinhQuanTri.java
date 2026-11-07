package com.app.quanlyquy.giao_dien;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import androidx.appcompat.app.AlertDialog; // Thêm import cho hộp thoại
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.app.quanlyquy.R;
import com.app.quanlyquy.dactinh.TaiKhoan;
import com.app.quanlyquy.dulieu.QuanLyDuLieu;
import com.app.quanlyquy.giao_dien.bophukiens.BoThichNghiTaiKhoan;
import java.util.List;

public class ManHinhQuanTri extends AppCompatActivity {
    private RecyclerView rvTaiKhoan;
    private QuanLyDuLieu quanLyDuLieu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.man_hinh_quan_tri);

        quanLyDuLieu = new QuanLyDuLieu(this);
        rvTaiKhoan = findViewById(R.id.rvDanhSachTaiKhoanAdmin);
        rvTaiKhoan.setLayoutManager(new LinearLayoutManager(this));

        // --- XỬ LÝ NÚT BACK TRÊN GIAO DIỆN (MŨI TÊN QUAY LẠI) ---
        ImageButton btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> hienThiDialogXacNhanThoat());
        }
    }

    // --- BẮT SỰ KIỆN NÚT BACK VẬT LÝ TRÊN ĐIỆN THOẠI ---
    @SuppressLint({"GestureBackNavigation", "MissingSuperCall"})
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
        List<TaiKhoan> ds = quanLyDuLieu.layDanhSachTaiKhoan();
        ds.removeIf(tk -> "ADMIN".equals(tk.getVaiTro()));

        BoThichNghiTaiKhoan adapter = new BoThichNghiTaiKhoan(ds, new BoThichNghiTaiKhoan.OnTaiKhoanListener() {
            @Override
            public void onChonTaiKhoan(TaiKhoan tk) {
                Intent intent = new Intent(ManHinhQuanTri.this, ManHinhChiTietAdmin.class);
                intent.putExtra("TEN_TAI_KHOAN", tk.getTenDangNhap());
                startActivity(intent);
            }

            @Override
            public void onKhoaTaiKhoan(TaiKhoan tk) {
                // 1. Đảo trạng thái khóa
                tk.setDaKhoa(!tk.isDaKhoa());

                // 2. Gọi hàm cập nhật dữ liệu tài khoản xuống JSON
                quanLyDuLieu.capNhatTaiKhoan(tk);

                // 3. Load lại danh sách lên giao diện
                capNhatDanhSach();
            }
        });
        rvTaiKhoan.setAdapter(adapter);
    }
}