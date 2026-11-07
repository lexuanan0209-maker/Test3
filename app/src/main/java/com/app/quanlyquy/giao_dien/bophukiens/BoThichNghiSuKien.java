package com.app.quanlyquy.giao_dien.bophukiens;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.app.quanlyquy.R;
import com.app.quanlyquy.dactinh.SuKien;
import java.util.List;

public class BoThichNghiSuKien extends RecyclerView.Adapter<BoThichNghiSuKien.ViewHolder> {
    private final List<SuKien> ds;
    private final OnSuKienClickListener listener;

    public interface OnSuKienClickListener { void onSuKienClick(SuKien sk); }

    public BoThichNghiSuKien(List<SuKien> ds, OnSuKienClickListener l) {
        this.ds = ds;
        this.listener = l;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.muc_su_kien, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SuKien sk = ds.get(position);
        holder.tvTen.setText(sk.getTenSuKien());
        holder.tvMa.setText("Mã: " + sk.getMaSuKien() + " | Thu: " + (long)sk.getSoTienCanThu() + "đ");
        holder.tvThoiGian.setText(sk.getThoiGianTao());
        holder.itemView.setOnClickListener(v -> listener.onSuKienClick(sk));
    }

    @Override
    public int getItemCount() { return ds.size(); }

    // Thêm từ khóa public vào đây để đồng bộ phạm vi truy cập
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTen, tvMa, tvThoiGian;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTen = itemView.findViewById(R.id.tvMucTenSuKien);
            tvMa = itemView.findViewById(R.id.tvMucMaVaTien);
            tvThoiGian = itemView.findViewById(R.id.tvMucThoiGian);
        }
    }
}