package com.app.quanlyquy.giao_dien.bophukiens;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.quanlyquy.R;
import com.app.quanlyquy.dactinh.HoatDongChi;

import java.util.List;

public class BoThichNghiHoatDongChi extends RecyclerView.Adapter<BoThichNghiHoatDongChi.ViewHolder> {
    private final List<HoatDongChi> ds;
    private final boolean coQuyen;
    private final OnChiClickListener listener;

    public interface OnChiClickListener { void onChiClick(HoatDongChi chi); }

    public BoThichNghiHoatDongChi(List<HoatDongChi> ds, boolean coQuyen, OnChiClickListener l) {
        this.ds = ds;
        this.coQuyen = coQuyen;
        this.listener = l;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.muc_hoat_dong_chi, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HoatDongChi chi = ds.get(position);
        holder.tvInfo.setText(chi.getTenNguoiChi() + " - " + (long)chi.getSoTienChi() + "đ\n" + chi.getNoiDungChi() + " (" + chi.getThoiGianChi() + ")");
        if (coQuyen) {
            holder.itemView.setOnClickListener(v -> listener.onChiClick(chi));
        }
    }

    @Override
    public int getItemCount() { return ds.size(); }

    // Thêm từ khóa public vào đây để tránh cảnh báo phạm vi truy cập
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvInfo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvInfo = itemView.findViewById(R.id.tvMucThongTinChi);
        }
    }
}