package com.app.quanlyquy.giao_dien.bophukiens;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;
import com.app.quanlyquy.R;
import com.app.quanlyquy.dactinh.ThanhVien;
import java.util.List;
import java.util.Objects;

public class BoThichNghiThanhVien extends RecyclerView.Adapter<BoThichNghiThanhVien.ViewHolder> {
    private final List<ThanhVien> ds;
    private final boolean coQuyen;
    private final OnThanhVienListener listener;

    public interface OnThanhVienListener {
        void onTichDongTien(ThanhVien tv, boolean isChecked);
        void onSuaThanhVien(ThanhVien tv);
        void onXoaThanhVien(ThanhVien tv);
    }

    public BoThichNghiThanhVien(List<ThanhVien> ds, boolean coQuyen, OnThanhVienListener l) {
        this.ds = ds;
        this.coQuyen = coQuyen;
        this.listener = l;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.muc_thanh_vien, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ThanhVien tv = ds.get(position);
        holder.tvTen.setText(tv.getTenThanhVien());
        holder.cb.setOnCheckedChangeListener(null);
        holder.cb.setChecked(tv.isDaDongTien());
        holder.cb.setEnabled(coQuyen);

        holder.cb.setOnCheckedChangeListener((b, isChecked) -> {
            if (coQuyen) listener.onTichDongTien(tv, isChecked);
        });

        if (coQuyen) {
            holder.btnMenu.setVisibility(View.VISIBLE);
            holder.btnMenu.setOnClickListener(v -> {
                PopupMenu p = new PopupMenu(v.getContext(), v);
                p.getMenu().add("Sửa tên");
                p.getMenu().add("Xóa");
                p.setOnMenuItemClickListener(item -> {
                    if (Objects.equals(item.getTitle(), "Sửa tên")) listener.onSuaThanhVien(tv);
                    else if (Objects.equals(item.getTitle(), "Xóa")) listener.onXoaThanhVien(tv);
                    return true;
                });
                p.show();
            });
        } else {
            holder.btnMenu.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() { return ds.size(); }

    // Thêm từ khóa public vào đây để tránh cảnh báo phạm vi truy cập
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTen;
        CheckBox cb;
        ImageView btnMenu;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTen = itemView.findViewById(R.id.tvMucTenThanhVien);
            cb = itemView.findViewById(R.id.cbMucDongTien);
            btnMenu = itemView.findViewById(R.id.btnMucMenuThanhVien);
        }
    }
}