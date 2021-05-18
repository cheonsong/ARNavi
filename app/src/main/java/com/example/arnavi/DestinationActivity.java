package com.example.arnavi;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.arnavi.Data.BPDAO;
import com.example.arnavi.Data.BuildingDAO;
import com.example.arnavi.Data.BuildingDTO;
import com.example.arnavi.Data.MajorDAO;
import com.example.arnavi.Data.MajorDTO;
import com.example.arnavi.Data.ProfessorDAO;
import com.example.arnavi.Data.ProfessorDTO;

import java.util.ArrayList;


public class DestinationActivity extends AppCompatActivity {


    private Spinner spinner;
    private AutoCompleteTextView autoText;
    private Button button;
    private int spinnerSelected;
    private String name;

    //세부 목적지 배열
    ArrayList<BuildingDTO> buildingList;
    ArrayList<MajorDTO> majorList;
    ArrayList<ProfessorDTO> professorList;

    //자동완성용 배열
    ArrayList<String> buildings = new ArrayList<>();
    ArrayList<String> majors = new ArrayList<>();
    ArrayList<String> professors = new ArrayList<>();

    ArrayAdapter<String> adapter;
    double[] LaLo = new double[2];
    BPDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_destination);

        //DB에서 테이블 불러오기
        initDB();

        spinner = (Spinner) findViewById(R.id.spinner);
        autoText = (AutoCompleteTextView) findViewById(R.id.autoText);
        button = (Button)findViewById(R.id.destinationButton);
        TextView textView = findViewById(R.id.textView);

        buildings.addAll(majors);
        buildings.addAll(professors);


        textView.setText(buildings.get(149));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                spinnerSelected = position;
                adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, buildings);
                autoText.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        autoText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                name = ((TextView)view).getText().toString();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);


                if(spinnerSelected == 0){
                    LaLo = dao.getLaLoFromB(name);
                }
                else if(spinnerSelected == 1){
                    LaLo = dao.getLaLoFromM(name);
                }
                else if(spinnerSelected == 2){
                    LaLo = dao.getLaLoFromP(name);
                }

                intent.putExtra("LaLo", LaLo);
                startActivityForResult(intent, 101);
            }
        });

    }

    private void initDB() {
        loadBuilding();
        setBuildingList(buildings, buildingList);

        loadMajor();
        setMajorList(majors, majorList);

        loadProfessor();
        setProfessorList(professors, professorList);

        loadBP();

    }

    private void loadBP(){
        dao = new BPDAO(getApplicationContext());
        dao.createDatabase();
        dao.open();
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
            majors.add(o.getMname());
        }
    }

    private void loadProfessor() {
        ProfessorDAO dao = new ProfessorDAO(getApplicationContext());

        dao.createDatabase();
        dao.open();
        professorList = dao.getTableData();
        dao.close();
    }

    private void setProfessorList(ArrayList<String> professors, ArrayList<ProfessorDTO> professorList) {
        for (ProfessorDTO o : professorList) {
            professors.add(o.getPname());
        }
    }


}