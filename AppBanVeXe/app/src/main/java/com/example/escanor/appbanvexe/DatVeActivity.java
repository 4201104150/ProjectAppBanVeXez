package com.example.escanor.appbanvexe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.escanor.model.VeXe;

public class DatVeActivity extends AppCompatActivity {

    TextView txtMSG,txtSoDT,txtTThai,txtGiaTien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat_ve);
        AddControls();
    }

    private void AddControls() {
        txtMSG=findViewById(R.id.txtMaSoGhe);
        txtSoDT=findViewById(R.id.txtPhone);
        txtTThai=findViewById(R.id.txtTrangThai);
        txtGiaTien=findViewById(R.id.txtMone);

        Intent intent=getIntent();
        Bundle bundle= intent.getBundleExtra("BUNDLE_VEXE");
        VeXe veXe= (VeXe) bundle.getSerializable("VeXe");

        txtMSG.setText(veXe.getSoGhe());
        txtGiaTien.setText(veXe.getSoTien());
        /*txtTThai.setText(veXe.getTrangThai());*/
        txtSoDT.setText(veXe.getSDT());

    }
}
