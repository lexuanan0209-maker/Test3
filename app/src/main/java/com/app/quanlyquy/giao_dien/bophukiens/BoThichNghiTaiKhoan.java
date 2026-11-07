package com.app.quanlyquy.giao_dien.bophukiens;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.app.quanlyquy.R;
import com.app.quanlyquy.dactinh.TaiKhoan;
import java.util.List;

public class BoThichNghiTaiKhoan extends RecyclerView.Adapter<BoThichNghiTaiKhoan.ViewHolder> {
    private final List<TaiKhoan> ds;
    private final OnTaiKhoanListener listener;

    public interface OnTaiKhoanListener {
        void onChonTaiKhoan(TaiKhoan tk);
        void onKhoaTaiKhoan(TaiKhoan tk);
    }

    public BoThichNghiTaiKhoan(List<TaiKhoan> ds, OnTaiKhoanListener l) {
        this.ds = ds;
        this.listener = l;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.muc_tai_khoan, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TaiKhoan tk = ds.get(position);
        holder.tvTen.setText("Thủ quỹ: " + tk.getTenDangNhap() + (tk.isDaKhoa() ? " [ĐÃ KHÓA]" : ""));
        holder.btnKhoa.setText(tk.isDaKhoa() ? "Mở khóa" : "Khóa");

        holder.itemView.setOnClickListener(v -> listener.onChonTaiKhoan(tk));
        holder.btnKhoa.setOnClickListener(v -> listener.onKhoaTaiKhoan(tk));
    }

    @Override
    public int getItemCount() { return ds.size(); }

    // Thêm từ khóa public vào đây để tránh cảnh báo phạm vi truy cập
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTen;
        Button btnKhoa;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTen = itemView.findViewById(R.id.tvMucTenTaiKhoan);
            btnKhoa = itemView.findViewById(R.id.btnKhoaTaiKhoan);
        }
    }
}