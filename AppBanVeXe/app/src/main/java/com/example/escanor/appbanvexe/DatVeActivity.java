package com.example.escanor.appbanvexe;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.escanor.model.VeXe;
import com.example.escanor.util.Server;

import java.util.HashMap;
import java.util.Map;

public class DatVeActivity extends AppCompatActivity {

    TextView txtMSG,txtGiaTien;
    Button btnDatVe;
    EditText txtSoDT;
    String trangthai="";
    String id="";
    String update="http://192.168.1.7/server/setVeXe.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat_ve);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        AddControls();
        AddEvents();
    }


    private void AddEvents() {
        btnDatVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sdt=txtSoDT.getText().toString().trim();
                if(sdt.length()==10)
                {
                    xuLyDangKyVeXe(update);
                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    VeXe veXe=new VeXe(txtMSG.getText().toString(),txtGiaTien.getText().toString(),txtSoDT.getText().toString(),"1");
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("traVeXe",veXe);
                    intent.putExtra("Bundle_Travexe",bundle);
                    startActivity(intent);

                }
                else
                {
                    Toast.makeText(DatVeActivity.this,"Vui lòng nhập đúng SDT!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void xuLyDangKyVeXe(String url)
    {
        final RequestQueue requestQueue=Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("success"))
                {
                    Toast.makeText(DatVeActivity.this,"Đặt vé thành công!",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(DatVeActivity.this,MainActivity.class));
                }
                if(response.trim().equals("0"))
                {
                    Toast.makeText(DatVeActivity.this,"Đặt vé thất bại!",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String ,String > param=new HashMap<>();
                param.put("soGhe",id);
                param.put("soTien",txtGiaTien.getText().toString().trim());
                param.put("SDT",txtSoDT.getText().toString().trim());

                param.put("trangThai","1");

                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void AddControls() {
        txtMSG=findViewById(R.id.txtMaSoGhe);
        txtSoDT=findViewById(R.id.txtPhone);
        txtGiaTien=findViewById(R.id.txtMone);
        btnDatVe=findViewById(R.id.btnDatVe);

        Intent intent=getIntent();
        Bundle bundle= intent.getBundleExtra("BUNDLE_VEXE");
        VeXe veXe= (VeXe) bundle.getSerializable("VeXe");

        txtMSG.setText(veXe.getSoGhe());
        txtGiaTien.setText(veXe.getSoTien());
        /*txtTThai.setText(veXe.getTrangThai());*/
        txtSoDT.setText(veXe.getSDT());
        trangthai=veXe.getTrangThai();
        id=veXe.getSoGhe();
        xuLyNhapSDT();
    }

    private void xuLyNhapSDT() {
        if(trangthai=="1")
        {
            btnDatVe.setEnabled(false);
        }
    }
}
