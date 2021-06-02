package com.example.capstone_ui_1;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;


import com.example.capstone_ui_1.Data.ChosunDAO;
import com.example.capstone_ui_1.Data.ChosunDTO;
import com.example.capstone_ui_1.Data.CustomAdapter;
import com.example.capstone_ui_1.Data.LaLo;
import com.example.capstone_ui_1.Data.RecyclerViewClickInterface;

import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.cache.DiskLruCache;


public class DestinationActivity extends AppCompatActivity implements RecyclerViewClickInterface {
    private RecyclerView recyclerView;
    CustomAdapter adapter;

    //세부 목적지 배열
    ArrayList<ChosunDTO> chosunList;
    ArrayList<ChosunDTO> laloList;
    //자동완성용 배열
    ArrayList<String> chosuns = new ArrayList<>();
    ArrayList<com.example.capstone_ui_1.Data.LaLo> lalos = new ArrayList<>();

    Button selectBtn;
    ChosunDAO dao;
    SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination2);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //DB에서 테이블 불러오기
        initDB();

        selectBtn = findViewById(R.id.selectButton);
        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                startActivity(intent);
            }
        });

        adapter = new CustomAdapter(chosunList, recyclerView,this, this);
        recyclerView.setAdapter(adapter);

        searchView = findViewById(R.id.searchView);
        searchView.setQueryHint("목적지를 입력하세요.");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s.toString());

                return false;
            }
        });
    }

    private void initDB() {
        loadBuilding();
        setChosunList(chosuns, chosunList);
        setLaloList(lalos, laloList);

        loadBP();

    }

    private void loadBP() {
        dao = new ChosunDAO(getApplicationContext());
        dao.createDatabase();
        dao.open();
    }

    private void loadBuilding() {
        ChosunDAO dao = new ChosunDAO(getApplicationContext());

        dao.createDatabase();
        dao.open();
        chosunList = dao.getTableData();
        laloList = dao.getTableData();
        dao.close();
    }

    private void setChosunList(ArrayList<String> chosuns, ArrayList<ChosunDTO> chosunList) {
        for (ChosunDTO o : chosunList) {
            chosuns.add(o.getBuilding());
            chosuns.add(o.getMajor());
            chosuns.add(o.getProfessor());
            chosuns.add(Double.toString(o.getLatitude()));
            chosuns.add(Double.toString(o.getLongtitude()));
        }
    }

    private void setLaloList(ArrayList<LaLo> lalos, ArrayList<ChosunDTO> laloList) {
        for (ChosunDTO o : laloList) {
            LaLo lalo = new LaLo();
            lalo.setLa(o.getLatitude());
            lalo.setLo(o.getLongtitude());
            lalos.add(lalo);
        }
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onLongItemClick(int position) {
        chosuns.remove(position);
        lalos.remove(position);
    }

}
