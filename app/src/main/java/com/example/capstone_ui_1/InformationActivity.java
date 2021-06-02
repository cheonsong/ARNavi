package com.example.capstone_ui_1;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;


public class InformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_information);

        ImageButton art = (ImageButton) findViewById(R.id.art);
        ImageButton backhak = (ImageButton) findViewById(R.id.backhak);
        ImageButton professor_research = (ImageButton) findViewById(R.id.professor);
        ImageButton main = (ImageButton) findViewById(R.id.main);
        ImageButton physical = (ImageButton) findViewById(R.id.physical);
        ImageButton international = (ImageButton) findViewById(R.id.international);
        ImageButton s_hall = (ImageButton) findViewById(R.id.s_hall);
        ImageButton student = (ImageButton) findViewById(R.id.student);
        ImageButton it = (ImageButton) findViewById(R.id.it);
        ImageButton business = (ImageButton) findViewById(R.id.business);
        ImageButton engineering_one = (ImageButton) findViewById(R.id.engineering_one);
        ImageButton engineering_two = (ImageButton) findViewById(R.id.engineering_two);
        ImageButton dental = findViewById(R.id.dental);
        ImageButton medical_one = findViewById(R.id.medical_one);
        ImageButton medical_two;
        ImageButton medical_three = findViewById(R.id.medical_three);
        ImageButton hospital = findViewById(R.id.hospital);
        ImageButton bio = findViewById(R.id.bio);
        ImageButton law = findViewById(R.id.law);
        ImageButton nurse = findViewById(R.id.nurse);
        ImageButton space = findViewById(R.id.space);
        ImageButton ship_marine = findViewById(R.id.ship_marine);
        ImageButton rotc = findViewById(R.id.rotc);

        art.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder z_art = new AlertDialog.Builder(InformationActivity.this);
                z_art.setTitle("미술대학");
                z_art.setMessage(getString(R.string.art));
                z_art.setNegativeButton("닫기", null);
                z_art.show();
            }
        });
        backhak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder z_bk = new AlertDialog.Builder(InformationActivity.this);
                z_bk.setTitle("백학기숙사");
                z_bk.setMessage(R.string.backhak);
                z_bk.setNegativeButton("닫기", null);
                z_bk.show();
            }
        });
        professor_research.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder z_professor = new AlertDialog.Builder(InformationActivity.this);
                z_professor.setTitle("백학기숙사");
                z_professor.setMessage(getString(R.string.professor_research));
                z_professor.setNegativeButton("닫기", null);
                z_professor.show();
            }
        });
        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder z_bon = new AlertDialog.Builder(InformationActivity.this);
                z_bon.setTitle("본관");
                z_bon.setMessage(getString(R.string.main));
                z_bon.setNegativeButton("닫기", null);
                z_bon.show();
            }
        });
        physical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder z_phy = new AlertDialog.Builder(InformationActivity.this);
                z_phy.setTitle("체육대학");
                z_phy.setMessage(getString(R.string.physical));
                z_phy.setNegativeButton("닫기", null);
                z_phy.show();
            }
        });
        international.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder z_guk = new AlertDialog.Builder(InformationActivity.this);
                z_guk.setTitle("국제관");
                z_guk.setMessage(getString(R.string.international));
                z_guk.setNegativeButton("닫기", null);
                z_guk.show();
            }
        });
        s_hall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder z_seo = new AlertDialog.Builder(InformationActivity.this);
                z_seo.setTitle("서석홀");
                z_seo.setMessage(getString(R.string.s_hall));
                z_seo.setNegativeButton("닫기", null);
                z_seo.show();
            }
        });
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder z_student = new AlertDialog.Builder(InformationActivity.this);
                z_student.setTitle("학생회관");
                z_student.setMessage(getString(R.string.student));
                z_student.setNegativeButton("닫기", null);
                z_student.show();
            }
        });
        it.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder z_it = new AlertDialog.Builder(InformationActivity.this);
                z_it.setTitle("IT융합대학");
                z_it.setMessage(getString(R.string.it));
                z_it.setNegativeButton("닫기", null);
                z_it.show();
            }
        });
        business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder z_gus = new AlertDialog.Builder(InformationActivity.this);
                z_gus.setTitle("경상대학");
                z_gus.setMessage(getString(R.string.business));
                z_gus.setNegativeButton("닫기", null);
                z_gus.show();
            }
        });
        engineering_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder z_gong1 = new AlertDialog.Builder(InformationActivity.this);
                z_gong1.setTitle("공대 1호관");
                z_gong1.setMessage(getString(R.string.engineering_one));
                z_gong1.setNegativeButton("닫기", null);
                z_gong1.show();
            }
        });
        engineering_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder z_gong2 = new AlertDialog.Builder(InformationActivity.this);
                z_gong2.setTitle("공대 2호관");
                z_gong2.setMessage(getString(R.string.engineering_two));
                z_gong2.setNegativeButton("닫기", null);
                z_gong2.show();
            }
        });
        dental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder z_art = new AlertDialog.Builder(InformationActivity.this);
                z_art.setTitle("치과대학");
                z_art.setMessage(getString(R.string.dental));
                z_art.setNegativeButton("닫기", null);
                z_art.show();
            }
        });
        medical_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder z_art = new AlertDialog.Builder(InformationActivity.this);
                z_art.setTitle("의과대학 1호관");
                z_art.setMessage(getString(R.string.medical_one));
                z_art.setNegativeButton("닫기", null);
                z_art.show();
            }
        });
        medical_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder z_art = new AlertDialog.Builder(InformationActivity.this);
                z_art.setTitle("의과대학 3호관");
                z_art.setMessage(getString(R.string.medical_three));
                z_art.setNegativeButton("닫기", null);
                z_art.show();
            }
        });
        hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder z_art = new AlertDialog.Builder(InformationActivity.this);
                z_art.setTitle("조선대학교 병원");
                z_art.setMessage(getString(R.string.hospital));
                z_art.setNegativeButton("닫기", null);
                z_art.show();
            }
        });
        bio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder z_art = new AlertDialog.Builder(InformationActivity.this);
                z_art.setTitle("생명공학관");
                z_art.setMessage(getString(R.string.bio));
                z_art.setNegativeButton("닫기", null);
                z_art.show();
            }
        });
        law.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder z_art = new AlertDialog.Builder(InformationActivity.this);
                z_art.setTitle("법과대학");
                z_art.setMessage(getString(R.string.law));
                z_art.setNegativeButton("닫기", null);
                z_art.show();
            }
        });
        nurse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder z_art = new AlertDialog.Builder(InformationActivity.this);
                z_art.setTitle("간호대학");
                z_art.setMessage(getString(R.string.nurse));
                z_art.setNegativeButton("닫기", null);
                z_art.show();
            }
        });
        space.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder z_art = new AlertDialog.Builder(InformationActivity.this);
                z_art.setTitle("항공우주대학");
                z_art.setMessage(getString(R.string.space));
                z_art.setNegativeButton("닫기", null);
                z_art.show();
            }
        });
        ship_marine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder z_art = new AlertDialog.Builder(InformationActivity.this);
                z_art.setTitle("선박해양대학");
                z_art.setMessage(getString(R.string.ship_marine));
                z_art.setNegativeButton("닫기", null);
                z_art.show();
            }
        });
        rotc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder z_art = new AlertDialog.Builder(InformationActivity.this);
                z_art.setTitle("학군단");
                z_art.setMessage(getString(R.string.rotc));
                z_art.setNegativeButton("닫기", null);
                z_art.show();
            }
        });

    }

}
