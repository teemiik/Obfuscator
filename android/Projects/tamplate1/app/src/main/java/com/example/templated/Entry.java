package com.example.templated;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

//pactf1

//pactf2

//pactf3

//pactf4

//pactf5

//pactf6

//pactf7

//pactf8

//pactf9

//pactf0

public class Entry extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        Button #batn1# = findViewById(R.id.btn1);
        Button #batn2# = findViewById(R.id.btn2);
        Button #batn3# = findViewById(R.id.btn3);
        Button #batn4# = findViewById(R.id.btn4);
        Button #batn5# = findViewById(R.id.btn5);
        Button #batn6# = findViewById(R.id.btn6);
        Button #batn7# = findViewById(R.id.btn7);
        Button #batn8# = findViewById(R.id.btn8);
        Button #batn9# = findViewById(R.id.btn9);
        Button #btna10# = findViewById(R.id.btn10);
        Button #btna11# = findViewById(R.id.btn11);
        Button #btna12# = findViewById(R.id.btn12);
        Button #btna13# = findViewById(R.id.btn13);
        Button #btna14# = findViewById(R.id.btn14);
        Button #btna15# = findViewById(R.id.btn15);
        Button #btna16# = findViewById(R.id.btn16);
        Button #btna17# = findViewById(R.id.btn17);
        Button #btna18# = findViewById(R.id.btn18);
        Button #btna19# = findViewById(R.id.btn19);
        Button #btna20# = findViewById(R.id.btn20);
        Button #btna21# = findViewById(R.id.btn21);
        Button #btna22# = findViewById(R.id.btn22);
        Button #btna23# = findViewById(R.id.btn23);
        Button #btna24# = findViewById(R.id.btn24);
        Button #btna25# = findViewById(R.id.btn25);

        batn1.setOnClickListener(this);
        batn2.setOnClickListener(this);
        batn3.setOnClickListener(this);
        batn4.setOnClickListener(this);
        batn5.setOnClickListener(this);
        batn6.setOnClickListener(this);
        batn7.setOnClickListener(this);
        batn8.setOnClickListener(this);
        batn9.setOnClickListener(this);
        btna10.setOnClickListener(this);
        btna11.setOnClickListener(this);
        btna12.setOnClickListener(this);
        btna13.setOnClickListener(this);
        btna14.setOnClickListener(this);
        btna15.setOnClickListener(this);
        btna16.setOnClickListener(this);
        btna17.setOnClickListener(this);
        btna18.setOnClickListener(this);
        btna19.setOnClickListener(this);
        btna20.setOnClickListener(this);
        btna21.setOnClickListener(this);
        btna22.setOnClickListener(this);
        btna23.setOnClickListener(this);
        btna24.setOnClickListener(this);
        btna25.setOnClickListener(this);

        //initfunc1

        //initfunc2

        //initfunc3

        //initfunc4

        //initfunc5

        //initfunc6

        //initfunc7

        //initfunc8

        //initfunc9

        //initfunc0
    }

    //funct1

    @SuppressLint("ResourceType")
    @Override
    public void onClick(View v) {
        Intent #intent# = new Intent(this,Pict.class);
        switch (v.getId()) {
            case R.id.btn1:
                intent.putExtra("places",0);
                break;
            case R.id.btn2:
                intent.putExtra("places",1);
                break;
            case R.id.btn3:
                intent.putExtra("places",2);
                break;
            case R.id.btn4:
                intent.putExtra("places",3);
                break;
            case R.id.btn5:
                intent.putExtra("places",4);
                break;
            case R.id.btn6:
                intent.putExtra("places",5);
                break;
            case R.id.btn7:
                intent.putExtra("places",6);
                break;
            case R.id.btn8:
                intent.putExtra("places",7);
                break;
            case R.id.btn9:
                intent.putExtra("places",8);
                break;
            case R.id.btn10:
                intent.putExtra("places",9);
                break;
            case R.id.btn11:
                intent.putExtra("places",10);
                break;
            case R.id.btn12:
                intent.putExtra("places",11);
                break;
            case R.id.btn13:
                intent.putExtra("places",12);
                break;
            case R.id.btn14:
                intent.putExtra("places",13);
                break;
            case R.id.btn15:
                intent.putExtra("places",14);
                break;
            case R.id.btn16:
                intent.putExtra("places",15);
                break;
            case R.id.btn17:
                intent.putExtra("places",16);
                break;
            case R.id.btn18:
                intent.putExtra("places",17);
                break;
            case R.id.btn19:
                intent.putExtra("places",18);
                break;
            case R.id.btn20:
                intent.putExtra("places",19);
                break;
            case R.id.btn21:
                intent.putExtra("places",20);
                break;
            case R.id.btn22:
                intent.putExtra("places",21);
                break;
            case R.id.btn23:
                intent.putExtra("places",22);
                break;
            case R.id.btn24:
                intent.putExtra("places",23);
                break;
            case R.id.btn25:
                intent.putExtra("places",24);
        }
        startActivity(intent);
    }

    //funct2

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }

    //funct3

    //funct4

    //funct5

    //funct6

    //funct7

    //funct8

    //funct9

    //funct0
}
