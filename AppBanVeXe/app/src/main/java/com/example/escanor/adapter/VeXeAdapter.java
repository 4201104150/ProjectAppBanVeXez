package com.example.escanor.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.escanor.appbanvexe.R;
import com.example.escanor.model.VeXe;

import java.util.ArrayList;
import java.util.List;

public class VeXeAdapter extends ArrayAdapter<VeXe>
{
    Activity context;
    List<VeXe> objects;
    int resource;
    public VeXeAdapter(@NonNull Activity context, int resource, @NonNull List<VeXe> objects) {
        super(context, resource, objects);
        this.context=context;
        this.objects=objects;
        this.resource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=this.context.getLayoutInflater();
        View item=inflater.inflate(this.resource,null);

        final VeXe veXe=this.objects.get(position);

        TextView txtSoGhe=item.findViewById(R.id.txtMaSoGhe);
        TextView txtTrangThai=item.findViewById(R.id.txtTrangThai);
        TextView txtSDT=item.findViewById(R.id.txtSDT);
        TextView txtGiaTien=item.findViewById(R.id.txtGiaGhe);

        txtTrangThai.setText(veXe.getTrangThai());
        txtSDT.setText(veXe.getSDT());
        txtGiaTien.setText(veXe.getSoTien());
        txtSoGhe.setText(veXe.getSoGhe());
        return item;
    }
}
