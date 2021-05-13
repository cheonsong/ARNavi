package com.example.arnavi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.arnavi.Data.BuildingDAO;
import com.example.arnavi.Data.BuildingDTO;
import com.example.arnavi.Data.DBHelper;
import com.example.arnavi.Data.MajorDAO;
import com.example.arnavi.Data.MajorDTO;
import com.example.arnavi.R;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DestinationActivity extends AppCompatActivity {


    private Spinner spinner;
    private AutoCompleteTextView autoText;
    private int spinnerSelected;
    private TextView tv_result;
    //세부 목적지 배열
    ArrayList<BuildingDTO> buildingList;
    ArrayList<MajorDTO> majorList;
//    ArrayList<ProfessorDTO> professorList;

    ArrayList<String> buildings = new ArrayList<>();
    ArrayList<String> majors = new ArrayList<>();
    ArrayList<String> professors = new ArrayList<>();

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_destination);

        //DB에서 테이블 불러오기
        initDB();

        spinner = (Spinner) findViewById(R.id.spinner);
        autoText = (AutoCompleteTextView) findViewById(R.id.autoText);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == 0) {
                    adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, buildings);
                    autoText.setAdapter(adapter);
                } else if (position == 1) {
                    adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, majors);
                    autoText.setAdapter(adapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void initDB() {
        loadBuilding();
        setBuildingList(buildings, buildingList);

        loadMajor();
        setMajorList(majors, majorList);
    }

    private void loadBuilding() {
        BuildingDAO dao = new BuildingDAO(getApplicationContext());

        dao.createDatabase();
        dao.open();
        buildingList = dao.getTableData();
        dao.close();
    }

    private void setBuildingList(ArrayList<String> buildings, ArrayList<BuildingDTO> buildingList) {
        for (BuildingDTO o : buildingList) {
            buildings.add(o.getBname());
        }
    }

    private void loadMajor() {
        MajorDAO dao = new MajorDAO(getApplicationContext());

        dao.createDatabase();
        dao.open();
        majorList = dao.getTableData();
        dao.close();
    }

    private void setMajorList(ArrayList<String> majors, ArrayList<MajorDTO> majorList) {
        for (MajorDTO o : majorList) {
            buildings.add(o.getMname());
        }
    }


}