package com.example.webapp.webapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

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


public class LoadActivity extends Activity {

    private Timer #wBVTimer#;
    private int #wBVSec# = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);

        getwBVTimer();

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

    private void getwBVTimer() {

        wBVTimer = new Timer();
        wBVTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (wBVSec != getResources().getInteger(R.integer.close_load_activity)) {
                    wBVSec++;
                } else {
                    wBVTimer.cancel();
                    LoadActivity.this.finish();
                }
            }
        }, 0, 1000);

    }

    //funct2

    //funct3

    //funct4

    //funct5

    //funct6

    //funct7

    //funct8

    //funct9

    //funct0
}
