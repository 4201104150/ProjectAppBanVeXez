package com.example.escanor.appbanvexe;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.escanor.adapter.VeXeAdapter;
import com.example.escanor.model.VeXe;
import com.example.escanor.util.CheckConnection;
import com.example.escanor.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.BufferUnderflowException;
import java.nio.ByteOrder;
import java.nio.channels.InterruptedByTimeoutException;
import java.util.ArrayList;

import static android.content.Context.*;

public class MainActivity extends AppCompatActivity {


    ListView lvVeXe;
    ArrayList<VeXe> dsVeXe;
    VeXeAdapter veXeAdapter;

    ListView lvDatVeXe;
    ArrayList<VeXe> dsDatVeXe;
    VeXeAdapter datVeXeAdapter;

    String maSoGhe;
    String soTien;
    String SDT;
    String trangThai;
    String s;
    TextView txtSoTien,txtTrangThai,txtSDT, txtSoghe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Server.getWifiAddress(this);
        Toast.makeText(this,Server.getWifiAddress(this),Toast.LENGTH_LONG).show();
        AddControls();
        AddEvents();


    }

    private void AddEvents()
    {
        lvVeXe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(),DatVeActivity.class);
                VeXe veXe= (VeXe) parent.getAdapter().getItem(position);
                Bundle bundle=new Bundle();
                //VeXe veXe=new VeXe(dsVeXe.get(position).getSoGhe(),dsVeXe.get(position).getSoTien(),dsVeXe.get(position).getSDT(),dsVeXe.get(position).getTrangThai());
                bundle.putSerializable("VeXe",veXe);

                intent.putExtra("BUNDLE_VEXE",bundle);
                startActivity(intent);
            }
        });
        /*txtMaSGhe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,DatVeActivity.class);
                intent.putExtra("x",4);
                startActivity(intent);
            }

        });*/
    }

    private void AddControls() {


        TabHost tabHost=findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tab1=tabHost.newTabSpec("t1");
        tab1.setIndicator("Trường Thành");
        tab1.setContent(R.id.tab1);
        tabHost.addTab(tab1);

        TabHost.TabSpec tab2=tabHost.newTabSpec("t2");
        tab2.setContent(R.id.tab2);
        tab2.setIndicator("Lịch sử");
        tabHost.addTab(tab2);

        txtSoghe=findViewById(R.id.textView3);
        txtSDT=findViewById(R.id.txtSDT);
        txtSoTien=findViewById(R.id.txtGiaGhe);
        txtTrangThai=findViewById(R.id.txtTrangThai);

        lvVeXe=findViewById(R.id.lvDSVeXe);
        dsVeXe=new ArrayList<>();
        veXeAdapter=new VeXeAdapter(MainActivity.this,R.layout.itemghe,dsVeXe);
        lvVeXe.setAdapter(veXeAdapter);


        /*Intent intent=getIntent();
        Bundle bundle= intent.getBundleExtra("Bundle_Travexe");
        VeXe veXe= (VeXe) bundle.getSerializable("traVeXe");*/


        lvDatVeXe=findViewById(R.id.lvHistory);
        dsDatVeXe=new ArrayList<>();

        datVeXeAdapter=new VeXeAdapter(MainActivity.this,R.layout.itemghe,dsDatVeXe);
        lvDatVeXe.setAdapter(datVeXeAdapter);

        VeXeTask task=new VeXeTask();
        task.execute();
    }

    class VeXeTask extends AsyncTask<Void,Void,ArrayList<VeXe>>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            veXeAdapter.clear();
        }

        @Override
        protected void onPostExecute(ArrayList<VeXe> veXes) {
            super.onPostExecute(veXes);
            veXeAdapter.clear();
            veXeAdapter.addAll(veXes);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected ArrayList<VeXe> doInBackground(Void... voids) {
            ArrayList<VeXe> ds=new ArrayList<>();
            try
            {
                URL url=new URL("http://192.168.1.7/server/getVeXe.php");
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                InputStreamReader inputStreamReader=new InputStreamReader(connection.getInputStream(),"UTF-8");
                BufferedReader bufferedReader=new BufferedReader(inputStreamReader);

                StringBuilder builder=new StringBuilder();
                String line=bufferedReader.readLine();
                while (line!=null)
                {
                    builder.append(line);
                    line=bufferedReader.readLine();
                }
                JSONArray jsonArray=new JSONArray(builder.toString());
                for (int i=0;i<jsonArray.length();i++)
                {
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    VeXe veXe=new VeXe();
                    if(jsonObject.has("soGhe"))
                    {
                        veXe.setSoGhe(jsonObject.getString("soGhe"));
                    }
                    if(jsonObject.has("SDT"))
                    {
                        veXe.setSDT(jsonObject.getString("SDT"));
                    }
                    if(jsonObject.has("soTien"))
                    {
                        veXe.setSoTien(jsonObject.getString("soTien"));
                    }
                    if(jsonObject.has("trangThai"))
                    {
                        veXe.setTrangThai(jsonObject.getString("trangThai"));
                        s=jsonObject.getString("trangThai");

                    }
                    if(veXe.trangThai=="1")
                {
                    txtSoghe.setTextColor(Color.GREEN);
                }
                    ds.add(veXe);
                }

            }
            catch (Exception ex)
            {
                Log.e("Loi",ex.toString());
            }

            return ds;
        }
    }
}
