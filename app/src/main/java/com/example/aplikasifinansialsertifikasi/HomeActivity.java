package com.example.aplikasifinansialsertifikasi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.aplikasifinansialsertifikasi.helpers.DatabaseHelper;
import com.example.aplikasifinansialsertifikasi.helpers.DetailCashFlow;
import com.example.aplikasifinansialsertifikasi.helpers.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {

    ImageButton pengaturanIcon;
    ImageButton pemasukanIcon;
    ImageButton pengeluaranIcon;
    ImageButton detailIcon;

    TextView pemasukanBulanIniTotal;
    TextView pengeluaranBulanIniTotal;

    DatabaseHelper databaseHelper = null;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        ActionBar ab = getActionBar();
//        ab.setTitle("Halaman Beranda");

        DateFormat dateFormat = new SimpleDateFormat("MM", Locale.US);
        Date date = new Date();

        String currentMonth = dateFormat.format(date);
        Log.d("Current month", currentMonth);

        if(getIntent().hasExtra("User")){
            user = getIntent().getParcelableExtra("User");
        }

        initViews();
        initObjects(currentMonth);

    }

    private void initViews(){
        pengaturanIcon = findViewById(R.id.pengaturan_icon);
        pemasukanIcon = findViewById(R.id.pemasukan_icon);
        pengeluaranIcon = findViewById(R.id.pengeluaran_icon);
        detailIcon = findViewById(R.id.detail_cash_flow_icon);

        pemasukanBulanIniTotal = findViewById(R.id.total_pemasukan_bulan_ini);
        pengeluaranBulanIniTotal = findViewById(R.id.total_pengeluaran_bulan_ini);
    }

    private void initObjects(String currentMonth){

        databaseHelper = new DatabaseHelper(getApplicationContext());

        pengaturanIcon.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), PengaturanActivity.class);
            i.putExtra("User",user);
            startActivity(i);
        });

        pemasukanIcon.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), PemasukanActivity.class);
            i.putExtra("User",user);
            startActivity(i);
        });

        pengeluaranIcon.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), PengeluaranActivity.class);
            i.putExtra("User",user);
            startActivity(i);
        });

        detailIcon.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), DetailCashFlowActivity.class);
            i.putExtra("User",user);
            startActivity(i);
        });

        if(databaseHelper.getNominalPemasukanByBulan(currentMonth) != null){

            int pemasukan = 0;
            List<DetailCashFlow> list = databaseHelper.getNominalPemasukanByBulan(currentMonth);

            for (int i = 0; i < list.size(); i++) {
                pemasukan += list.get(i).getNominal();
            }
            pemasukanBulanIniTotal.setText("Rp. "+ pemasukan);
            Log.d("Pemasukan", ""+pemasukan);
        }else{
            pemasukanBulanIniTotal.setText("Rp. 0");
        }

        if(databaseHelper.getNominalPengeluaranByBulan(currentMonth) != null) {

            int pengeluaran = 0;
            List<DetailCashFlow> list = databaseHelper.getNominalPengeluaranByBulan(currentMonth);

            for (int i = 0; i < list.size(); i++) {
                pengeluaran += list.get(i).getNominal();
            }

            pengeluaranBulanIniTotal.setText("Rp. "+pengeluaran);
            Log.d("Pengeluaran", ""+pengeluaran);

        }else{
            pengeluaranBulanIniTotal.setText("Rp. 0");
        }

    }
}